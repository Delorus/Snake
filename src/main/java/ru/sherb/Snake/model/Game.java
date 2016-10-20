package ru.sherb.Snake.model;


import java.awt.*;
import java.util.Random;

/**
 * Created by sherb on 12.10.2016.
 */
public class Game implements Runnable {
    //    private Cell[][] grid;
    private Grid grid;
    private Snake[] players; // Один игрок за раз //TODO сделать больше
    private Fruit fruit; // Один фрукт за раз
    private long gameTime;
    private Color fruitColor;
//    private boolean active;

    public Game(Grid grid, Color fruitColor, Snake ...players/*, byte backgroundColor*//*, byte snakeColor*/) {
        // Создание поля для игры
        this.grid = grid;
//        this.grid.setActive(false);
        this.players = players;
        this.fruitColor = fruitColor;
//        grid = new Cell[width][height];
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                grid[i][j] = new Cell(State.EMPTY, i, j/*, backgroundColor*/);
//            }
//        }

//        player = new Snake(grid, grid.length/2, grid[0].length/2/*, snakeColor*/);

        fruit = new Fruit(grid);
    }

    //TODO [COMPLETED] объединить методы движения в один
    //TODO [COMPLETED] сделать метод синхронизированным, что бы листенер не мог задать несколько значений до того как змейка походит (возможно не нужно)
    //TODO [COMPLETED] если быстро нажать на вниз\вверх и лево\право, то змейка съедает сама себя, решается дав возможность устанавливать направление один раз за цикл или пока змейка не двинется
//    @Deprecated
//    public void moveSnakeUp(Snake player) {
//        if (!player.isCanMove() || player.getDirect() == Snake.DOWN || player.getDirect() == Snake.UP) {
//            return;
//        }
//        player.moveTo(Snake.UP);
//        player.canMove(false);
//    }
//
//    @Deprecated
//    public void moveSnakeRight(Snake player) {
//        if (!player.isCanMove() || player.getDirect() == Snake.LEFT || player.getDirect() == Snake.RIGHT) {
//            return;
//        }
//        player.moveTo(Snake.RIGHT);
//        player.canMove(false);
//    }
//
//    @Deprecated
//    public void moveSnakeDown(Snake player) {
//        if (!player.isCanMove() || player.getDirect() == Snake.UP || player.getDirect() == Snake.DOWN) {
//            return;
//        }
//        player.moveTo(Snake.DOWN);
//        player.canMove(false);
//    }
//
//    @Deprecated
//    public void moveSnakeLeft(Snake player) {
//        if (!player.isCanMove() || player.getDirect() == Snake.RIGHT || player.getDirect() == Snake.LEFT) {
//            return;
//        }
//        player.moveTo(Snake.LEFT);
//        player.canMove(false);
//    }
//
//    @Deprecated
//    public void testMoveUp() {
//        moveSnakeUp(player);
//    }
//
//    @Deprecated
//    public void testMoveDown() {
//        moveSnakeDown(player);
//    }
//
//    @Deprecated
//    public void testMoveLeft() {
//        moveSnakeLeft(player);
//    }
//
//    @Deprecated
//    public void testMoveRight() {
//        moveSnakeRight(player);
//    }

    private boolean collisionProc(Snake player) {
        //TODO [REFACTOR] избавиться от switch-enum
        //TODO [REFACTOR] не явно когда возвращает false, когда true
        switch (player.getHead().getStatus()) {
            case SNAKE:
                if (player.isThisSnake(player.getHead())) {
                    return false;
                }
                //TODO дописать, что будет если одна змейка столкнется с другой
//                throw new RuntimeException("змейка съела саму себя");
//                break;
            case EMPTY:
                grid.getCell(player.getHead().getPosX(), player.getHead().getPosY()).setStatus(State.SNAKE, player);
                break;
            case FRUIT:
                //работает только если на поле существует только один фрукт
                fruit.eatenBy(player);
                grid.getCell(player.getHead().getPosX(), player.getHead().getPosY()).setStatus(State.SNAKE, player);
                while (!fruit.createFruit(new Random().nextInt(grid.getWidth()), new Random().nextInt(grid.getHeight()), fruitColor));
                break;
        }
        return true;
    }


    public void setScale(int scaleCoeff) {
        Cell.setSizeCoeff(scaleCoeff);
    }

//    public Cell[][] getGrid() {
//        return grid;
//    }

//    @Deprecated
//    public int getScore() {
//        return player.getScore();
//    }


    public void run() {
        long timeStart = System.currentTimeMillis();
        while (!fruit.createFruit(new Random().nextInt(grid.getWidth()), new Random().nextInt(grid.getHeight()), fruitColor)) ;
        for (Snake player: players) {
            player.moveTo(Snake.RIGHT);
        }
        grid.setActive(true);
        //TODO [DEBUG] удалить
//        active = true;

        while (grid.isActive()) {
            try {
                //TODO сделать зависимость от времени игры или накопленных очков
                //TODO [ВОЗМОЖНО] уменьшать время существования фрукта
                //100 - минимальное знач. для комфортной игры
                Thread.sleep(500);
                for (Snake player: players) {
                    player.canMove(true);
                    if (!player.move() || !collisionProc(player)) {
                        grid.setActive(false);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } /*catch (RuntimeException er) {
                active = false;
            }*/
        }
        System.out.println("Я завершил игру");
        gameTime = System.currentTimeMillis() - timeStart;
    }

//    public boolean isActive() {
//        return active;
//    }
}
