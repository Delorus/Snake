package ru.sherb.Snake.model;

import java.awt.*;

/**
 * Created by sherb on 10.10.2016.
 */

//TODO сделать из ячеек контейнеры, которые будут хранить ссылки на объекты, которые находятся в этой ячейке в текущий момент
public class Cell extends GameObject {
    public static final int NORMAL_SIZE = 4;
    private static double sizeCoeff = 1;  //потому что маштаб должен сохраняться во всех объектах
    private static int size = (int) Math.round(sizeCoeff * NORMAL_SIZE); //NotNull
    private Color color;
    private final Point position;
    private State status;

    private static void resize() {
        size = (int) Math.round(sizeCoeff * NORMAL_SIZE);
    }

    //Всегда при изменении коэффициента делать перерасчет размера
    public static void setSizeCoeff(double sizeCoeff) {
        Cell.sizeCoeff = sizeCoeff;
        resize();
    }

    public static int getSize() {
        return size;
    }

    public Cell(State status, int x, int y, Color color) {
        resize();
        this.status = status;
        position = new Point(x, y);
        this.color = color;
    }

    public Cell(State status, int x, int y, Color color, int sizeCoeff) {
        this(status, x, y, color);
        Cell.setSizeCoeff(sizeCoeff);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setStatus(State status, Colorable o) {
        this.status = status;
        color = o.getColor();
    }

    public Color getColor() {
        return color;
    }

    public int getPosX() {
        return position.x;
    }

    public int getPosY() {
        return position.y;
    }

    public Point getPosition() {
        return position;
    }

    public State getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return !(position != null ? !position.equals(cell.position) : cell.position != null);

    }

    @Override
    public int hashCode() {
        return position != null ? position.hashCode() : 0;
    }
}

