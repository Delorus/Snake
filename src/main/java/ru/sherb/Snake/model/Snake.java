package ru.sherb.Snake.model;


import java.awt.*;
import java.util.LinkedList;

/**
 * Реализация змейки через связанный список
 * <p>
 * Created by sherb on 12.10.2016.
 */
public class Snake extends GameObject implements Controllable {
    /**
     * Длина змейки, включая голову
     */
    private int length;
    /**
     * Сама змея, последний элемент является головой, первый - хвостом
     */
    private LinkedList<Cell> tail;
    /**
     * Очки за игру
     */
    private int score;
    /**
     * Ссылка на игрокое поле
     */
    private Grid grid;
    /**
     * Текущее направление движения змейки (см. {@link Controllable}
     */
    private int direct;
    //TODO заменить цвет на тональность текстурки
    /**
     * Текущий цвет змейки
     */
    private Color color;
    /**
     * Хранит координаты фруктов, которые проглотила змейка
     */
    private LinkedList<Point> foods;
    //TODO [DEBUG] возвращает длину последнего съеденного фрукта, т.е. если съесть сначала фрукт на +2 а потом на +1, то оба раза увеличиться на +1
    /**
     * Число, на которое вырастит змейка, после переваривания пищи
     */
    private int countLength;
    //TODO [ВОЗМОЖНО] сделать ввод пользователем своего имени при старте игры
    private String name;
    /**
     * Блокирование изменение направления движения, если игрок уже его выбрал, а змейка еще не походила
     * На всякий случай потокобезопасна, т.к. игрок в теории может одновременно попытаться задать несколько направлений движения
     */
    private volatile boolean lockChangeDirect;
    /**
     * Показывает, что змейка была изменена
     * Нужен для того что бы внешние потоки могли видеть, когда нужно обновить информацию
     */
    private volatile boolean changed;
    /**
     * Коэффициент скорости змейки, где
     * <ul>
     * <li>1 - максимальная скорость;</li>
     * <li> 0 - не двигается.</li>
     * </ul>
     */
    private double speedk;
    /**
     * Количество клеток, которые змейка проходит за один игровой шаг.
     * Дробные числа округляются к ближайшему наименьшему значению
     */
    private double speed;
    /**
     * Временный флажок, показывающий что змейка на этом ходу съела фрукт
     */
    private boolean eatFruit;

    public Snake(Grid grid, int posX, int posY, String name, Color color, int length) {
        this.name = name;
        this.color = color;
        this.length = length;
        this.grid = grid;
        tail = new LinkedList<>();
        tail.add(grid.getCell(posX, posY));
        tail.getLast().setStatus(State.SNAKE, this);
        lockChangeDirect = false;
        foods = new LinkedList<>();
        changed = true;
        speed = 1;
        speedk = .11;
    }


    public boolean move() {
        if (step() == 0) return true; // пропуск хода

        if (!moveToDirect(tail.getLast(), direct)) return false; // змейка влепилась в стенку
        lockChangeDirect = false;

        // Перемещение хвоста змейки в след за головой, либо рост змейки
        if (tail.size() > length) {
            tail.peek().setStatus(State.EMPTY, grid);
            tail.remove();
            if (foods.peek() != null) {
                // Проверка, является ли хвост змейки на самом деле проглоченным фруктом, если да, то змейка растет
                if (tail.peek().getPosition().equals(foods.peek())) {
                    grow();
                }
            }
        }

        return collision(tail.getLast());
    }

    /**
     * Определяет, нужно ли двигаться на этом ходу.
     * Каждый вызов метода обрабатывает новый ход, поэтому, что бы избежать ошибок нужно вызывать этот метод один раз за ход
     *
     * @return 0 - если двигаться не надо, в противном случае 1
     */
    private int step() {
        int maxSpeed = (int) Math.ceil(speedk);
        final double E = 0.1;
        // Формула определяет, будет ли на этом ходу ходить змейка
        // Если ее текущая скорость достигла максимальной, то скорости присваивается начальная скорость
        // Если ее текущая скорость отличается от максимальной на погрешность E, то скорости присваивается максимальная скорость
        // В остальных случаях к текущей скорости прибавляется коэффициент скорости
        speed = Double.compare(speed, (double) maxSpeed) == 0
                ? speedk
                : ((Math.abs(maxSpeed - speed) < E)
                ? maxSpeed
                : speed + speedk);
        return (int) Math.floor(speed);
    }

