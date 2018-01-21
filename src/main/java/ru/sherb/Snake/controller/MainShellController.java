package ru.sherb.Snake.controller;

import org.eclipse.swt.SWT;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.view.MainMenu;
import ru.sherb.Snake.view.MainShell;

/**
 * Created by sherb on 27.10.2016.
 */
public class MainShellController {
    private MainShell shell;

    public MainShellController() {
        shell = new MainShell(Main.display);
    }

    public void open() {
        shell.setComposite(new MainMenu(shell, SWT.NONE));
        shell.open();
        shell.layout();
        shell.addListener(SWT.Close, e -> Main.display.dispose());
    }

    public boolean isDisposed() {
        return shell.isDisposed();
    }
}
