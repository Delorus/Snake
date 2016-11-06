package ru.sherb.Snake.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.model.Cell;
import ru.sherb.Snake.model.Game;
import ru.sherb.Snake.model.Grid;
import ru.sherb.Snake.model.Snake;
import ru.sherb.Snake.util.FPSCounter;
import ru.sherb.Snake.util.SettingHelper;
import ru.sherb.Snake.util.Timer;
import ru.sherb.Snake.view.DialogForm;
import ru.sherb.Snake.view.GameShell;

import java.awt.*;
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
//        Properties setting = new Properties();
//        try (FileInputStream fin = new FileInputStream("Snake.properties")) {
//            setting.load(fin);
//        } catch (FileNotFoundException e) {
//            //TODO перенести создание файла во время загрузки программы
//            try (FileOutputStream fout = new FileOutputStream("Snake.properties")) {
//                //TODO доделать после того как будет создана меню настроек
//                //TODO [ВОЗМОЖНО] сделать все параметры в виде констант
//                setting.setProperty("ScreenSizeX", "864");
//                setting.setProperty("ScreenSizeY", "486");
//                setting.setProperty("Fullscreen", "false");
//
//                setting.setProperty("Grid_HEIGHT", "18");
//
//                setting.setProperty("Player1_UP", String.valueOf(SWT.ARROW_UP));
//                setting.setProperty("Player1_DOWN", String.valueOf(SWT.ARROW_DOWN));
//                setting.setProperty("Player1_RIGHT", String.valueOf(SWT.ARROW_RIGHT));
//                setting.setProperty("Player1_LEFT", String.valueOf(SWT.ARROW_LEFT));
//
//                setting.setProperty("Player1_COLOR", String.valueOf(Color.GREEN.getRGB()));
//                setting.setProperty("Grid_COLOR", String.valueOf(Color.WHITE.getRGB()));
//                setting.setProperty("Fruit_COLOR", String.valueOf(Color.RED.getRGB()));
//                //...
//                setting.store(fout, "Snake setting");
//            } catch (IOException e1) {
//                if (Main.debug) e1.printStackTrace();
//            }
//        } catch (IOException e) {
//            if (Main.debug) e.printStackTrace();
//        }
        SettingHelper setting = new SettingHelper("Snake.properties");
        try {
            setting.loadOrDefault();
        } catch (IOException e) {
            if (Main.debug) e.printStackTrace();
        }


        Point bounds = new Point(setting.getScreenSizeX(), setting.getScreenSizeY());
        gameShell = new GameShell(Main.display, bounds, setting.isFullscreen());
        gameShell.open();
        gameShell.layout();
        gameShell.getGameField().setFocus();

        if (Main.debug) System.out.println("Game area = " + gameShell.getGameArea());


        //16:9
        assert setting.getGrid_HEIGHT() % 3 == 0;
        int cellCountHeight = setting.getGrid_HEIGHT(); // Количество ячеек
        int cellCountWidth = cellCountHeight / 9 * 16;
        //TODO [DEBUG] не верно работает функция масштабирования
        //количество ячеек по горизонтали задается в зависимости от начального размера окна
        computeCellSize(Math.min(cellCountHeight, cellCountWidth), new Point(gameShell.getGameArea().width, gameShell.getGameArea().height));
        Grid grid = new Grid(cellCountWidth, cellCountHeight, setting.getGrid_COLOR());

        Color fruitColor = setting.getFruit_COLOR();
        //TODO переместить управление змейкой в отдельный класс
        Snake player1 = new Snake(grid, 0, grid.getHeight() - 1, "player1", setting.getPlayer1_COLOR(), 3,
                setting.getPlayer1_UP(),
                setting.getPlayer1_DOWN(),
                setting.getPlayer1_RIGHT(),
                setting.getPlayer1_LEFT());
//        Snake player1 = new Snake(grid, grid.getWidth() / 2, grid.getHeight() / 2, "player1", java.awt.Color.GREEN, 3);
//        Snake player2 = new Snake(grid, 0, 0, "player2", java.awt.Color.MAGENTA, 3);
        final Game game = new Game(grid, fruitColor, player1);

        GC gc = new GC(gameShell.getGameField());
        new Thread(game).start();
        //TODO избавиться от состояния гонок
        new Thread(() -> {

            //TODO [REFACTOR] убрать после оптимизации рендеринга
            Timer timer = new Timer(1000);
            FPSCounter counter = new FPSCounter(System.out);
            timer.setTask(counter::printCount);
            Thread timerThread = new Thread(timer);
            timerThread.start();


            while (grid.isActive()) {

                //TODO [REFACTOR] блокировка FPS на ~60, убрать после оптимизации рендеринга
                try {
                    Thread.sleep(17);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                paint(grid, gc);

                counter.incCount();

                if (!timerThread.isAlive()) {
                    timerThread = new Thread(timer);
                    timerThread.start();
                }


                if (gameShell.isDisposed()) {
                    if (Main.debug) System.out.println("Я закрыл игровое окно");
                    grid.setActive(false);
                    //Что бы поток мог нормально завершиться после закрытия окна
                    game.start();
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

        gameShell.getGameField().addListener(SWT.KeyDown, e -> {
            if (Main.debug) {
                gameShell.printMessage("Нажатие клавиши " + Integer.toString(e.keyCode, 16));
            }
            //TODO добавить сохранение до двух нажатий в очередь
            if (player1.control.contains(e.keyCode)) {
                player1.moveTo(e.keyCode);
            } else {
                //TODO временное решение, пока не будет создано окно паузы
                if (e.keyCode == 27) {
                    if (game.isPause()) {
                        game.start();
                    } else {
                        game.stop();
                    }
                }
            }
        });

        if (Main.debug) System.out.println("Размер окна = " + gameShell.getSize());
        gameShell.addListener(SWT.Resize, event -> {
            //TODO [DEBUG] пока лучший вариант для стирания старой картинки, но из-за этого происходят фризы при изменениех размера окна
//            gameShell.getGameField().pack();
            computeCellSize(Math.min(cellCountHeight, cellCountWidth), new Point(gameShell.getGameArea().width, gameShell.getGameArea().height));
            if (Main.debug) {
                System.out.println("Размер окна = " + gameShell.getSize());
                System.out.println("Game area = " + gameShell.getGameArea());
            }
        });

    }

    //TODO [THREAD] синхронизировать потоки обновление графического и логического состояния игры
    //TODO [REFACTOR] перенести рендеринг с ЦП на ГП
    public void paint(Grid grid, GC gc) {
        Point start = new Point(0, 0);
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                gc.setBackground(new org.eclipse.swt.graphics.Color(null,
                        grid.getCell(i, j).getColor().getRed(),
                        grid.getCell(i, j).getColor().getGreen(),
                        grid.getCell(i, j).getColor().getBlue()));
                gc.fillRectangle(start.x, start.y, Cell.getSize(), Cell.getSize());
//                gc.setForeground(new org.eclipse.swt.graphics.Color(null, 0, 0, 255));
//                gc.drawRectangle(start.x, start.y, Cell.getSize(), Cell.getSize());
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


    public void computeCellSize(int minCellCount, Point canvasSize) {
        //TODO [DEBUG] не верно выравнивает число клеток, если уменьшать по х
        int size = (int) Math.ceil((double) Math.min(canvasSize.x, canvasSize.y) / minCellCount);
        if (Main.debug) System.out.println("Размер ячейки = " + size);
        Cell.setSizeCoeff((double) size / Cell.NORMAL_SIZE); //Расчет размера каждой ячейки
    }
}
