package ru.sherb.Snake.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.view.MainMenu;
import ru.sherb.Snake.view.MainShell;
import ru.sherb.Snake.view.SettingMenu;

/**
 * Created by sherb on 27.10.2016.
 */
public class MainShellController {
    private MainShell shell;

    public MainShellController() {
        shell = new MainShell(Main.display);
        shell.setComposite(new MainMenu(shell, SWT.NONE));
        shell.open();
        shell.layout();

        shell.addListener(SWT.Close, e -> Main.display.dispose());
//        shell.addShellListener(new ShellAdapter() {
//            @Override
//            public void shellClosed(ShellEvent e) {
//                Main.display.dispose();
//            }
//        });
    }

    public boolean isDisposed() {
        return shell.isDisposed();
    }

//    public void open() {
//        shell.setVisible(true);
////        shell.forceFocus();
//        shell.setFocus();
//    }
//
//    public void close() {
//        shell.setVisible(false);
//    }
}
