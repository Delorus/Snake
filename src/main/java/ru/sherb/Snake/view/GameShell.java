package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class GameShell extends Shell {
    private Label sysMessage;
    private  Canvas gameField;
    private Composite stateComposite;
    private Composite toolComposite;

    /**
	 * Create the shell.
	 * @param display
	 */
	public GameShell(Display display) {
		super(display, SWT.SHELL_TRIM);
        setLayout(new FormLayout());

        //Create menu
		Menu bar = new Menu (this, SWT.BAR);
        this.setMenuBar(bar);
        MenuItem fileItem = new MenuItem (bar, SWT.CASCADE);
        fileItem.setText("&File");
        Menu submenu = new Menu (this, SWT.DROP_DOWN);
        fileItem.setMenu(submenu);
        MenuItem item = new MenuItem (submenu, SWT.PUSH);
        item.addListener(SWT.Selection, e -> System.out.println("Exit"));
        item.setText("Select &All\tCtrl+A");
        item.setAccelerator(SWT.MOD1 + 'A');
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("The Snake");
		setSize(640, 480);

        stateComposite = new Composite(this, SWT.BORDER);
        FormData fd_stateComposite = new FormData();
        fd_stateComposite.right = new FormAttachment(100);
        fd_stateComposite.top = new FormAttachment(0);
        fd_stateComposite.left = new FormAttachment(100, -153);
        stateComposite.setLayoutData(fd_stateComposite);

        Composite gameComposite = new Composite(this, SWT.BORDER);
        fd_stateComposite.bottom = new FormAttachment(gameComposite, 0, SWT.BOTTOM);
        FormData fd_gameComposite = new FormData();
        fd_gameComposite.bottom = new FormAttachment(100, -33);
        fd_gameComposite.top = new FormAttachment(0);
        fd_gameComposite.right = new FormAttachment(stateComposite, -6);
        fd_gameComposite.left = new FormAttachment(0);
        gameComposite.setLayoutData(fd_gameComposite);
        gameComposite.setLayout(new FillLayout());
        gameField = new Canvas(gameComposite, SWT.NONE);
        gameField.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

        toolComposite = new Composite(this, SWT.BORDER);
        FormData fd_toolComposite = new FormData();
        fd_toolComposite.top = new FormAttachment(stateComposite, 2);
        fd_toolComposite.left = new FormAttachment(0);
        fd_toolComposite.right = new FormAttachment(100);
        fd_toolComposite.bottom = new FormAttachment(100);
        toolComposite.setLayoutData(fd_toolComposite);
        toolComposite.setLayout(new FillLayout());
        sysMessage = new Label(toolComposite, SWT.LEFT);


    }

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

    public void printMessage(String message) {
        sysMessage.setText(message);
    }

    public Rectangle getBoundsGame() {
        this.layout();
        return gameField.getClientArea();
    }

    public Canvas getGameField() {
        return gameField;
    }
}
