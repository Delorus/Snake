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
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;
    public Control control;
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
    private boolean transparentBorder;

    public class Control {
        public final int up;
        public final int down;
        public final int right;
        public final int left;

        public Control(int up, int down, int right, int left) {
            this.up = up;
            this.down = down;
            this.right = right;
            this.left = left;
        }

        public boolean contains(int key) {
            return key == up || key == down || key == right || key == left;
        }

    }

    public Snake(Grid grid, int posX, int posY, String name, Color color, int length, int up, int down, int right, int left) {
//        resetScore();
        this.name = name;
        this.color = color;
        this.length = length; // Начальная длина змеи
        this.grid = grid;
        tail = new SimpleQueue<>();
        pierce = grid.getCell(posX, posY); // Установка головы змейки по указанным координатам
        pierce.setStatus(State.SNAKE, this);
        tail.add(pierce); // Добавление текущей частички змейки в хвост
        canMove = true;
        foods = new SimpleQueue<>();
        this.control = new Control(up, down, right, left);
        //TODO убрать
        transparentBorder = true;
    }


    public boolean move() {
        try {
//            if (transparentBorder) {
//                if (pierce.getPosX() == )
//            }

            //TODO [REFACTOR] изменить способ передвижения
            Point buff = new Point(pierce.getPosition());
            if (direct == control.up) {
                if (transparentBorder && pierce.getPosY() == 0) {
                    buff.y = grid.getHeight();
                }
                pierce = grid.getCell(buff.x, buff.y - 1);
            } else if (direct == control.down) {
                if (transparentBorder && pierce.getPosY() == grid.getHeight() - 1) {
                    buff.y = -1;
                }
                pierce = grid.getCell(buff.x, buff.y + 1);
            } else if (direct == control.left) {
                if (transparentBorder && pierce.getPosX() == 0) {
                    buff.x = grid.getWidth();
                }
                pierce = grid.getCell(buff.x - 1, buff.y);
            } else if (direct == control.right) {
                if (transparentBorder && pierce.getPosX() == grid.getWidth() - 1) {
                    buff.x = -1;
                }
                pierce = grid.getCell(buff.x + 1, buff.y);
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
                        (direct == control.right && newDirect == control.left) ||
                        (direct == control.left && newDirect == control.right) ||
                        (direct == control.up && newDirect == control.down) ||
                        (direct == control.down && newDirect == control.up))) {
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
