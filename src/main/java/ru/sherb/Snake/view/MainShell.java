package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import ru.sherb.Snake.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class MainShell extends Shell {
    private Composite menu;

	public MainShell(Display display) {
		super(display, SWT.CLOSE | SWT.MIN | SWT.TITLE);
		setText("Main menu");
		setSize(300, 480);
        setBackground(Main.display.getSystemColor(SWT.COLOR_MAGENTA));
        try (InputStream is =  Class.class.getResourceAsStream("/ru/sherb/Snake/res/backgroundTmp.png")) {
            if (Objects.nonNull(is)) {
                setBackgroundImage(new Image(Main.display, is));
            } else {
                setBackground(Main.display.getSystemColor(SWT.COLOR_WHITE));
            }
        } catch (IOException e) {
            if (Main.isDebug()) e.printStackTrace();
        }

		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {

        Label lblLogo = new Label(this, SWT.CENTER);
        try (InputStream is =  Class.class.getResourceAsStream("/ru/sherb/Snake/res/LogoTmp.png")) {
            if (Objects.nonNull(is))
                lblLogo.setImage(new Image(Main.display, is));
        } catch (IOException e) {
            if (Main.isDebug()) e.printStackTrace();
        }
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
