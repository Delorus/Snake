package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;
import ru.sherb.Snake.controller.GameShellController;

public class MainMenu extends Composite {
    public Button btnNewGame;
    public Button btnGameLoad;
    public Button btnState;
    public Button btnSetting;
    public Button btnHelp;
    public Button btnExit;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public MainMenu(MainShell parent, int style) {
		super(parent, style);

		setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		RowLayout rl_composite = new RowLayout(SWT.VERTICAL);
        rl_composite.spacing = 5;
        rl_composite.marginHeight = 50;
        rl_composite.center = true;
		rl_composite.marginWidth = 100;
		rl_composite.fill = true;
		setLayout(rl_composite);

		btnNewGame = new Button(this, SWT.NONE);
		btnNewGame.setLayoutData(new RowData(100, SWT.DEFAULT));
		// TODO убрать в контроллер
		btnNewGame.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
                // TODO добавить запуск игры

//                new GameShellController();
//                GameShell shell = new GameShell(Display.getDefault());
            }
        });
		btnNewGame.setText("New game");

        btnGameLoad = new Button(this, SWT.NONE);
		btnGameLoad.setEnabled(false);
		btnGameLoad.setText("Game load");

        btnState = new Button(this, SWT.NONE);
		btnState.setText("Statistics");

        btnSetting = new Button(this, SWT.NONE);
		// TODO убрать в контроллер
		btnSetting.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.setComposite(new SettingMenu(parent, SWT.NONE));
			}
		});
		btnSetting.setText("Setting");

        btnHelp = new Button(this, SWT.NONE);
		btnHelp.setText("Help");

		btnExit = new Button(this, SWT.NONE);
		btnExit.setText("Exit");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