    /**
     * Двигает змейку в указанном направлении
     *
     * @return {@code true} - если змейка успешно передвинулась, {@code false} - если змейка столкнулась со стенкой
     */
    private boolean moveToDirect(Cell head, int direct) {
        Point buff = new Point(head.getPosition());
        // Если направление на право или вниз, то знак положительный, иначе отрицательный
        int sign = (direct & 0b0101) == 0 ? +1 : -1;
        int x = buff.x + Integer.bitCount((direct & 0b1100)) * sign;
        int y = buff.y + Integer.bitCount((direct & 0b0011)) * sign;

        if (isOutOfBorder(x, y, grid)) {
            if (grid.isTransparentBorder()) {
                x = x < 0 ? grid.getWidth() - 1 : (x > grid.getWidth() - 1 ? 0 : x);
                y = y < 0 ? grid.getHeight() - 1 : (y > grid.getHeight() - 1 ? 0 : y);
            } else {
                return false;
            }
        }
        tail.add(grid.getCell(x, y));
        return true;
    }

    private boolean isOutOfBorder(int x, int y, Grid grid) {
        return x < 0 || y < 0 || x > grid.getWidth() - 1 || y > grid.getHeight() - 1;
    }

    /**
     * Проверка чем была раньше ячейка, на которую встала змейка.
     * <ul>
     *     <li>Ячейка была пустой -- установка в эту ячейку голову змейки</li>
     *     <li>В ячейке был фрукт -- добавление фрукта в съеденные</li>
     *     <li>В ячейке была змея -- возвращается {@code false}</li>
     * </ul>
     * @return {@code false} если змейка наткнулась на змейку, иначе {@code true}
     */
    private boolean collision(Cell head) {
        switch (head.getStatus()) {
            case EMPTY:
                head.setStatus(State.SNAKE, this);
                break;
            case FRUIT:
                //сделать возможность получить фрукт из змейки
                //TODO [REFACTOR] временное решение проблемы
                eatFruit = true;
                head.setColor(Color.MAGENTA);
                foods.add(head.getPosition());
                break;
            case SNAKE:
                return false;
        }
        return true;
    }


    public boolean eatFruit() {
        return eatFruit;
    }


    /**
     * Конверция съеденного фрукта в размер змейки
     */
    private void grow() {
        length += countLength;
        changed = true;
        foods.remove();
    }

    public void setDirect(int newDirect) {
        if (!lockChangeDirect && !((direct == newDirect) || direct == Controllable.reverseDirect(newDirect))) {
            direct = newDirect;
            lockChangeDirect = true;
        }
    }


    //TODO съедать фрукт
    public void eat(int count) {
        countLength = count;
        eatFruit = false;
    }

    public void addScore(int count) {
        this.score += count;
        changed = true;
    }

    public void resetScore() {
        this.score = 0;
        changed = true;
    }

    public int getScore() {
        return score;
    }

    public boolean isThisSnake(int x, int y) {
        return !(y < 0 || x < 0 || x > (grid.getWidth() - 1) || y > (grid.getHeight() - 1)) && tail.contains(new Cell(State.SNAKE, x, y, color));
    }

    public boolean isThisSnake(Cell cell) {
        return isThisSnake(cell.getPosX(), cell.getPosY());
    }

    @Override
    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public boolean isChanged() {
        return changed;
    }

    public synchronized void setChanged(boolean changed) {
        this.changed = changed;
    }

    public int getLength() {
        return length;
    }

    public void lockChangeDirect() {
        lockChangeDirect = true;
    }

    public void unlockChangeDirect() {
        lockChangeDirect = false;
    }
}
