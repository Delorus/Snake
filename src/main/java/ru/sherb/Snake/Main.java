package ru.sherb.Snake;

import org.eclipse.swt.widgets.Display;
import ru.sherb.Snake.controller.MainShellController;
import ru.sherb.Snake.setting.Setting;

import java.io.IOException;

/**
 * Created by sherb on 12.10.2016.
 */
public class Main {
    public static final Display display = Display.getDefault();
    private static boolean debug = false;

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-debug")) debug = true;

        Setting setting = Setting.getInstance();
        setting.setPath("Snake.properties");
        try {
            setting.loadOrDefault();
        } catch (IOException e) {
            if (Main.isDebug()) e.printStackTrace();
        }

        try {
            new MainShellController().open();

            while (!display.isDisposed()) {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
        } catch (Exception e) {
            if (debug) e.printStackTrace();
        }
    }

    public static boolean isDebug() {
        return debug;
    }
}



