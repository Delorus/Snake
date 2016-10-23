package ru.sherb.Snake.model;

import java.awt.*;

/**
 * Created by sherb on 10.10.2016.
 */


public class Cell implements Colorable {
    public static final int NORMAL_SIZE = 4;
    private static double sizeCoeff = 1;  //потому что маштаб должен сохраняться во всех объектах
    private static int size = (int) Math.round(sizeCoeff * NORMAL_SIZE); //NotNull
    private Color color;
    private Point position;
//    private int posX, posY;
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

//    public Cell(State status, int x, int y) {
//        resize();
//        this.status = status;
//        posX = x;
//        posY = y;
//    }

    public Cell(State status, int x, int y, Color color) {
//        this(status, x, y);
        resize();
        this.status = status;
        position = new Point(x, y);
//        posX = x;
//        posY = y;
        this.color = color;
    }

    public Cell(State status,int x, int y, Color color, int sizeCoeff) {
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
//        return posX;
    }

    public int getPosY() {
        return position.y;
//        return posY;
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

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Cell cell = (Cell) o;
//
//
//        return posX == cell.posX && posY == cell.posY;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = posX;
//        result = 31 * result + posY;
//        return result;
//    }
}

