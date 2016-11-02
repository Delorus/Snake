package ru.sherb.Snake.model;


import ru.sherb.Snake.Main;

import java.awt.*;
import java.util.Random;

/**
 * Created by sherb on 12.10.2016.
 */
public class Game implements Runnable {
    private Grid grid;
    private Snake[] players; // Один игрок за раз //TODO сделать больше
    private Fruit fruit; // Один фрукт за раз
    private long gameTime;
    private Color fruitColor;

    public Game(Grid grid, Color fruitColor, Snake ...players) {
        // Создание поля для игры
        this.grid = grid;
        this.players = players;
        this.fruitColor = fruitColor;

        fruit = new Fruit(grid);
    }

    private boolean collisionProc(Snake player) {
        //TODO [REFACTOR] избавиться от switch-enum
        //TODO [REFACTOR] не явно когда возвращает false, когда true
        switch (player.getPierce().getStatus()) {
            case SNAKE:
//                if (player.isThisSnake(player.getPierce())) {
//                    return false;
//                }
                //TODO дописать, что будет если одна змейка столкнется с другой
                return false;
//                break;
            case EMPTY:
                //TODO перенести строчку в класс змейки
                grid.getCell(player.getPierce().getPosX(), player.getPierce().getPosY()).setStatus(State.SNAKE, player);
                break;
            case FRUIT:
                //работает только если на поле существует только один фрукт
                fruit.eatenBy(player);
                // шанс выпадения супер-фрукта 20%
                if (new Random().nextInt(10) <= 2) {
                    // TODO сделать зависимость времени существования от количеста клеток в игровом поле
                    // Время существование = время, которая змейка пройдет 70% от бОльшого значения длины поля

                    fruit.createFruitRandPos(10, 2, (int) (Math.max(grid.getWidth(), grid.getHeight()) * 0.7), Color.CYAN);
                } else {
                    fruit.createFruitRandPos(1, 1, -1, fruitColor);
                }
                break;
        }
        return true;
    }


    public void setScale(int scaleCoeff) {
        Cell.setSizeCoeff(scaleCoeff);
    }


    public void run() {
        long timeStart = System.currentTimeMillis();
        int sleep = 5000 / Math.max(grid.getWidth(), grid.getHeight());
        int minSleep = 1000 / Math.max(grid.getWidth(), grid.getHeight());
        if (Main.debug) {
            System.out.println("Время задержки = " + sleep);
            System.out.println("Минимальное время задержки = " + minSleep);
        }
        //TODO [REFACTOR] изменить константные значения на переменные
        fruit.createFruitRandPos(1, 1, -1, fruitColor);
        for (Snake player: players) {
            player.moveTo(Snake.RIGHT); // начальное направление движение всех игроков
        }
        grid.setActive(true);
        while (grid.isActive()) {
            try {
                //TODO оптимизировать этот процесс
                //TODO подобрать оптимальные значения задерки
                //100 - минимальное знач. для комфортной игры
                int totalScore = 0;
                for (Snake player : players) {
                    totalScore += player.getScore() * 4;
                }

                int delay = sleep - totalScore < minSleep ? minSleep : sleep - totalScore;
                Thread.sleep(delay);
                for (Snake player : players) {
                    player.canMove(true);
                    if (!player.move() || !collisionProc(player)) {
                        grid.setActive(false);
                    }
                    if (!fruit.decExistOfTime()) {
                        fruit.createFruitRandPos(1, 1, -1, fruitColor);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (Main.debug) System.out.println("Я завершил игру");
        gameTime = (System.currentTimeMillis() - timeStart);
    }

    /**
     * @return время игры в миллисекундах
     */
    public long getGameTime() {
        return gameTime;
    }
}
