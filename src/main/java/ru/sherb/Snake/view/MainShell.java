package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class MainShell extends Shell {
    private Composite menu;

	/**
	 * Create the shell.
	 * @param display
	 */
	public MainShell(Display display) {
		super(display, SWT.CLOSE | SWT.MIN | SWT.TITLE);
		setText("Main menu");
		setSize(300, 480);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
        //TODO [DEBUG] Настроить нормальную загрузку ресурсов
//        this.setBackgroundImage(SWTResourceManager.getImage("F:\\YandexDisk\\Documents\\Java\\Eclipse\\SnakeGUI\\src\\ru\\sherb\\Snake\\resources\\backgroundTmp.png"));

        Label lblLogo = new Label(this, SWT.CENTER);
//        lblLogo.setImage(SWTResourceManager.getImage("F:\\YandexDisk\\Documents\\Java\\Eclipse\\SnakeGUI\\src\\ru\\sherb\\Snake\\resources\\LogoTmp.png"));
        lblLogo.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
        lblLogo.setAlignment(SWT.CENTER);
        lblLogo.setLocation(0, 0);
        lblLogo.setSize(294, 100);
//        setComposite(new MainMenu(this, SWT.NONE));
    }

    public <O extends Composite> void  setComposite(O composite) {
        if (menu != null) {
            menu.dispose();
        }
        menu = composite;
        menu.setLocation(0, 100);
        menu.setSize(294, 345);
        pack();
    }

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
