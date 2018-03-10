package ru.sherb.Snake.model;

import ru.sherb.Snake.Main;

import java.awt.*;

/**
 * Created by sherb on 11.10.2016.
 */

public class Grid implements Colorable {
    private final Cell[][] grid;
    //TODO [REFACTOR] переместить свойство в Cell
    private final Color color;
    private final boolean transparentBorder;

    public Grid(int width, int height, Color color/*, boolean transparentBorder*/) {
        this.color = color;
        grid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Cell(State.EMPTY, i, j, this.color);
            }
        }
        this.transparentBorder = true;
        if (Main.isDebug())
            System.out.println("Создано игровое поле " + getWidth() + " на " + getHeight());
    }

    public Cell getCell(int x, int y) {
        return grid[x][y];
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        // поле квадратное, поэтому проверяется размер только первого элемента
        if (grid[0] == null) {
            return 0;
        }
        return grid[0].length;
    }

    public boolean isTransparentBorder() {
        return transparentBorder;
    }

    @Deprecated
    @Override
    public Color getColor() {
        return color;
    }
}
