package ru.sherb.Snake.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.model.Cell;
import ru.sherb.Snake.model.Game;
import ru.sherb.Snake.model.Grid;
import ru.sherb.Snake.model.MovementController;
import ru.sherb.Snake.model.Snake;
import ru.sherb.Snake.util.Setting;
import ru.sherb.Snake.view.GameShell;

import java.awt.*;

/**
 * Created by sherb on 27.10.2016.
 */
public class GameShellController {
    private GameShell gameShell;

    public GameShellController() {

        Setting setting = Setting.getInstance();

        Point defaultSize = new Point(setting.getScreenSizeX(), setting.getScreenSizeY());
        gameShell = new GameShell(Main.display, defaultSize, setting.isFullscreen());
        gameShell.open();
        gameShell.layout();
        gameShell.getGameField().setFocus();

        if (Main.isDebug()) System.out.println("Game area = " + gameShell.getGameArea());

        //16:9
        assert setting.getGrid_HEIGHT() % 3 == 0;
        int cellCountHeight = setting.getGrid_HEIGHT(); // Количество ячеек по вертикали
        int cellCountWidth = cellCountHeight / 9 * 16; // Количество ячеек по горизонтали
        int size = gameShell.getGameArea().height / cellCountHeight;
        Cell.setSizeCoeff((double) size / Cell.NORMAL_SIZE);
        //TODO [DEBUG] не верно работает функция масштабирования
        //количество ячеек по горизонтали задается в зависимости от начального размера окна
//        computeCellSize(Math.min(cellCountHeight, cellCountWidth), new Point(gameShell.getGameArea().width, gameShell.getGameArea().height));

        Grid grid = new Grid(cellCountWidth, cellCountHeight, setting.getGrid_COLOR());
        Color fruitColor = setting.getFruit_COLOR();
        String player1Name = setting.getPlayerNames()[0];
        Snake player1 = new Snake(grid, 0, grid.getHeight() - 1, player1Name, setting.getPlayer_COLOR(player1Name), 3);
        MovementController player1Controller = new MovementController(player1, setting.getControlOver(player1Name));
//        Snake player2 = new Snake(grid, 0, 0, "player2", java.awt.Color.MAGENTA, 3);
        final Game game = new Game(grid, fruitColor, player1);
        Updater updater = new Updater(game, gameShell);
        updater.init();

        gameShell.addListener(SWT.Close, e -> {
            updater.stop();
            new MainShellController();
        });

        gameShell.getGameField().addListener(SWT.KeyDown, e -> {
            if (player1Controller.containsKey(e.keyCode)) {
                //TODO добавить сохранение до двух нажатий в очередь
                player1Controller.changeDirection(e.keyCode);
            } else {
                //TODO временное решение, пока не будет создано окно паузы
                if (e.keyCode == 27) {
                    if (Main.isDebug()) System.out.println("pause");
                    if (updater.isPause()) updater.start(); else updater.pause();
                }
            }
        });

        if (Main.isDebug()) System.out.println("Размер окна = " + gameShell.getSize());
        gameShell.addListener(SWT.Resize, event -> {
            //TODO [DEBUG] пока лучший вариант для стирания старой картинки, но из-за этого происходят фризы при изменениех размера окна
//            gameShell.getGameField().pack();
            computeCellSize(Math.min(cellCountHeight, cellCountWidth), new Point(gameShell.getGameArea().width, gameShell.getGameArea().height));
            if (Main.isDebug()) {
                System.out.println("Размер окна = " + gameShell.getSize());
                System.out.println("Game area = " + gameShell.getGameArea());
            }
        });

        // Главный поток обновление графического окна.
        new Thread(updater).start();
    }




    public void computeCellSize(int minCellCount, Point canvasSize) {
        //TODO [DEBUG] не верно выравнивает число клеток, если уменьшать по х
        int size = (int) Math.ceil((double) Math.min(canvasSize.x, canvasSize.y) / minCellCount);
        if (Main.isDebug()) System.out.println("Размер ячейки = " + size);
        Cell.setSizeCoeff((double) size / Cell.NORMAL_SIZE); //Расчет размера каждой ячейки
    }
}
