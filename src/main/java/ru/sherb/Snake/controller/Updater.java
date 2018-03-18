package ru.sherb.Snake.controller;

import ru.sherb.Snake.Main;
import ru.sherb.Snake.model.Game;
import ru.sherb.Snake.model.Snake;
import ru.sherb.Snake.statistic.Player;
import ru.sherb.Snake.statistic.PlayerStatisticLoader;
import ru.sherb.Snake.util.AwtToSwt;
import ru.sherb.Snake.view.DialogForm;
import ru.sherb.Snake.view.GameShell;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * Класс обновляет игровое состояние, рисует графическую часть и обновляет данные в графических объектах.
 * Запускать выполнение класса рекомендуется в специальном ассинхронном потоке из библиотеки SWT.
 * <p>
 * Created by sherb on 07.11.2016.
 */
class Updater implements Runnable {
    /**
     * Количество наносекунд, за которое должен обработаться один логический фрейм
     */
    private static final double NS_PER_TICK = 1_000_000_000.0 / 60;

    private final Game game;
    private final GameShell gameShell;
    private final IRender render;

    private boolean stop;
    private volatile boolean pause;

    private long lastTime;
    private double unprocessed;
    private long lastTimer;
    private int frames;
    private int ticks;


    public Updater(Game game, GameShell gameShell) {
        this.game = game;
        this.gameShell = gameShell;
        render = new RenderCPU(game.getGrid(), gameShell.getGameField());
    }

    public void init() {
        render.init();
        game.init();
        start();

        lastTime = System.nanoTime();
        unprocessed = 0;
        lastTimer = System.currentTimeMillis();
        frames = 0;
        ticks = 0;
    }

    @Override
    public void run() {
        if (gameShell.isDisposed() || stop) {
            if (Main.isDebug()) System.out.println("Я закончил обновляться");
            return;
        }

        long now = System.nanoTime();
        // "необработанное" время, прошедшее с последней обработки
        // делится на время одного "тика"
        // в результате получается количество операций, которые должны быть обработаны
        unprocessed += (now - lastTime) / NS_PER_TICK;
        if (pause) {
            game.stop();
            while (pause && !gameShell.isDisposed()) {
                if (!Main.display.readAndDispatch()) {
                    Main.display.sleep();
                }
            }
            if (gameShell.isDisposed()) {
                if (Main.isDebug()) System.out.println("Я закончил обновляться");
                return;
            }
            game.start();
        }
        lastTime = System.nanoTime();
        boolean shouldRender = false;
        while (unprocessed >= 1) {
            ticks++;
            game.step();
            unprocessed -= 1;
            shouldRender = true;
        }


        if (shouldRender) {
            frames++;
            render.paint();
        }

        for (Snake player : game.getPlayers()) {
            if (player.isChanged()) {
                gameShell.setData(
                        player.getName(),
                        AwtToSwt.toSwtColor(player.getColor()),
                        player.getScore(),
                        player.getLength());
                player.setChanged(false);
            }
        }

        // выводить каждую секунду
        if (System.currentTimeMillis() - lastTimer > 1000) {
            lastTimer += 1000;
            if (Main.isDebug()) System.out.println(ticks + " ticks, " + frames + " fps");
            frames = 0;
            ticks = 0;
        }

        if (game.isEnd()) {
            stop();
            List<Player> currentPlayers = new ArrayList<>();
            for (Snake player : game.getPlayers()) {
                Player statisticPlayer = new Player();
                statisticPlayer.setName(player.getName());
                statisticPlayer.setScore(player.getScore());
                statisticPlayer.setTime(game.getGameTime());
                currentPlayers.add(statisticPlayer);
            }

            PlayerStatisticLoader.getInstance().addAllRecord(currentPlayers);

            String scores = currentPlayers.stream()
                    .map(Player::toString)
                    .collect(Collectors.joining());

            ForkJoinPool.commonPool().execute(PlayerStatisticLoader.getInstance()::save);

            Main.display.syncExec(() -> new DialogForm(
                    gameShell,
                    "You lose",
                    "You time: " + (game.getGameTime() / 1000) + "sec." + "\n"
                            + scores));
        }

        Main.display.asyncExec(this);
    }

    public synchronized void stop() {
        stop = true;
        pause = false;
        if (!game.isEnd()) game.end();
        notifyAll();
    }

    public synchronized void start() {
        stop = false;
        pause = false;
        game.start();
        notify();
    }

    public synchronized void pause() {
        pause = true;
        game.stop();
    }

    public boolean isStop() {
        return stop;
    }

    public boolean isPause() {
        return pause;
    }
}
