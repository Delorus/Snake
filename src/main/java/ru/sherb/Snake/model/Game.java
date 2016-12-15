package ru.sherb.Snake.model;


import ru.sherb.Snake.Main;

import java.awt.*;
import java.util.Random;

/**
 * Created by sherb on 12.10.2016.
 */
public class Game {
    /**
     * Игровое поле
     */
    private Grid grid;
    /**
     * Все игроки в этой игре
     */
    private Snake[] players;
    private Fruit fruit; // Один фрукт за раз
    private long timeStart;
    private long gameTime;
    //TODO [REFACTOR] сделать что-нибудь с этим
    //TODO убрать настраиваемый цвет для фрукта, устанавливать цвет, в соответствии с созданным фруктом в самой игре
    private Color fruitColor;
    private boolean end;
    private volatile boolean stop;

    public Game(Grid grid, Color fruitColor, Snake... players) {
        // Создание поля для игры
        this.grid = grid;
        this.players = players;
        this.fruitColor = fruitColor;
        fruit = new Fruit(grid);
    }

    public void init() {
        //TODO [REFACTOR] изменить константные значения на переменные
        fruit.createFruitRandPos(1, 1, -1, fruitColor);
        for (Snake player : players) {
            player.setDirect(Controllable.RIGHT); // начальное направление движение всех игроков
        }
        end = false;
        start();
    }

    /**
     * Метод выполняет все действия, совершаемые за один игровой шаг.
     */
    public void step() {
        if (stop || end) return;

        for (Snake player : players) {
            // если игрок не может двигаться или столкнулся с чем то плохим, то игра останавливается
            if (!player.move()) {
                end();
            }

            if (player.eatFruit()) {
                fruit.eatenBy(player);
                if (new Random().nextInt(10) <= 2) {
                    //TODO добавить создание случайных фруктов в класс фрукт
                    // Время существование = время, за которое змейка пройдет 70% пути до самой удаленной точки
                    fruit.createFruitRandPos(10, 2, (int) (Math.max(grid.getWidth(), grid.getHeight()) * 0.7) + (60 * 3), Color.CYAN);
                } else {
                    fruit.createFruitRandPos(1, 1, -1, fruitColor);
                }
            }else if (!fruit.decExistOfTime()) {
                fruit.createFruitRandPos(1, 1, -1, fruitColor);
            }
        }
    }

    /**
     * @return время игры в миллисекундах
     */
    public long getGameTime() {
        return gameTime;
    }

    public void stop() {
        stop = true;
        gameTime += System.currentTimeMillis() - timeStart;
        for (Snake player : players) {
            player.lockChangeDirect();
        }
    }

    public void start() {
        stop = false;
        timeStart = System.currentTimeMillis();
        for (Snake player : players) {
            player.unlockChangeDirect();
        }
    }

    public void end() {
        end = true;
        gameTime += System.currentTimeMillis() - timeStart;
        if (Main.debug) System.out.println("Я закончил игру, прошло времени = " + gameTime);
    }

    public Grid getGrid() {
        return grid;
    }

    public Snake[] getPlayers() {
        return players;
    }

    public boolean isStop() {
        return stop;
    }

    public boolean isEnd() {
        return end;
    }
}
