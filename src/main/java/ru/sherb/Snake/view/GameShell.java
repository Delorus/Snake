package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.controller.MainShellController;

public class GameShell extends Shell {
    private Label sysMessage;
    private Canvas gameField;
    private Composite stateComposite;
    private Composite toolComposite;
    public Composite gameComposite;
    private Label lblPlayer;
    private Composite compPlayerColor;
    private Label lblScoreValue;
    private Label lblLengthValue;

    /**
     * Create the shell.
     */
    public GameShell(Display display, Point defaultSize, boolean fullscreen) {
        super(display, SWT.DIALOG_TRIM);
        setLayout(new FormLayout());
        setMinimumSize(432, 243);
        setText("The Snake");
        setSize(defaultSize.x, defaultSize.y);
        if (fullscreen) {
            setMaximized(true);
            setFullScreen(true);
        }
        Image icon = new Image(Main.display, Class.class.getResourceAsStream("/icon.ico"));
        setImage(icon);

        //Create menu
        Menu bar = new Menu(this, SWT.BAR);
        this.setMenuBar(bar);
        MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
        fileItem.setText("&File");
        Menu submenu = new Menu(this, SWT.DROP_DOWN);
        fileItem.setMenu(submenu);
        MenuItem item = new MenuItem(submenu, SWT.PUSH);
        item.addListener(SWT.Selection, e -> {
            new MainShellController().open();
            this.dispose();
        });
        item.setText("&Exit\tESC");
        item.setAccelerator(SWT.MOD1 + 'A');
        createContents();
    }


    /**
     * Create contents of the shell.
     */
    private void createContents() {
        stateComposite = new Composite(this, SWT.NONE);
        stateComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

        toolComposite = new Composite(this, SWT.NONE);
        toolComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
        sysMessage = new Label(toolComposite, SWT.NONE);
        sysMessage.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TRANSPARENT));

        Composite gameComposite = new Composite(this, SWT.NONE);
        gameField = new Canvas(gameComposite, SWT.NONE);
        gameField.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
        stateComposite.setLayout(new GridLayout(2, false));

        FormData fd_stateComposite = new FormData();
        fd_stateComposite.left = new FormAttachment(gameComposite);
        fd_stateComposite.right = new FormAttachment(100);
        fd_stateComposite.top = new FormAttachment(0);
        fd_stateComposite.bottom = new FormAttachment(gameComposite, 0, SWT.BOTTOM);
        stateComposite.setLayoutData(fd_stateComposite);

        FormData fd_gameComposite = new FormData();
        fd_gameComposite.bottom = new FormAttachment(100, -30);
        fd_gameComposite.top = new FormAttachment(0);
        fd_gameComposite.right = new FormAttachment(100, -154);
        fd_gameComposite.left = new FormAttachment(0);
        gameComposite.setLayoutData(fd_gameComposite);
        gameComposite.setLayout(new FillLayout());


        FormData fd_toolComposite = new FormData();
        fd_toolComposite.top = new FormAttachment(gameComposite);
        fd_toolComposite.left = new FormAttachment(0);
        fd_toolComposite.right = new FormAttachment(100);
        fd_toolComposite.bottom = new FormAttachment(100);
        toolComposite.setLayoutData(fd_toolComposite);
        toolComposite.setLayout(new FillLayout());

        lblPlayer = new Label(stateComposite, SWT.NONE);
        lblPlayer.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblPlayer.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
        GridData gd_lblPlayer = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_lblPlayer.widthHint = 76;
        lblPlayer.setLayoutData(gd_lblPlayer);

        compPlayerColor = new Composite(stateComposite, SWT.BORDER);
        GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
        gd_composite.heightHint = 20;
        compPlayerColor.setLayoutData(gd_composite);

        Label lblScore = new Label(stateComposite, SWT.NONE);
        lblScore.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
        lblScore.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblScore.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblScore.setText("Score");

        lblScoreValue = new Label(stateComposite, SWT.NONE);
        lblScoreValue.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblScoreValue.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
        lblScoreValue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

        Label lblLength = new Label(stateComposite, SWT.NONE);
        lblLength.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
        lblLength.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblLength.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblLength.setText("Length");

        lblLengthValue = new Label(stateComposite, SWT.NONE);
        lblLengthValue.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
        lblLengthValue.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblLengthValue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
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

    public void setData(String playerName, Color playerColor, int playerScore, int playerLength) {
        lblPlayer.setText(playerName);
        compPlayerColor.setBackground(playerColor);
        lblScoreValue.setText(String.valueOf(playerScore));
        lblLengthValue.setText(String.valueOf(playerLength));

        stateComposite.layout();
    }
}
