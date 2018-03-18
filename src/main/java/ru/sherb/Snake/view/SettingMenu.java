package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import ru.sherb.Snake.Main;

class SettingMenu extends Composite {
	private final Button btnGame;
	private final Button btnGraphics;
	private final Button btnControl;
	private final Button btnExit;

	/**
	 * Create the composite.
	 */
	public SettingMenu(MainShell parent, int style) {
		super(parent, style);
		
		setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
		RowLayout rl_composite = new RowLayout(SWT.VERTICAL);
		rl_composite.spacing = 5;
		rl_composite.marginHeight = 50;
		rl_composite.center = true;
		rl_composite.marginWidth = 100;
		rl_composite.fill = true;
		setLayout(rl_composite);
		
		btnGame = new Button(this, SWT.NONE);
		btnGame.setLayoutData(new RowData(100, SWT.DEFAULT));
		btnGame.setText("Game");
		btnGame.addListener(SWT.Selection, e -> parent.setComposite(new GameSetting(parent, SWT.NONE)));
		
		btnGraphics = new Button(this, SWT.NONE);
		btnGraphics.setText("Graphics");
        btnGraphics.addListener(SWT.Selection, e -> parent.setComposite(new GraphicSetting(parent, SWT.NONE)));
		
		btnControl = new Button(this, SWT.NONE);
		btnControl.setText("Control");
        btnControl.addListener(SWT.Selection, e -> parent.setComposite(new ControlSetting(parent, SWT.NONE)));
		
		btnExit = new Button(this, SWT.NONE);
		btnExit.setText("Exit");
        btnExit.addListener(SWT.Selection, e -> parent.setComposite(new MainMenu(parent, SWT.NONE)));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
