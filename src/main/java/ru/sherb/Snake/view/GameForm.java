package ru.sherb.Snake.view;
/**
 * Created by sherb on 17.10.2016.
 */


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import ru.sherb.Snake.controller.GameFormController;

import java.awt.*;
import java.awt.Color;


public class GameForm {
    //TODO вынести создание дисплея в главную форму приложения
    public static final Display display = Display.getDefault();
    public /*static*/ Shell shell = new Shell(display);
    private GameFormController controller;

    public GameForm() {

        shell.setText("GAME FORM");
        Point tmp = shell.computeSize(640, 480); // Не обязательно
        shell.setSize(tmp.x, tmp.y);
        shell.open();
        shell.setLayout(new FillLayout());
        controller = new GameFormController(this);


        // Обработка закрытия окна
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
        System.out.println("Я завершил работу");
    }

    public void destructGameForm() {
        shell.dispose();
        display.dispose();
    }
}
