package ru.sherb.Snake.controller;

/**
 * Created by sherb on 08.12.2016.
 */
public interface IRender {
    /**
     * Метод нужен для инициализации начальных значений, вызывается один раз, перед запуском.
     */
    void init();

    /**
     * Метод предназначен для отрисовки графической части за один игровой фрейм.
     *
     */
    void paint();
}
