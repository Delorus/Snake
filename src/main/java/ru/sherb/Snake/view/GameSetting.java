package ru.sherb.Snake.view;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.setting.Setting;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sherb on 09.11.2016.
 */
class GameSetting extends Composite {
    private final ButtonComposite buttonComposite;
    private Combo comboCellCount;
    private Button transparentBorder;

    /**
     * Create the composite.
     */
    public GameSetting(MainShell parent, int style) {
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
                Setting.getInstance().store();
            } catch (IOException exc) {
                if (Main.isDebug()) exc.printStackTrace();
            }
            parent.setComposite(new SettingMenu(parent, SWT.NONE));
        });

        createConponents();

        comboCellCount.addListener(SWT.Selection, e -> Setting.getInstance().setGrid_HEIGHT(Integer.valueOf(comboCellCount.getText())));

        transparentBorder.addListener(SWT.Selection, e ->
                Setting.getInstance().setTransparentBorder(transparentBorder.getSelection()));
    }

    private void createConponents() {
        Composite compositeMenu = new Composite(this, SWT.NONE);
        compositeMenu.setLayout(new GridLayout(2, true));
        FormData compositeMenuData = new FormData();
        compositeMenuData.top = new FormAttachment(0);
        compositeMenuData.left = new FormAttachment(0);
        compositeMenuData.right = new FormAttachment(100);
        compositeMenuData.bottom = new FormAttachment(buttonComposite, -3);
        compositeMenu.setLayoutData(compositeMenuData);
        compositeMenu.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));

        Setting setting = Setting.getInstance();

        Label lblCellsCount = new Label(compositeMenu, SWT.NONE);
        lblCellsCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        lblCellsCount.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblCellsCount.setText("Cells count");


        comboCellCount = new Combo(compositeMenu, SWT.READ_ONLY);
        ArrayList<String> buff = new ArrayList<>();
        buff.add("9");
        buff.add("18");
        buff.add("27");
        buff.add("36");
        comboCellCount.setItems(buff.toArray(new String[0]));
        comboCellCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        comboCellCount.select(buff.indexOf(String.valueOf(setting.getGrid_HEIGHT())));


        Label lblTransparentBorder = new Label(compositeMenu, SWT.NONE);
        lblTransparentBorder.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblTransparentBorder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        lblTransparentBorder.setText("Transparent border");


        transparentBorder = new Button(compositeMenu, SWT.CHECK);
        transparentBorder.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, false, false, 1, 1));
        transparentBorder.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        transparentBorder.setSelection(setting.getTransparentBorder());
    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }
}
