package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import ru.sherb.Snake.Main;

public class MainShell extends Shell {
    private Composite menu;

	public MainShell(Display display) {
		super(display, SWT.CLOSE | SWT.MIN | SWT.TITLE);
		setText("Main menu");
		setSize(300, 480);
        setBackground(Main.display.getSystemColor(SWT.COLOR_MAGENTA));
        //TODO [DEBUG] Настроить нормальную загрузку ресурсов
        Image background = new Image(Main.display, Class.class.getResourceAsStream("/ru/sherb/Snake/res/backgroundTmp.png"));
        setBackgroundImage(background);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {

        Label lblLogo = new Label(this, SWT.CENTER);
        lblLogo.setImage(new Image(Main.display, Class.class.getResourceAsStream("/ru/sherb/Snake/res/LogoTmp.png")));
//        lblLogo.setImage(SWTResourceManager.getImage("F:\\YandexDisk\\Documents\\Java\\Eclipse\\SnakeGUI\\src\\ru\\sherb\\Snake\\resources\\LogoTmp.png"));
        lblLogo.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblLogo.setAlignment(SWT.CENTER);
        lblLogo.setLocation(0, 0);
        lblLogo.setSize(294, 100);
    }

    public <C extends Composite> void  setComposite(C composite) {
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
