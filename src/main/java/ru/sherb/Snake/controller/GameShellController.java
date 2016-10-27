package ru.sherb.Snake.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.Rectangle;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.model.Cell;
import ru.sherb.Snake.model.Game;
import ru.sherb.Snake.model.Grid;
import ru.sherb.Snake.model.Snake;
import ru.sherb.Snake.view.DialogForm;
import ru.sherb.Snake.view.GameShell;

import java.awt.Point;

/**
 * Created by sherb on 27.10.2016.
 */
public class GameShellController {

    public GameShellController() {
//        final GameShell gameShell;
        GameShell gameShell = new GameShell(Main.display);
        gameShell.open();
        gameShell.layout();
        Rectangle bounds = gameShell.getBoundsGame();
        //TODO [ВОЗМОЖНО] вынести в класс Game
        int CellCount = 10; // Количество ячеек
        //Math.min();
        int size = (int) Math.ceil((double)Math.min(bounds.height, bounds.width) / CellCount);
//        int size = bounds.height < bounds.width ? (int) Math.ceil((double) bounds.height / CellCount) : (int) Math.ceil((double) bounds.width / CellCount); // Расчет оптимального размера ячейки
        Cell.setSizeCoeff((double) size / Cell.NORMAL_SIZE); //Расчет размера каждой ячейки
        int gridWidth = (int) Math.round((double) bounds.width / Cell.getSize()); // round
        int gridHeight = (int) Math.round((double) bounds.height / Cell.getSize()); //round

        Grid grid = new Grid(gridWidth, gridHeight, java.awt.Color.BLACK);
        java.awt.Color fruitColor = java.awt.Color.RED;
        Snake player1 = new Snake(grid, 0, grid.getHeight() - 1, "player1", java.awt.Color.GREEN, 3);
//        Snake player1 = new Snake(grid, grid.getWidth() / 2, grid.getHeight() / 2, "player1", java.awt.Color.GREEN, 3);
//        Snake player2 = new Snake(grid, 0, 0, "player2", java.awt.Color.MAGENTA, 3);
        final Game game = new Game(grid, fruitColor, player1);

        GC gc = new GC(gameShell.getGameField());
        new Thread(game).start();
//        Thread upd = new Thread(new Updater(gc, grid));
//        upd.start();


        //TODO придумать что делать с этим слушателем, он мешает всей программе
        //TODO [DEBUG] слушатель не работает!
        gameShell.getGameField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gameShell.printMessage("Нажантие клавиши" + e);
                switch (e.keyCode) {
                    //TODO придумать где хранить управление у каждой змейки
                    case SWT.ARROW_UP:
                        player1.moveTo(Snake.UP);
                        break;
                    case SWT.ARROW_RIGHT:
                        player1.moveTo(Snake.RIGHT);
                        break;
                    case SWT.ARROW_DOWN:
                        player1.moveTo(Snake.DOWN);
                        break;
                    case SWT.ARROW_LEFT:
                        player1.moveTo(Snake.LEFT);
                        break;
//                    case 'w':
//                        player2.moveTo(Snake.UP);
//                        break;
//                    case 'd':
//                        player2.moveTo(Snake.RIGHT);
//                        break;
//                    case 's':
//                        player2.moveTo(Snake.DOWN);
//                        break;
//                    case 'a':
//                        player2.moveTo(Snake.LEFT);
//                        break;
                }

            }
        });

        //TODO избавиться от состояния гонок
        new Thread(() -> {
            while (grid.isActive()) {
                paint(grid, gc);
                if (gameShell.isDisposed()) {
                    System.out.println("Я закрыл игровое окно");
                    grid.setActive(false);
                    return;
                }
            }
            System.out.println("Я закончил обновляться");
            String buffScore = "";
            //TODO [REFACTOR] засунуть всех игроков в один массив
            buffScore += player1.getName() + "= " + player1.getScore() + "\n";
//            buffScore += player2.getName() + "= " + player2.getScore() + "\n";
            final String CountPlayerScore = buffScore;
            Main.display.syncExec(() -> new DialogForm(Main.display, "You lose", "You score: \n" + CountPlayerScore + " \n You time: " + game.getGameTime()));

        }).start();


    }

    //TODO [THREAD] синхронизировать потоки обновление графического и логического состояния игры
    public void paint(Grid grid, GC gc) {
        Point start = new Point(0, 0);
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                gc.setBackground(new org.eclipse.swt.graphics.Color(null,
                        grid.getCell(i, j).getColor().getRed(),
                        grid.getCell(i, j).getColor().getGreen(),
                        grid.getCell(i, j).getColor().getBlue()));
                gc.fillRectangle(start.x, start.y, Cell.getSize(), Cell.getSize());
//                gc.drawOval(start.x, start.y, Cell.getSize(), Cell.getSize());
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
}
