package ru.sherb.Snake;

import org.eclipse.swt.widgets.Display;
import ru.sherb.Snake.controller.MainShellController;

/**
 * Created by sherb on 12.10.2016.
 */
public class Main {
    public static final Display display = Display.getDefault();

    public static void main(String[] args) {
//        GameForm form = new GameForm();


        try {
            MainShellController mainShellController = new MainShellController();

            while (!mainShellController.isDisposed()) {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



