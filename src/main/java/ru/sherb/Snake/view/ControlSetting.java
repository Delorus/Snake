package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.*;
import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.model.Controllable;
import ru.sherb.Snake.util.Setting;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by sherb on 09.11.2016.
 */
public class ControlSetting extends Composite {
    private ButtonComposite buttonComposite;
    //TODO [REFACTOR] такой способ индентификации игрока не подходит для случая, когда игроков больше чем 1
    private TabItem tbtmPlayer;
    private Text txtMoveUp;
    private Text txtMoveDown;
    private Text txtMoveLeft;
    private Text txtMoveRight;
    /**
     * Constructs a new instance of this class given its parent
     * and a style value describing its behavior and appearance.
     * <p>
     * The style value is either one of the style constants defined in
     * class <code>SWT</code> which is applicable to instances of this
     * class, or must be built by <em>bitwise OR</em>'ing together
     * (that is, using the <code>int</code> "|" operator) two or more
     * of those <code>SWT</code> style constants. The class description
     * lists the style constants that are applicable to the class.
     * Style bits are also inherited from superclasses.
     * </p>
     *
     * @param parent a widget which will be the parent of the new instance (cannot be null)
     * @param style  the style of widget to construct
     * @throws IllegalArgumentException <ul>
     *                                  <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
     *                                  </ul>
     * @throws SWTException             <ul>
     *                                  <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
     *                                  </ul>
     * @see SWT#NO_BACKGROUND
     * @see SWT#NO_FOCUS
     * @see SWT#NO_MERGE_PAINTS
     * @see SWT#NO_REDRAW_RESIZE
     * @see SWT#NO_RADIO_GROUP
     * @see SWT#EMBEDDED
     * @see SWT#DOUBLE_BUFFERED
     * @see Widget#getStyle
     */
    //TODO добавить кнопку сброса настроек на стандартные
    public ControlSetting(MainShell parent, int style) {
        super(parent, style);

        setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        FormLayout formLayout = new FormLayout();
        formLayout.marginBottom = 5;
        formLayout.marginRight = 5;
        formLayout.marginLeft = 5;
        formLayout.marginTop = 20;
        setLayout(formLayout);

        buttonComposite = new ButtonComposite(this, SWT.NONE);
        FormData compositeButtonData = new FormData();
        compositeButtonData.right = new FormAttachment(100);
        compositeButtonData.left = new FormAttachment(0);
        compositeButtonData.bottom = new FormAttachment(100);
        buttonComposite.setLayoutData(compositeButtonData);
        buttonComposite.getBtnExit().addListener(SWT.Selection, e -> parent.setComposite(new SettingMenu(parent, SWT.NONE)));
        buttonComposite.getBtnApply().addListener(SWT.Selection, e -> {
            try {
                Setting setting = Setting.getInstance();

                HashMap<Integer, Integer> control = new HashMap<>(4);
                control.put(Controllable.UP,    codeKey(txtMoveUp.getText()));
                control.put(Controllable.RIGHT, codeKey(txtMoveRight.getText()));
                control.put(Controllable.DOWN,  codeKey(txtMoveDown.getText()));
                control.put(Controllable.LEFT,  codeKey(txtMoveLeft.getText()));

                setting.setControlOver(tbtmPlayer.getText(), control);
                Setting.getInstance().store();
            } catch (IOException exc) {
                if (Main.debug) exc.printStackTrace();
                //TODO добавить предупреждение для пользователя
            }
            parent.setComposite(new SettingMenu(parent, SWT.NONE));
        });

        createContents();

        FocusListener onEdit = new FocusListener() {
            private String oldText;

            @Override
            public void focusGained(FocusEvent e) {
                if (!(e.widget instanceof Text)) return;
                Text item = (Text) e.widget;
                oldText = item.getText();
                item.setText("");
                item.setText("Press any key...");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!(e.widget instanceof Text)) return;
                Text item = (Text) e.widget;
                if (item.getText().equals("Press any key...") || item.getText().equals("")) item.setText(oldText);
            }
        };

