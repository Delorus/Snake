package ru.sherb.Snake.util;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by sherb on 03.11.2016.
 */
public class FPSCounter {
    private int count;
    private PrintStream out;

    public FPSCounter(PrintStream out) {
        this.out = out;
    }

    synchronized public int getCount() {
        return count;
    }

    synchronized public void incCount() {
        this.count++;
    }

    synchronized public void reset() {
        count = 0;
    }

    public void printCount() throws IOException {
        out.println("FPS = " + count);
        reset();
    }
}
