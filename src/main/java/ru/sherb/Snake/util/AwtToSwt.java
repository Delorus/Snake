package ru.sherb.Snake.util;

import org.eclipse.swt.graphics.Color;
import ru.sherb.Snake.Main;

public final class AwtToSwt {

    private AwtToSwt() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Color toSwtColor(java.awt.Color awtColor) {
        return new Color(Main.display, awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
    }

    public static java.awt.Color toAwtColor(Color swtColor) {
        return new java.awt.Color(swtColor.getRed(), swtColor.getGreen(), swtColor.getBlue());
    }
}
