package ru.sherb.Snake.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.model.*;
import ru.sherb.Snake.util.SettingHelper;
import ru.sherb.Snake.view.GameShell;

import java.awt.Color;
import java.io.IOException;

/**
 * Created by sherb on 27.10.2016.
 */
public class GameShellController {
    private GameShell gameShell;

    public GameShellController() {
        SettingHelper setting = SettingHelper.getInstance();
        setting.setPath("Snake.properties");
        try {
            setting.loadOrDefault();
        } catch (IOException e) {
            if (Main.debug) e.printStackTrace();
        }

        Point defaultSize = new Point(setting.getScreenSizeX(), setting.getScreenSizeY());
        gameShell = new GameShell(Main.display, defaultSize, setting.isFullscreen());
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
        Snake player1 = new Snake(grid, 0, grid.getHeight() - 1, "player1", setting.getPlayer1_COLOR(), 3);
        MovementController player1Controller = new MovementController(player1,
                setting.getPlayer1_UP(),
                setting.getPlayer1_DOWN(),
                setting.getPlayer1_LEFT(),
                setting.getPlayer1_RIGHT());
//        Snake player2 = new Snake(grid, 0, 0, "player2", java.awt.Color.MAGENTA, 3);
        final Game game = new Game(grid, fruitColor, player1);


        //TODO [ВОЗМОЖНО] объединить потоки игры и рендеринга в один
        new Thread(game).start();

        //Поток рендеринга игры
        //TODO избавиться от состояния гонок
        new Thread(new Updater(game, gameShell)).start();

        gameShell.addListener(SWT.Close, e -> new MainShellController());

        gameShell.getGameField().addListener(SWT.KeyDown, e -> {
            if (Main.debug) {
                gameShell.printMessage("Нажатие клавиши " + Integer.toString(e.keyCode, 16));
            }
            if (player1Controller.containsKey(e.keyCode)) {
                //TODO добавить сохранение до двух нажатий в очередь
                player1Controller.changeDirection(e.keyCode);
            } else {
                //TODO временное решение, пока не будет создано окно паузы
                if (e.keyCode == 27) {
                    if (game.isPause()) {
                        game.start();
                    } else {
                        game.pause();
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




    public void computeCellSize(int minCellCount, Point canvasSize) {
        //TODO [DEBUG] не верно выравнивает число клеток, если уменьшать по х
        int size = (int) Math.ceil((double) Math.min(canvasSize.x, canvasSize.y) / minCellCount);
        if (Main.debug) System.out.println("Размер ячейки = " + size);
        Cell.setSizeCoeff((double) size / Cell.NORMAL_SIZE); //Расчет размера каждой ячейки
    }
}
