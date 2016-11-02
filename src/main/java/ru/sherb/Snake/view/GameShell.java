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
    private Canvas gameField;
    private Composite stateComposite;
    private Composite toolComposite;

    /**
     * Create the shell.
     *
     * @param display
     */
    public GameShell(Display display, Point defaultSize, boolean fullscreen) {
        super(display, (fullscreen ? SWT.NO_TRIM : SWT.SHELL_TRIM));
        setLayout(new FormLayout());
        setMinimumSize(432, 243);
        setText("The Snake");
        setSize(defaultSize.x, defaultSize.y);
        if (fullscreen) {
            setMaximized(true);
            setFullScreen(true);
        }

        //Create menu
        Menu bar = new Menu(this, SWT.BAR);
        this.setMenuBar(bar);
        MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
        fileItem.setText("&File");
        Menu submenu = new Menu(this, SWT.DROP_DOWN);
        fileItem.setMenu(submenu);
        MenuItem item = new MenuItem(submenu, SWT.PUSH);
        item.addListener(SWT.Selection, e -> System.out.println("Exit"));
        item.setText("Select &All\tCtrl+A");
        item.setAccelerator(SWT.MOD1 + 'A');
        createContents();
    }


    /**
     * Create contents of the shell.
     */
    protected void createContents() {


        stateComposite = new Composite(this, SWT.BORDER);

        toolComposite = new Composite(this, SWT.BORDER);
        sysMessage = new Label(toolComposite, SWT.LEFT);

        Composite gameComposite = new Composite(this, SWT.BORDER);
        gameField = new Canvas(gameComposite, SWT.NONE);
        //TODO удалить после дебага
        gameField.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

        FormData fd_stateComposite = new FormData();
        fd_stateComposite.right = new FormAttachment(100);
        fd_stateComposite.top = new FormAttachment(0);
        fd_stateComposite.left = new FormAttachment(gameComposite, 6);
        fd_stateComposite.bottom = new FormAttachment(gameComposite, 0, SWT.BOTTOM);
        stateComposite.setLayoutData(fd_stateComposite);



        FormData fd_gameComposite = new FormData();
        fd_gameComposite.bottom = new FormAttachment(100, -32);
        fd_gameComposite.top = new FormAttachment(0);
        fd_gameComposite.right = new FormAttachment(100, -170);
        fd_gameComposite.left = new FormAttachment(0);
        gameComposite.setLayoutData(fd_gameComposite);
        gameComposite.setLayout(new FillLayout());


        FormData fd_toolComposite = new FormData();
        fd_toolComposite.top = new FormAttachment(gameComposite, 3);
        fd_toolComposite.left = new FormAttachment(0);
        fd_toolComposite.right = new FormAttachment(100);
        fd_toolComposite.bottom = new FormAttachment(100);
        toolComposite.setLayoutData(fd_toolComposite);
        toolComposite.setLayout(new FillLayout());
    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

    public void printMessage(String message) {
        sysMessage.setText(message);
    }

    public Rectangle getGameArea() {
        this.layout();
        return gameField.getClientArea();
    }

    public Canvas getGameField() {
        return gameField;
    }
}
