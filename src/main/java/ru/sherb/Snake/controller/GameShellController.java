package ru.sherb.Snake.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.model.Cell;
import ru.sherb.Snake.model.Game;
import ru.sherb.Snake.model.Grid;
import ru.sherb.Snake.model.Snake;
import ru.sherb.Snake.view.DialogForm;
import ru.sherb.Snake.view.GameShell;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by sherb on 27.10.2016.
 */
public class GameShellController {
    private GameShell gameShell;

    public GameShellController() {
        //TODO [REFACTOR] вынести в отдельный метод
        //Чтение настроек из файла
        Properties setting = new Properties();
        try(FileInputStream fin = new FileInputStream("Snake.properties")) {
            setting.load(fin);
        } catch (FileNotFoundException e) {
            try(FileOutputStream fout = new FileOutputStream("Snake.properties")) {
                //TODO доделать после того как будет создана меню настроек
                //TODO [ВОЗМОЖНО] сделать все параметры в виде констант
                setting.setProperty("ScreenSizeX", "640");
                setting.setProperty("ScreenSizeY", "480");
                setting.setProperty("Fullscreen", "false");
                //...
                setting.store(fout, "Snake setting");
            } catch (IOException e1) {
                if (Main.debug) e1.printStackTrace();
            }
        } catch (IOException e) {
            if (Main.debug) e.printStackTrace();
        }

        assert setting.getProperty("ScreenSizeX") != null && setting.getProperty("ScreenSizeY") != null;
        Point bounds = new Point(Integer.valueOf(setting.getProperty("ScreenSizeX")), Integer.valueOf(setting.getProperty("ScreenSizeY")));
        assert setting.getProperty("Fullscreen") != null;
        gameShell = new GameShell(Main.display, bounds, Boolean.valueOf(setting.getProperty("Fullscreen")));
        gameShell.open();
        gameShell.layout();
        gameShell.getGameField().setFocus();
        //TODO [ВОЗМОЖНО] вынести в класс Game
        int cellCount = 20; // Количество ячеек

        computeCellSize(cellCount, new Point(gameShell.getBoundsGame().width, gameShell.getBoundsGame().height));
        int cellCountWidth = (int) Math.floor((double) gameShell.getBoundsGame().width / Cell.getSize()); // количество ячеек по горизонтали
        int cellCountHeight = (int) Math.floor((double) gameShell.getBoundsGame().height / Cell.getSize()); // количество ячеек по вертикали
        Grid grid = new Grid(cellCountWidth, cellCountHeight, Color.WHITE);
        Color fruitColor = Color.RED;
        Snake player1 = new Snake(grid, 0, grid.getHeight() - 1, "player1", Color.GREEN, 3);
//        Snake player1 = new Snake(grid, grid.getWidth() / 2, grid.getHeight() / 2, "player1", java.awt.Color.GREEN, 3);
//        Snake player2 = new Snake(grid, 0, 0, "player2", java.awt.Color.MAGENTA, 3);
        final Game game = new Game(grid, fruitColor, player1);

        GC gc = new GC(gameShell.getGameField());
        new Thread(game).start();
        //TODO избавиться от состояния гонок
        new Thread(() -> {
            while (grid.isActive()) {
                paint(grid, gc);
                if (gameShell.isDisposed()) {
                    if (Main.debug) System.out.println("Я закрыл игровое окно");
                    grid.setActive(false);
                    return;
                }
            }
            if (Main.debug) System.out.println("Я закончил обновляться");
            String buffScore = "";
            //TODO [REFACTOR] засунуть всех игроков в один массив
            buffScore += player1.getName() + "= " + player1.getScore() + "\n";
//            buffScore += player2.getName() + "= " + player2.getScore() + "\n";
            final String CountPlayerScore = buffScore;
            Main.display.syncExec(() -> new DialogForm(gameShell, "You lose", "You score: \n" + CountPlayerScore + " \n You time: " + (game.getGameTime() / 1000) + " сек."));
        }).start();


        gameShell.addListener(SWT.Close, e -> new MainShellController());

        //TODO придумать что делать с этим слушателем, он мешает всей программе
        gameShell.getGameField().addListener(SWT.KeyDown, e -> {
            if (Main.debug) gameShell.printMessage("Нажантие клавиши " + String.valueOf((char) e.keyCode));
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
        });

        gameShell.addListener(SWT.Resize, event -> {
//        gameShell.addControlListener(new ControlAdapter() {
//            @Override
//            public void controlResized(ControlEvent e) {
                if (Main.debug)
                    System.out.println("with and height = " + gameShell.getSize().x + " " + gameShell.getSize().y);
                //TODO [DEBUG] пока лучший вариант для стирания старой картинки, но из-за этого происходят фризы при изменениех размера окна
                gameShell.getGameField().pack();
//                Point canvasBounds = new Point(gameShell.getBoundsGame().width, gameShell.getBoundsGame().height);
                computeCellSize(cellCount, new Point(gameShell.getBoundsGame().width, gameShell.getBoundsGame().height));
//            }
        });


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
//                gc.fillOval(start.x, start.y, Cell.getSize(), Cell.getSize());
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


    public void computeCellSize(int cellCount, Point canvasSize) {
        //TODO [DEBUG] не верно выравнивает число клеток, если уменьшать по х
        int size = (int) Math.ceil((double) Math.min(canvasSize.x, canvasSize.y) / cellCount);
        Cell.setSizeCoeff((double) size / Cell.NORMAL_SIZE); //Расчет размера каждой ячейки
    }
}