package ru.sherb.Snake.model;

/**
 * <p>Интерфейс содержит константы направления движения:
 * <ul>
 * <li>{@code UP} = 1</li>
 * <li>{@code DOWN} = 2</li>
 * <li>{@code LEFT} = 4</li>
 * <li>{@code RIGHT} = 8</li>
 * </ul></p>
 *
 * <p>Так же тут находятся методы для работы с ними ({@link Controllable#move()}; {@link Controllable#setDirect(int)})</p>
 * <p>
 * Created by sherb on 07.11.2016.
 */
public interface Controllable {
    int UP = 1;
    int DOWN = 2;
    int LEFT = 4;
    int RIGHT = 8;

    /**
     * Изменяет направление движения на обратное
     * @param direct текущее направление движения
     * @return Движение обратное текущему (например, если движение было на право, то возвращает движение на лево)
     */
    static int reverseDirect(int direct) {
        int highBits = direct & 0b1100;
        int lowBits = direct & 0b0011;
        highBits = highBits == 0 ? 0 : highBits ^ 0b1100;
        lowBits = lowBits == 0 ? 0 : lowBits ^ 0b0011;
        return highBits | lowBits;
    }

    /**
     * Устанавливается новое направление движение согласно константам в {@link Controllable}
     * @param newDirect новое направление движение (например направление вверх - {@link Controllable#UP})
     */
    void setDirect(int newDirect);

    /**
     * Реализуется алгоритм движения, согласно установленому направлению
     * @return {@code true} если движение прошло успешно (объект не натолкнулся на препятствие)
     */
    boolean move();
}
