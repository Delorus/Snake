package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.controller.MainShellController;

/**
 * Created by sherb on 19.10.2016.
 */
public class DialogForm {
    public DialogForm(Shell callBox, String title, String message) {
        Shell dialog = new Shell(callBox, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        dialog.setText(title);
        dialog.setSize(200, 200);
        dialog.setLocation(callBox.getClientArea().width / 2, callBox.getClientArea().height / 2);
        FormLayout formLayout = new FormLayout();
        formLayout.marginWidth = 10;
        formLayout.marginHeight = 10;
        formLayout.spacing = 10;
        dialog.setLayout(formLayout);

        Label text = new Label(dialog, SWT.NONE);
        text.setText(message);
        FormData data = new FormData();
        text.setLayoutData(data);

        dialog.pack();
        dialog.open();

        // Обработка закрытия окна
        //TODO [DEBUG] удалить после тестирования
        while (!dialog.isDisposed()) {
            if (!Display.getCurrent().readAndDispatch()) {
                Display.getCurrent().sleep();
            }
        }
        callBox.dispose();
        new MainShellController();
        if (Main.isDebug()) System.out.println("Я закрыл окно");
    }
}