        KeyAdapter onPressed = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!(e.widget instanceof Text)) return;
                Text item = (Text) e.widget;
                item.setText(decodeKey(e.keyCode));
            }
        };

        txtMoveUp.addFocusListener(onEdit);
        txtMoveUp.addKeyListener(onPressed);

        txtMoveRight.addFocusListener(onEdit);
        txtMoveRight.addKeyListener(onPressed);

        txtMoveDown.addFocusListener(onEdit);
        txtMoveDown.addKeyListener(onPressed);

        txtMoveLeft.addFocusListener(onEdit);
        txtMoveLeft.addKeyListener(onPressed);
    }

    protected void createContents() {

        TabFolder tabFolder = new TabFolder(this, SWT.NONE);
        FormData tabFolderData = new FormData();
        tabFolderData.top = new FormAttachment(0);
        tabFolderData.left = new FormAttachment(0);
        tabFolderData.right = new FormAttachment(100);
        tabFolderData.bottom = new FormAttachment(buttonComposite, -3);
        tabFolder.setLayoutData(tabFolderData);

        Setting setting = Setting.getInstance();
        tbtmPlayer = new TabItem(tabFolder, SWT.NONE);
        String currentPlayer = setting.getPlayerNames()[0];
        tbtmPlayer.setText(currentPlayer);

        Composite compositeMenu = new Composite(tabFolder, SWT.NONE);
        tbtmPlayer.setControl(compositeMenu);
        compositeMenu.setLayout(new GridLayout(2, true));


        //TODO [REFACTOR] сделать возможность изменять управления не только у первого игрока
        HashMap<Integer, Integer> control = setting.getControlOver(currentPlayer);

        Label lblMoveUp = new Label(compositeMenu, SWT.NONE);
        lblMoveUp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        lblMoveUp.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblMoveUp.setText("Move up");


        txtMoveUp = new Text(compositeMenu, SWT.NONE);
        txtMoveUp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        txtMoveUp.setText(decodeKey(control.get(Controllable.UP)));
        txtMoveUp.setEditable(false);

        Label lblMoveDown = new Label(compositeMenu, SWT.NONE);
        lblMoveDown.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        lblMoveDown.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblMoveDown.setText("Move down");


        txtMoveDown = new Text(compositeMenu, SWT.NONE);
        txtMoveDown.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        txtMoveDown.setText(decodeKey(control.get(Controllable.DOWN)));
        txtMoveDown.setEditable(false);

        Label lblMoveLeft = new Label(compositeMenu, SWT.NONE);
        lblMoveLeft.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        lblMoveLeft.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblMoveLeft.setText("Move left");


        txtMoveLeft = new Text(compositeMenu, SWT.NONE);
        txtMoveLeft.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        txtMoveLeft.setText(decodeKey(control.get(Controllable.LEFT)));
        txtMoveLeft.setEditable(false);

        Label lblMoveRight = new Label(compositeMenu, SWT.NONE);
        lblMoveRight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        lblMoveRight.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblMoveRight.setText("Move right");


        txtMoveRight = new Text(compositeMenu, SWT.NONE);
        txtMoveRight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        txtMoveRight.setText(decodeKey(control.get(Controllable.RIGHT)));
        txtMoveRight.setEditable(false);


        TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
        tabItem.setText("New Player");
    }

    private static String decodeKey(int key) {
        String result;
        switch (key) {
            case SWT.ARROW_UP:
                result = "up";
                break;
            case SWT.ARROW_RIGHT:
                result = "right";
                break;
            case SWT.ARROW_DOWN:
                result = "down";
                break;
            case SWT.ARROW_LEFT:
                result = "left";
                break;
            default:
                result = Character.toString((char) key);
                break;
        }
        return result;
    }

    private static Integer codeKey(String keyName) {
        Integer result;
        switch (keyName) {
            case "up":
                result = SWT.ARROW_UP;
                break;
            case "right":
                result = SWT.ARROW_RIGHT;
                break;
            case "down":
                result = SWT.ARROW_DOWN;
                break;
            case "left":
                result = SWT.ARROW_LEFT;
                break;
            default:
                result = (int) keyName.charAt(0);
                break;
        }
        return result;
    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }
}
