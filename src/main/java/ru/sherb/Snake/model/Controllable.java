package ru.sherb.Snake.model;

/**
 * <p>Интерфейс содержит константы направления движения:
 * <ul>
 * <li>{@code UP} = 0</li>
 * <li>{@code DOWN} = 1</li>
 * <li>{@code LEFT} = 2</li>
 * <li>{@code RIGHT} = 3</li>
 * </ul></p>
 *
 * <p>Так же тут находятся методы для работы с ними ({@link Controllable#move()}; {@link Controllable#setDirect(int)})</p>
 * Created by sherb on 07.11.2016.
 */
public interface Controllable {
    int UP = 0;
    int DOWN = 1;
    int LEFT = 2;
    int RIGHT = 3;

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
//    boolean moveTo(int newDirect);
}
