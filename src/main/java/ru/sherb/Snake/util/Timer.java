package ru.sherb.Snake.util;

import ru.sherb.Snake.Main;

/**
 * Created by sherb on 03.11.2016.
 */
public class Timer implements Runnable {
    private int delay;
    private Task task;

    public Timer(int delay) {
        this.delay = delay;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delay);
            task.performTask();
        } catch (Exception e) {
            if (Main.debug) e.printStackTrace();
        }
    }
}
