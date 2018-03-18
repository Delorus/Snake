package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
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
        super(display, SWT.NONE);
		setText("Main menu");
		setSize(300, 480);
        Rectangle bounds = Main.display.getPrimaryMonitor().getBounds();
        Rectangle size = getBounds();
        final int x = bounds.x + (bounds.width - size.width) / 2;
        final int y = bounds.y + (bounds.height - size.height) / 2;
        setLocation(x, y);
        setBackground(Main.display.getSystemColor(SWT.COLOR_MAGENTA));
        try (InputStream is = Class.class.getResourceAsStream("/background.png")) {
            if (is != null) {
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
    private void createContents() {

        Label lblLogo = new Label(this, SWT.CENTER);
        try (InputStream is = Class.class.getResourceAsStream("/logo.png")) {
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
