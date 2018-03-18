package ru.sherb.Snake.util;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public final class ResolutionGenerator {

    private final int widthFactor;
    private final int heightFactor;

    public ResolutionGenerator(int widthFactor, int heightFactor) {
        this.widthFactor = widthFactor;
        this.heightFactor = heightFactor;
    }

    public List<Point> computeByHeight(int maxHeight, Integer minHeight) {
        List<Point> result = new ArrayList<>();
        int divider = heightFactor;
        int minDividend = OptionalInt.of(minHeight).orElse(0);
        for (int i = minDividend; i <= maxHeight; i++) {
            if (i % divider == 0) {
                result.add(new Point((i / divider * widthFactor), i));
            }
        }
        return result;
    }

    public List<Point> computeByWidth(int maxWidth, Integer minWidth) {
        List<Point> result = new ArrayList<>();
        int divider = widthFactor;
        int minDividend = OptionalInt.of(minWidth).orElse(0);
        for (int i = minDividend; i <= maxWidth; i++) {
            if (i % divider == 0) {
                result.add(new Point(i, (i * divider / heightFactor)));
            }
        }
        return result;
    }

    public List<Point> computeCount(int n, Integer minWidth) {
        List<Point> result = new ArrayList<>();
        int divider = widthFactor;
        int minDividend = OptionalInt.of(minWidth).orElse(0);
        for (int i = minDividend, count = 0; count < n; i++) {
            if (i % divider == 0) {
                result.add(new Point(i, (i * heightFactor / divider)));
                count++;
            }
        }
        return result;
    }
}
