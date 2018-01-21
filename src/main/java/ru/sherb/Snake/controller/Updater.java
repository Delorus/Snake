package ru.sherb.Snake.controller;

import ru.sherb.Snake.Main;
import ru.sherb.Snake.model.Game;
import ru.sherb.Snake.model.Snake;
import ru.sherb.Snake.view.GameShell;

/**
 * Класс обновляет игровое состояние, рисует графическую часть и обновляет данные в графических объектах.
 * Запускать выполнение класса рекомендуется в специальном ассинхронном потоке из библиотеки SWT.
 * <p>
 * Created by sherb on 07.11.2016.
 */
public class Updater implements Runnable {
    /**
     * Количество наносекунд, за которое должен обработаться один логический фрейм
     */
    private static final double NS_PER_TICK = 1_000_000_000.0 / 60;

    private Game game;
    private GameShell gameShell;
    private IRender render;

    private boolean stop;
    private volatile boolean pause;


    public Updater(Game game, GameShell gameShell) {
        this.game = game;
        this.gameShell = gameShell;
        render = new RenderCPU(game.getGrid(), gameShell.getGameField());
    }

    public void init() {
        render.init();
        game.init();
        start();
    }

    @Override
    public void run() {
        if (gameShell.isDisposed()) return;

        long lastTime = System.nanoTime();
        double unprocessed = 0;
        long lastTimer = System.currentTimeMillis();
        int frames = 0;
        int ticks = 0;

        while (!stop) {

            long now = System.nanoTime();
            // "необработанное" время, прошедшее с последней обработки
            // делится на время одного "тика"
            // в результате получается количество операций, которые должны быть обработаны
            unprocessed += (now - lastTime) / NS_PER_TICK;
            if (pause) {
                game.stop();
                synchronized (this) {
                    while (pause) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
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


            //TODO [REFACTOR] большую часть времени цикл проходит в "холостую", что излишне нагружает процессор
            // освобождение ресурсов процессора на 2мс,
            // пока остальные потоки не требовательны этого времени хватает, что бы ресурсы ЦП не простаивали
            Thread.yield();

            if (shouldRender) {
                frames++;
                render.paint();
            }

            //TODO [ВОЗМОЖНО] оптимизировать операцию, что бы не проходить массив каждый раз
            for (Snake player : game.getPlayers()) {
                if (player.isChanged()) {
                    org.eclipse.swt.graphics.Color swtColor = new org.eclipse.swt.graphics.Color(Main.display,
                            player.getColor().getRed(),
                            player.getColor().getGreen(),
                            player.getColor().getBlue());
                    //TODO [DEBUG] если закрыть окно в то время как идет обновление информации, то вылетит ошибка и закроет приложение.
                    // Попытка перехватить исключение и обработать не возымела эффекта
                    Main.display.syncExec(() -> gameShell.setData(player.getName(), swtColor, player.getScore(), player.getLength()));
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
            }

        }
        if (Main.isDebug()) System.out.println("Я закончил обновляться");


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

    public synchronized boolean isStop() {
        return stop;
    }

    public boolean isPause() {
        return pause;
    }
}
