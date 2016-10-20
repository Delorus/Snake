package ru.sherb.Snake.util;

/**
 * Created by sherb on 13.10.2016.
 */
public class Timer implements Runnable {
    private long value; //время в милисекундах
    private boolean stop;

    public Timer() {
        value = 0;
        stop = false;
    }

    @Override
    public void run() {
        while (!stop) {
            value++;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace(); //TODO изменить поток вывода ошибок
            }
        }
    }

    public void stop() {
        stop = true;
    }

    public void start() {
        stop = false;
    }

    public void reset() {
        value = 0;
    }

    public long getValue() {
        return value;
    }
}
