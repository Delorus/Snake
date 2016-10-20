package ru.sherb.Snake.controller;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import ru.sherb.Snake.model.*;
import ru.sherb.Snake.view.DialogForm;
import ru.sherb.Snake.view.GameForm;

/**
 * Created by sherb on 17.10.2016.
 */
public class GameFormController {
    private GameForm form;
    private Composite composite;
    private Grid grid;
    //TODO [ВОЗМОЖНО] стоит ли в контроллере создавать змейку?
//    private Snake player1;
    private Rectangle bounds;
    private GC gc;

    public GameFormController(GameForm form) {
        this.form = form;
        composite = form.shell;
        bounds = composite.getClientArea();
        //TODO [ВОЗМОЖНО] вынести в класс Game
        int CellCount = 20; // Количество ячеек
        int size = bounds.height < bounds.width ? bounds.height / CellCount : bounds.width / CellCount; // Расчет оптимального размера ячейки
        Cell.setSizeCoeff(size / Cell.NORMAL_SIZE); //Расчет размера каждой ячейки
        int width = bounds.width / Cell.getSize();
        int height = bounds.height / Cell.getSize();

        grid = new Grid(width, height, java.awt.Color.BLACK);
        java.awt.Color fruitColor = java.awt.Color.RED;
        Snake player1 = new Snake(grid, grid.getWidth() / 2, grid.getHeight() / 2, java.awt.Color.WHITE);
        Snake player2 = new Snake(grid, 0, 0, java.awt.Color.MAGENTA);
        final Game game = new Game(grid, fruitColor, player1, player2);

        gc = new GC(composite);
        new Thread(game).start();
        Thread upd = new Thread(new Updater(gc, grid));
        upd.start();



        //TODO придумать что делать с этим слушателем, он мешает всей программе
        composite.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.keyCode) {
                    //TODO придумать где хранить управление у каждой змейки
                    case SWT.ARROW_UP:
                        player1.moveTo(Snake.UP);
//                        game.testMoveUp();
                        break;
                    case SWT.ARROW_RIGHT:
                        player1.moveTo(Snake.RIGHT);
//                        game.testMoveRight();
                        break;
                    case SWT.ARROW_DOWN:
                        player1.moveTo(Snake.DOWN);
//                        game.testMoveDown();
                        break;
                    case SWT.ARROW_LEFT:
                        player1.moveTo(Snake.LEFT);
//                        game.testMoveLeft();
                        break;
                    case 'w':
                        player2.moveTo(Snake.UP);
                        break;
                    case 'd':
                        player2.moveTo(Snake.RIGHT);
                        break;
                    case 's':
                        player2.moveTo(Snake.DOWN);
                        break;
                    case 'a':
                        player2.moveTo(Snake.LEFT);
                        break;
                }

            }
        });

//        try {
//            upd.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        while (upd.isAlive()) {};
//        new DialogForm(Display.getCurrent(), "You lose", "You score: false");

    }

    public void destructGameFormController() {
        gc.dispose();

    }
}


//TODO [REFACTOR] удалить костыль, удалить полностью, писалось в невменяемом состоянии
//TODO [THREAD] синхронизировать потоки обновление графического и логического состояния игры
class Updater implements Runnable {
    private GC gc;
    private Grid grid;
    private Rectangle bounds;
//    private boolean active;

    public Updater(GC gc, Grid grid) {
        this.grid = grid;
        this.gc = gc;
        bounds = new Rectangle(0, 0, grid.getWidth(), grid.getHeight());
//        active = true;
    }

    @Override
    public void run() {

        while (grid.isActive()) {
                update();
//            try {
//                Thread.sleep(34);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if (GameForm.display.isDisposed()) {
                System.out.println("Дисплей закрылся!");
                grid.setActive(false);
                return;
            }
        }
        System.out.println("Я закончил обновляться");
        //TODO [ВОЗМОЖНО] переместить вызов окна в контроллер
        Display.getDefault().syncExec(() -> new DialogForm(Display.getCurrent(), "You lose", "You score: null \n You time: null"));
//        GameForm.createDialogForm("You lose", "You score: " + game.getScore());

    }

    public void update() {
            Point start = new Point(bounds.x, bounds.y);
            for (int i = 0; i < grid.getWidth(); i++) {
                for (int j = 0; j < grid.getHeight(); j++) {
                    gc.setBackground(new Color(null,
                            grid.getCell(i, j).getStatus().getColor(grid.getCell(i, j)).getRed(),
                            grid.getCell(i, j).getStatus().getColor(grid.getCell(i, j)).getGreen(),
                            grid.getCell(i, j).getStatus().getColor(grid.getCell(i, j)).getBlue()));
                    gc.fillRectangle(start.x, start.y, Cell.getSize(), Cell.getSize());
                    start.y += Cell.getSize();
                }
                start.y = bounds.y;
                start.x += Cell.getSize();

            }
//        if (!game.isActive()) {
//            active = false;
//            try {
//                GameForm.createDialogForm("You lose", "You score: " + game.getScore());
//            } catch (RuntimeException re) {
////                re.printStackTrace();
//                System.out.println("You score: "+ game.getScore());
//            }

//        }
    }
}
