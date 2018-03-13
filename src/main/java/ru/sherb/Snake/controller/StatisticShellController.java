package ru.sherb.Snake.controller;

import org.eclipse.swt.SWT;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.view.StatisticShell;

public class StatisticShellController {

    private final StatisticShell shell;

    public StatisticShellController() {
        shell = new StatisticShell(Main.display);
    }

    public void open() {
        shell.customize();
        shell.open();
        shell.layout();
        shell.addListener(SWT.CLOSE, e -> new MainShellController().open());
    }
}
