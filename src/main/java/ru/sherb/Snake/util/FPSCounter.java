package ru.sherb.Snake.util;

import java.io.PrintStream;

/**
 * Created by sherb on 03.11.2016.
 */
class FPSCounter {
    private int count;
    private final PrintStream out;

    public FPSCounter(PrintStream out) {
        this.out = out;
    }

    synchronized public int getCount() {
        return count;
    }

    synchronized public void incCount() {
        this.count++;
    }

    private synchronized void reset() {
        count = 0;
    }

    public synchronized void printCount() {
        out.println("FPS = " + count);
        reset();
    }
}