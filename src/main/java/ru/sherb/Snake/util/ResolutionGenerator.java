package ru.sherb.Snake.util;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class ResolutionGenerator {

    private final int widthFactor;
    private final int heightFactor;

    public ResolutionGenerator(int widthFactor, int heightFactor) {
        this.widthFactor = widthFactor;
        this.heightFactor = heightFactor;
    }

    public List<Point> computeCount(int n) {
        return computeCount(n, 0);
    }

    public List<Point> computeCount(int n, int minWidth) {
        List<Point> result = new ArrayList<>();
        int divider = widthFactor;
        for (int i = minWidth, count = 0; count < n; i++) {
            if (i % divider == 0) {
                result.add(new Point(i, (i * heightFactor / divider)));
                count++;
            }
        }
        return result;
    }
}
