package ru.sherb.Snake.model;


import ru.sherb.Snake.util.SimpleQueue;

import java.awt.Color;
import java.awt.Point;

/**
 * Реализация змейки через связанный список
 * <p>
 * Created by sherb on 12.10.2016.
 */
public class Snake implements Colorable {
    @Deprecated
    public static final int UP = 0;
    @Deprecated
    public static final int RIGHT = 1;
    @Deprecated
    public static final int DOWN = 2;
    @Deprecated
    public static final int LEFT = 3;
    private int length;// = 3; // длина змеи с головой, длина хвоста = length - 1, начальный размер 3?
    private SimpleQueue<Cell> tail;
    private Cell pierce; // элемент змейки
    private int score; // очки за игру
    private Grid grid; // Поле на котором ползает змейка. Отказаться от этого
    private int direct;
    private Color color;
    private SimpleQueue<Point> foods; // Хранит адрес фруктов, которые проглатила змейка
    private int countLength;
    //TODO сделать ввод пользователем своего имени при старте игры
    private String name;
    //TODO [DEBUG] удалить
    // временное решение бага, пока не изменится способ управления змейкой
    private boolean canMove;

    public Snake(Grid grid, int posX, int posY, String name, Color color, int length) {
        this.name = name;
        resetScore();
        this.color = color;
        this.length = length; // Начальная длина змеи
        this.grid = grid;
        tail = new SimpleQueue<>();
        pierce = grid.getCell(posX, posY); // Установка головы змейки по указанным координатам
        pierce.setStatus(State.SNAKE, this);
        tail.add(pierce); // Добавление текущей частички змейки в хвост
        canMove = true;
        foods = new SimpleQueue<>();
    }


    public boolean move() {
        //TODO [REFACTOR] избавиться от этой бяки
        try {
            switch (direct) {
                case UP:
                    pierce = grid.getCell(pierce.getPosX(), pierce.getPosY() - 1);
                    break;
                case RIGHT:
                    pierce = grid.getCell(pierce.getPosX() + 1, pierce.getPosY());
                    break;
                case DOWN:
                    pierce = grid.getCell(pierce.getPosX(), pierce.getPosY() + 1);
                    break;
                case LEFT:
                    pierce = grid.getCell(pierce.getPosX() - 1, pierce.getPosY());
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            canMove = false;
            return false;
        }

        tail.add(pierce);
        if (tail.size() > length) {
            tail.peek().setStatus(State.EMPTY, grid);
            tail.remove();
            if (foods.peek() != null) {
                if (tail.peek().getPosX() == foods.peek().x && tail.peek().getPosY() == foods.peek().y) {
                    length += countLength;
                    foods.remove();
                }
            }
        }

        canMove = true;
        return true;
    }

    public void moveTo(int newDirect) {
        //TODO [REFACTOR] попробовать как-нибудь сократить условие
        if (canMove &&
                !((direct == newDirect) ||
                        (direct == RIGHT && newDirect == LEFT) ||
                        (direct == LEFT && newDirect == RIGHT) ||
                        (direct == UP && newDirect == DOWN) ||
                        (direct == DOWN && newDirect == UP))) {
            direct = newDirect;
            canMove = false;
        }
    }


    //TODO съедать фрукт, а не его позицию
    public void eat(Point location, int count) {
        foods.add(location.getLocation());
        countLength = count;
    }

    public void addScore(int count) {
        this.score += count;
    }

    public void resetScore() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public Cell getPierce() {
        return pierce;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void canMove(boolean canMove) {
        this.canMove = canMove;
    }


//    public boolean isThisSnake(int x, int y) {
//        if (y < 0 || x < 0 || x > (grid.getWidth() - 1)  || y > (grid.getHeight() - 1)) {
//            return false;
//        }
//        return tail.contains(new Cell(State.SNAKE, x, y, color));
//    }

//    public boolean isThisSnake(Cell cell) {
//        return isThisSnake(cell.getPosX(), cell.getPosY());
//    }

    @Override
    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
