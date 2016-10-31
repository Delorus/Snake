package ru.sherb.Snake;

import org.eclipse.swt.widgets.Display;
import ru.sherb.Snake.controller.MainShellController;

/**
 * Created by sherb on 12.10.2016.
 */
public class Main {
    public static final Display display = Display.getDefault();
    public static boolean debug = false;
    public static void main(String[] args) {
//        GameForm form = new GameForm();
        if (args.length > 0 && args[0].equals("-debug")) debug = true;

        try {
            MainShellController mainShellController = new MainShellController();

            while (!mainShellController.isDisposed()) {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
        } catch (Exception e) {
            if (debug) e.printStackTrace();
        }
    }

}



