package ru.sherb.Snake.model;

import ru.sherb.Snake.Main;

import java.awt.*;

/**
 * Created by sherb on 11.10.2016.
 */

public class Grid implements Colorable {
    private Cell[][] grid;
    private Color color;

    public Grid(int width, int height, Color color) {
        this.color = color;
        grid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Cell(State.EMPTY, i, j, this.color);
            }
        }
        if (Main.debug) System.out.println("Создано игровое поле " + getWidth() + " на " + getHeight());
    }

    public Cell getCell(int x, int y) {
        return grid[x][y];
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        if (grid[0] == null) {
            return 0;
        }
        return grid[0].length;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
