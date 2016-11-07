package ru.sherb.Snake.model;


import ru.sherb.Snake.Main;
import ru.sherb.Snake.util.FPSCounter;
import ru.sherb.Snake.util.Timer;

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
    //TODO [REFACTOR] сделать что-нибудь с этим
    //TODO убрать настраиваемый цвет для фрукта, устанавливать цвет, в соответствии с созданным фруктом в самой игре
    private Color fruitColor;
    private boolean pause;
    private volatile boolean stop;

    public Game(Grid grid, Color fruitColor, Snake... players) {
        // Создание поля для игры
        this.grid = grid;
        this.players = players;
        this.fruitColor = fruitColor;
        pause = false;
        fruit = new Fruit(grid);
        stop = false;
    }

    private boolean collisionProc(Snake player) {
        //TODO [REFACTOR] избавиться от switch-enum
        //TODO [REFACTOR] не явно когда возвращает false, когда true
        //TODO [REFACTOR] перенести обработку столкновений в классы, которым принадлежат объекты столкновений
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
                    // Время существование = время, за которое змейка пройдет 70% пути до самой удаленной точки

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
        for (Snake player : players) {
            player.setDirect(Controllable.RIGHT); // начальное направление движение всех игроков
        }
//        grid.setActive(true);
        while (!stop) {
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
                        stop = true;
                    }
                    if (!fruit.decExistOfTime()) {
                        fruit.createFruitRandPos(1, 1, -1, fruitColor);
                    }
                }

                synchronized (this) {
                    while (pause) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                if (Main.debug) e.printStackTrace();
                stop = true;
            }
        }
        if (Main.debug) System.out.println("Я завершил игру");
        stop = true;
        gameTime = (System.currentTimeMillis() - timeStart);
    }

    /**
     * @return время игры в миллисекундах
     */
    public long getGameTime() {
        return gameTime;
    }

    public synchronized void pause() {
        pause = true;
        for (Snake player : players) {
            player.canMove(false);
        }
    }

    public synchronized void start() {
        pause = false;
        for (Snake player : players) {
            player.canMove(true);
        }
        notify();
    }

    public boolean isPause() {
        return pause;
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

    public synchronized void stop() {
        stop = true;
        pause = false;
        notifyAll();
    }
}
