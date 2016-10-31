package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

public class SettingMenu extends Composite {
	public Button btnGame;
    public Button btnGraphics;
    public Button btnAudio;
    public Button btnControl;
    public Button btnExit;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SettingMenu(MainShell parent, int style) {
		super(parent, style);
		
		setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
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
		
		btnGraphics = new Button(this, SWT.NONE);
		btnGraphics.setText("Graphics");
		
		btnAudio = new Button(this, SWT.NONE);
		btnAudio.setText("Audio");
		
		btnControl = new Button(this, SWT.NONE);
		btnControl.setText("Control");
		
		btnExit = new Button(this, SWT.NONE);
		btnExit.setText("Exit");
        btnExit.addListener(SWT.Selection, e -> parent.setComposite(new MainMenu(parent, SWT.NONE)));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
