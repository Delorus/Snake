package ru.sherb.Snake.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.view.MainMenu;
import ru.sherb.Snake.view.MainShell;
import ru.sherb.Snake.view.SettingMenu;

/**
 * Created by sherb on 27.10.2016.
 */
public class MainShellController {
    private MainShell shell;
    private MainMenu mainMenu;
    private SettingMenu settingMenu;

    public MainShellController() {
        shell = new MainShell(Main.display);
        mainMenu = new MainMenu(shell, SWT.NONE);
        settingMenu = new SettingMenu(shell, SWT.NONE);
        shell.setComposite(mainMenu);
        shell.open();
        shell.layout();

        //TODO [DEBUG] перестают работать после смены меню
        mainMenu.btnExit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Main.display.dispose();
            }
        });
        mainMenu.btnNewGame.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                openGameShell();
            }
        });
        mainMenu.btnSetting.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.setComposite(new SettingMenu(shell, SWT.NONE));
            }
        });


        settingMenu.btnExit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.setComposite(new MainMenu(shell, SWT.NONE));
            }
        });
    }

    public boolean isDisposed() {
        return shell.isDisposed();
    }

    public void openGameShell() {
        new GameShellController();
    }
}
