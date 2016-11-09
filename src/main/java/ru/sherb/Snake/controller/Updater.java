package ru.sherb.Snake.controller;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.model.Cell;
import ru.sherb.Snake.model.Game;
import ru.sherb.Snake.model.Grid;
import ru.sherb.Snake.model.Snake;
import ru.sherb.Snake.util.FPSCounter;
import ru.sherb.Snake.util.Timer;
import ru.sherb.Snake.view.GameShell;

/**
 * Created by sherb on 07.11.2016.
 */
public class Updater implements Runnable {
    private Game game;
    private GameShell gameShell;
    private GC gc;


    public Updater(Game game, GameShell gameShell) {
        this.game = game;
        this.gameShell = gameShell;
    }

    //TODO [THREAD] синхронизировать потоки обновление графического и логического состояния игры
    //TODO [REFACTOR] перенести рендеринг с ЦП на ГП
    public void paint() {
        Point start = new Point(0, 0);
        Grid grid = game.getGrid();
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                gc.setBackground(new org.eclipse.swt.graphics.Color(null,
                        grid.getCell(i, j).getColor().getRed(),
                        grid.getCell(i, j).getColor().getGreen(),
                        grid.getCell(i, j).getColor().getBlue()));
                gc.fillRectangle(start.x, start.y, Cell.getSize(), Cell.getSize());
                start.y += Cell.getSize();
            }
            start.y = 0;
            start.x += Cell.getSize();

        }
        // Попытка оптимизации кода
        // height = 0
        //TODO [DEBUG] если на одной вертикали находятся два объекта, то происходит неверная отрисовка
        //TODO [DEBUG] находится в состоянии гонок с Game.class, из-за этого не всегда вовремя обновляется
        //TODO [DEBUG] при первом проходе не обновляются последние ячейки
        //TODO [REFACTOR] доделать обновление через буфер, после окончания разработки GUI
//        Point start = new Point(bounds.x, bounds.y);
//        Rectangle buff = new Rectangle(start.x, start.y, Cell.getSize(), Cell.getSize()); //одна ячейка
//        Color background = new Color(Display.getCurrent(),
//                grid.getColor().getRed(),
//                grid.getColor().getGreen(),
//                grid.getColor().getBlue());
//        for (int i = 0; i < grid.getWidth(); i++) {
//            for (int j = 0; j < grid.getHeight(); j++) {
//                if (grid.getCell(i, j).getStatus() == State.EMPTY) {
//                    buff.height += Cell.getSize();
//                } else {
//                    gc.fillRectangle(buff);
//                    gc.setBackground(new Color(Display.getCurrent(),
//                            grid.getCell(i, j).getColor().getRed(),
//                            grid.getCell(i, j).getColor().getGreen(),
//                            grid.getCell(i, j).getColor().getBlue()));
//                    gc.fillRectangle(start.x, start.y, Cell.getSize(), Cell.getSize());
//                    buff.y = start.y + Cell.getSize();
//                    buff.height = buff.y;
//                }
//                start.y += Cell.getSize();
//            }
//            gc.setBackground(background);
//            buff.height = buff.height - buff.y;
//            gc.fillRectangle(buff);
//            buff.y = start.y = bounds.y;
//            buff.x = start.x += Cell.getSize();
//            buff.height = Cell.getSize();
//        }
    }

    @Override
    public void run() {
        //TODO [REFACTOR] убрать после оптимизации рендеринга
        Timer timer = new Timer(1000);
        FPSCounter counter = new FPSCounter(System.out);
        timer.setTask(counter::printCount);
        Thread timerThread = new Thread(timer);
        timerThread.start();
        Runnable updateData = null;

        //Это сделано для того что бы рисование происходило в другом потоке и не мешало игре
        // Уличная магия
        Main.display.syncExec(() -> gc = new GC(gameShell.getGameField()));


        while (!game.isStop()) {

            //TODO [REFACTOR] блокировка FPS на ~60, убрать после оптимизации рендеринга
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            paint();
            counter.incCount();

            if (!timerThread.isAlive()) {
                timerThread = new Thread(timer);
                timerThread.start();
            }


            //TODO [ВОЗМОЖНО] оптимизировать операцию, что бы не проходить массив каждый раз
            for (Snake player: game.getPlayers()) {
                if (player.isChanged()){
                    org.eclipse.swt.graphics.Color swtColor = new org.eclipse.swt.graphics.Color(Main.display,
                            player.getColor().getRed(),
                            player.getColor().getGreen(),
                            player.getColor().getBlue());
                    updateData = () -> gameShell.setData(player.getName(), swtColor, player.getScore(), player.getLength());
                    Main.display.syncExec(updateData);
                    player.setChanged(false);
                }
            }



            if (gameShell.isDisposed()) {
                if (Main.debug) System.out.println("Я закрыл игровое окно");
                game.stop();
                //Что бы поток мог нормально завершиться после закрытия окна
//                game.start();
            }
        }
        if (Main.debug) System.out.println("Я закончил обновляться");

        //TODO это не то, что должен делать рисовальщик
        String buffScore = "";
        //TODO [REFACTOR] засунуть всех игроков в один массив
//        buffScore += player1.getName() + "= " + player1.getScore() + "\n";
////            buffScore += player2.getName() + "= " + player2.getScore() + "\n";
//        final String CountPlayerScore = buffScore;
//        Main.display.syncExec(() -> new DialogForm(gameShell, "You lose", "You score: \n" + CountPlayerScore + " \n You time: " + (game.getGameTime() / 1000) + " сек."));
    }
}
