package ru.sherb.Snake.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.model.Cell;
import ru.sherb.Snake.model.Game;
import ru.sherb.Snake.model.Grid;
import ru.sherb.Snake.model.MovementController;
import ru.sherb.Snake.model.Snake;
import ru.sherb.Snake.setting.Setting;
import ru.sherb.Snake.view.CreatePlayerDialog;
import ru.sherb.Snake.view.GameShell;

import java.awt.*;
import java.util.Map;

/**
 * Created by sherb on 27.10.2016.
 */
public class GameShellController {

    public void open() {
        Setting setting = Setting.getInstance();

        Point defaultSize = new Point(setting.getScreenSizeX(), setting.getScreenSizeY());
        GameShell gameShell = new GameShell(Main.display, defaultSize, setting.isFullscreen());

        if (Main.isDebug()) System.out.println("Game area = " + gameShell.getGameArea());

        CreatePlayerDialog dialog = new CreatePlayerDialog(gameShell);
        dialog.open();

        while (!dialog.isDisposed()) {
            if (!Main.display.readAndDispatch()) {
                Main.display.sleep();
            }
        }
        if (gameShell.isDisposed()) {
            new MainShellController().open();
            return;
        }

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
        // TODO: 11.03.2018 добавить игрокам id
        Map<Integer, Integer> control = setting.getControlOver(setting.getPlayerNames()[0]);
        String player1Name = dialog.getPlayers().get(0).getName();
        Color player1Color = dialog.getPlayers().get(0).getColor();
        Snake player1 = new Snake(grid, 0, grid.getHeight() - 1, player1Name, player1Color, 3);
        MovementController player1Controller = new MovementController(player1, control);
//        Snake player2 = new Snake(grid, 0, 0, "player2", java.awt.Color.MAGENTA, 3);
        final Game game = new Game(grid, fruitColor, player1);
        Updater updater = new Updater(game, gameShell);
        updater.init();

        gameShell.addListener(SWT.Close, e -> {
            updater.stop();
            new MainShellController().open();
        });

        gameShell.getGameField().addListener(SWT.KeyDown, e -> {
            if (player1Controller.containsKey(e.keyCode)) {
                //TODO добавить сохранение до двух нажатий в очередь
                player1Controller.changeDirection(e.keyCode);
            } else {
                //TODO временное решение, пока не будет создано окно паузы
                if (e.keyCode == 27) {
                    if (updater.isPause()) {
                        if (Main.isDebug()) System.out.println("end pause");
                        updater.start();
                    } else {
                        if (Main.isDebug()) System.out.println("start pause");
                        updater.pause();
                    }
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


        gameShell.layout();
        gameShell.open();
        gameShell.getGameField().setFocus();

        // Главный поток обновление графического окна.
        Main.display.asyncExec(updater);
    }

    private void computeCellSize(int minCellCount, Point canvasSize) {
        //TODO [DEBUG] не верно выравнивает число клеток, если уменьшать по х
        int size = (int) Math.ceil((double) Math.min(canvasSize.x, canvasSize.y) / minCellCount);
        if (Main.isDebug()) System.out.println("Размер ячейки = " + size);
        Cell.setSizeCoeff((double) size / Cell.NORMAL_SIZE); //Расчет размера каждой ячейки
    }
}
