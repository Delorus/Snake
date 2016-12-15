package ru.sherb.Snake.controller;

import org.eclipse.swt.graphics.*;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.model.*;

import java.io.InputStream;

/**
 * Класс предназначен для визуализации логической части игры. Отрисовка графических объектов происходит на ЦП.
 * <p>
 * Created by sherb on 08.12.2016.
 */
public class RenderCPU implements IRender {
    /**
     * Ссылка на игру, с которой будут браться данные для отрисовки
     */
    private Grid grid;
    /**
     * Ссылка на поле, на котором нужно нарисовать графику
     */
    private Drawable field;
    private GC gc;

    /**
     * @param grid  логическая часть игры, из которой берутся данные
     * @param field поля для рисования
     */
    public RenderCPU(Grid grid, Drawable field) {
        this.grid = grid;
        this.field = field;
        gc = new GC(field);
    }

    @Override
    public void init() {
        // В этом классе нечего инициализировать
    }

    @Override
    public void paint() {
        Point start = new Point(0, 0);
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                gc.setBackground(new Color(null,
                        grid.getCell(i, j).getColor().getRed(),
                        grid.getCell(i, j).getColor().getGreen(),
                        grid.getCell(i, j).getColor().getBlue()));
                gc.fillRectangle(start.x, start.y, Cell.getSize(), Cell.getSize());
                start.y += Cell.getSize();
            }
            start.y = 0;
            start.x += Cell.getSize();
        }
    }

}
