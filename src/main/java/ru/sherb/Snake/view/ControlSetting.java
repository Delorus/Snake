package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.model.Controllable;
import ru.sherb.Snake.setting.Setting;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sherb on 09.11.2016.
 */
class ControlSetting extends Composite {
    private final ButtonComposite buttonComposite;
    private TabItem tbtmPlayer;
    private Text txtMoveUp;
    private Text txtMoveDown;
    private Text txtMoveLeft;
    private Text txtMoveRight;

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
                if (Main.isDebug()) exc.printStackTrace();
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

    private void createContents() {

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


        Map<Integer, Integer> control = setting.getControlOver(currentPlayer);

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
