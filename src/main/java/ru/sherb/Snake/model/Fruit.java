package ru.sherb.Snake.model;

import java.awt.*;
import java.util.Random;

/**
 * Created by sherb on 12.10.2016.
 */
//TODO сделать наследование от ячейки
public class Fruit extends GameObject{
    private Grid grid;
    private Cell fruit;
    private int scoreInc; // число, на которое увеличиваются очки, когда змейка съест фрукт
    private int lengthSnakeInc; //число, на которое увеличится размер змейки, когда она съест фрукт
    private int existOfTime; //время, которое будет существовать фрукт, изменяется количестве ходов (например, для значения 2, змейка должна походить два хода, прежде чем он исчезнет)
    private boolean exist;
    private Color color;

    public Fruit(Grid grid) {
        this.grid = grid;
        exist = false;
    }


    /**
     * Метод предназначен для инициализации фрукта на игровом поле {@link Grid}
     * @param cell ячейка, которая станет фруктом
     * @param scoreInc значение на которое увеличится очки змейки, после съедании этого фрукта
     * @param lengthSnakeInc значение на которое увеличится размер змейки, после съедания этого фрукта, если равно -1, значит фрукт будет существовать пока не съедят
     * @param existOfTime время существования фрукта на поле, в тактах игры
     * @param color цвет, на который поменяется цвет указанной ячейки
     * @return {@code false} если фрукт уже существует на поле
     */
    public boolean createFruit(Cell cell, int scoreInc, int lengthSnakeInc, int existOfTime, Color color) {
        if (exist) return false;
        fruit = cell;
        this.color = color;
        this.scoreInc = scoreInc;
        this.lengthSnakeInc = lengthSnakeInc;
        this.existOfTime = existOfTime;
        fruit.setStatus(State.FRUIT, this);
        exist = true;
        return true;
    }

    /**
     * Метод предназначен для инизиализации фрукта на игровом поле, отличается от {@link #createFruit}  только тем, что фрукт создается на случайной ячейке
     * @param scoreInc значение на которое увеличится очки змейки, после съедании этого фрукта
     * @param lengthSnakeInc значение на которое увеличится размер змейки, после съедания этого фрукта, если равно -1, значит фрукт будет существовать пока не съедят
     * @param existOfTime время существования фрукта на поле, в тактах игры
     * @param color цвет, на который поменяется цвет указанной ячейки
     * @return {@code false} если фрукт уже существует на поле
     */
    public boolean createFruitRandPos(int scoreInc, int lengthSnakeInc, int existOfTime, Color color) {
        Cell buff;
        //работает только если на поле есть хотя бы одна пустая ячейка, иначе уйдет в бесконечный цикл
        do {
            buff = grid.getCell(new Random().nextInt(grid.getWidth()), new Random().nextInt(grid.getHeight()));
        } while (buff.getStatus() != State.EMPTY);
        return createFruit(buff, scoreInc, lengthSnakeInc, existOfTime, color);
    }

    public boolean decExistOfTime() {
        if (existOfTime > 0) {
            existOfTime--;
        }
        if (existOfTime == 0) {
            fruit.setStatus(State.EMPTY, grid);
            destroyFruit();
            return false;
        }
        return true;
    }

    public void eatenBy(Snake snake) {
        snake.addScore(scoreInc);
        snake.eat(fruit.getPosition(), lengthSnakeInc);
        //TODO [REFACTOR] неверное использование методов
        fruit.setStatus(State.SNAKE, () -> new Color(color.getRGB() + snake.getColor().getRGB()));
        destroyFruit();
    }

    private void destroyFruit() {
        fruit = null;
        color = null;
        scoreInc = 0;
        lengthSnakeInc = 0;
        existOfTime = 0;
        exist = false;
    }
    @Override
    public Color getColor() {
        return color;
    }
}
