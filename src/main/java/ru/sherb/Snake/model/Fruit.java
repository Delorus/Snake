package ru.sherb.Snake.model;

import java.awt.*;

/**
 * Created by sherb on 12.10.2016.
 */
//TODO сделать наследование от ячейки
public class Fruit implements Colorable {
    private Grid grid;
    private Cell fruit;
    private int scoreInc; // число, на которое увеличиваются очки, когда змейка съест фрукт
    private int lengthSnakeInc; //число, на которое увеличится размер змейки, когда она съест фрукт
    private int existOfTime; //время, которое будет существовать фрукт ms
    private boolean exist;
    private Color color;

    public Fruit(Grid grid/*, byte color*/) {
//        fruit = new Cell(State.FRUIT/*, color*/);
        this.grid = grid;
        scoreInc = 1;
        lengthSnakeInc = 1;
        exist = false;

    }

    //TODO [REFACTOR] сделать что-нибудь с этим
    private Fruit(Grid grid, Cell fruit, int scoreInc, int lengthSnakeInc, int existOfTime, boolean exist, Color color) {
        this.grid = grid;
        this.fruit = fruit;
        this.scoreInc = scoreInc;
        this.lengthSnakeInc = lengthSnakeInc;
        this.existOfTime = existOfTime;
        this.exist = exist;
        this.color = color;
    }


    // Конструктор клонирования
    public Fruit(Fruit other) {
        this(other.grid, other.fruit, other.scoreInc, other.lengthSnakeInc, other.existOfTime, other.exist, other.color);
    }
    public boolean createFruit(int x, int y, Color color) {
        //TODO доделать создания фрукта, в том числе начать отсчет времени его существования
        if (grid.getCell(x, y).getStatus() != State.EMPTY) {
            return false;
        }
        fruit = grid.getCell(x, y);
        this.color = color;
        fruit.setStatus(State.FRUIT, this);
        exist = true;
        return true;
    }

    public boolean createFruit(int x, int y, Color color, int existOfTime, int scoreInc, int lengthSnakeInc) {
        this.existOfTime = existOfTime;
        this.scoreInc = scoreInc;
        this.lengthSnakeInc = lengthSnakeInc;
        return createFruit(x, y, color);
    }

    public void setScoreInc(int scoreInc) {
        this.scoreInc = scoreInc;
    }

    public void setLengthSnakeInc(int lengthSnakeInc) {
        this.lengthSnakeInc = lengthSnakeInc;
    }

    public void setExistOfTime(int existOfTime) {
        this.existOfTime = existOfTime;
    }

//    public int getScoreInc() {
//        return scoreInc;
//    }
//
//    public int getLengthSnakeInc() {
//        return lengthSnakeInc;
//    }

    public int getExistOfTime() {
        return existOfTime;
    }

    public void eatenBy(Snake snake) {
        snake.addScore(scoreInc);
        snake.eat(fruit.getPosition(), lengthSnakeInc);
        exist = false;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
