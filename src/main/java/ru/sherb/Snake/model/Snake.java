package ru.sherb.Snake.model;


import java.awt.*;
import java.util.ArrayList;

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
    private int length;// = 3; //длина змеи с головой, длина хвоста = length - 1, начальный размер 3?
    private ArrayList<Cell> tail; //ArrayList пока не напишу свою очередь
    private Cell pierce; //элементы змейки
    private int score; //очки за игру
    private Grid grid; // Поле на котором ползает змейка. Отказаться от этого
//    private Direct direct; //Направление движение змейки
    private int direct;
    private Color color;
    //TODO сделать ввод пользователем своего имени при старте игры
    private String name;
    //TODO [DEBUG] удалить
    // временное решение бага, пока не изменится способ управления змейкой
    private boolean canMove;

    public Snake(Grid grid, int posX, int posY, String name, Color color) {
        this.name = name;
        resetScore();
        this.color = color;
        length = 3; //Начальная длина змеи
        this.grid = grid;
        tail = new ArrayList<>();
        pierce = grid.getCell(posX, posY); //Установка головы змейки в центр решетки
        pierce.setStatus(State.SNAKE, this);
        tail.add(pierce); // Добавление головы змейки в хвост -_-
//        direct = Direct.RIGHT;
        canMove = true;
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
//            grid.setActive(false);
            canMove = false;
            return false;
        }
        tail.add(pierce);
        if (tail.size() > length) {
            tail.get(0).setStatus(State.EMPTY, grid);
//            tail.peek().setStatus(State.EMPTY);
            tail.remove(0);
//            tail.poll();
        }
        canMove = true;
        return true;
    }

    public synchronized void moveTo(/*Direct*/ int newDirect) {
        //TODO [REFACTOR] попробовать как-нибудь сократить условие
        if ( canMove &&
                !((direct == newDirect) ||
                (direct == /*Direct.*/RIGHT && newDirect == /*Direct.*/LEFT) ||
                (direct == /*Direct.*/LEFT && newDirect == /*Direct.*/RIGHT) ||
                (direct == /*Direct.*/UP && newDirect == /*Direct.*/DOWN) ||
                (direct == /*Direct.*/DOWN && newDirect == /*Direct.*/UP))){
            direct = newDirect;
            canMove = false;
        }
    }

//    public Direct getDirect() {
//        return direct;
//    }
    public int getDirect() {
        return direct;
    }

    //TODO нужен ли этот метод тут?
//    private void collision() {
//        if (pierce.getStatus() == State.SNAKE) {
//            throw new IndexOutOfBoundsException("змейка съела себя");
//        }
        // ошибка в архитектуре
//        if (pierce.getStatus() == State.FRUIT) {
//        }
//    }

//    private void hasEaten()

    //TODO [ВОЗМОЖНО] вынести логику перемещения змейки в класс Game
//    @Deprecated
//    public void moveUp() {
//        pierce = grid[pierce.getPosX()][pierce.getPosY() - 1];
//        pierce.setStatus(State.SNAKE);
//        move();
//    }
//
//    @Deprecated
//    public void moveDown() {
//        pierce = grid[pierce.getPosX()][pierce.getPosY() + 1];
//        pierce.setStatus(State.SNAKE);
//        move();
//    }
//
//    @Deprecated
//    public void moveRight() {
//        pierce = grid[pierce.getPosX() + 1][pierce.getPosY()];
//        pierce.setStatus(State.SNAKE);
//        move();
//    }
//
//    @Deprecated
//    public void moveLeft() {
//        pierce = grid[pierce.getPosX() - 1][pierce.getPosY()];
//        pierce.setStatus(State.SNAKE);
//        move();
//    }

    public void grow(int count) {
        length += count;
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

    public Cell getHead() {
        return pierce;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void canMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isThisSnake(int x, int y) {
        if (y < 0 || x < 0 || x > (grid.getWidth() - 1)  || y > (grid.getHeight() - 1)) {
            return false;
        }
        return tail.contains(new Cell(State.SNAKE, x, y, color));
    }

    public boolean isThisSnake(Cell cell) {
        return isThisSnake(cell.getPosX(), cell.getPosY());
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

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

//enum Direct {
//    UP {
////        @Override
////        public void moveTo(Snake snake) {
////
////        }
//    },
//    RIGHT,
//    DOWN,
//    LEFT;
////    abstract public void moveTo(Snake snake);
//}