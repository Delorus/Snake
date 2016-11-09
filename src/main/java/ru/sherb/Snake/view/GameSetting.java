package ru.sherb.Snake.view;

/**
 * Created by sherb on 09.11.2016.
 */

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import ru.sherb.Snake.Main;

public class GameSetting extends Composite {
        /**
         * Create the composite.
         * @param parent
         * @param style
         */
        //TODO при открытии загружать состояние кнопок из файла
        public GameSetting(MainShell parent, int style) {
            super(parent, style);

            setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
            FormLayout formLayout = new FormLayout();
            formLayout.marginBottom = 5;
            formLayout.marginRight = 5;
            formLayout.marginLeft = 5;
            formLayout.marginTop = 20;
            setLayout(formLayout);

            Composite compositeButton = new Composite(this, SWT.NONE);
            compositeButton.setLayout(new GridLayout(2, true));
            FormData compositeButtonData = new FormData();
            compositeButtonData.right = new FormAttachment(100);
            compositeButtonData.left = new FormAttachment(0);
            compositeButtonData.bottom = new FormAttachment(100);
            compositeButton.setLayoutData(compositeButtonData);
            compositeButton.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));

            Composite compositeMenu = new Composite(this, SWT.NONE);
            compositeMenu.setLayout(new GridLayout(2, true));
            FormData compositeMenuData = new FormData();
            compositeMenuData.top = new FormAttachment(0);
            compositeMenuData.left = new FormAttachment(0);
            compositeMenuData.right = new FormAttachment(100);
            compositeMenuData.bottom = new FormAttachment(compositeButton, -3);
            compositeMenu.setLayoutData(compositeMenuData);
            compositeMenu.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));


            Label lblCellsCount = new Label(compositeMenu, SWT.NONE);
            lblCellsCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            lblCellsCount.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
            lblCellsCount.setText("Cells count");

            Combo comboCellCount = new Combo(compositeMenu, SWT.READ_ONLY);
            comboCellCount.setItems("Quick - 9", "Normal - 18", "Long - 27", "Very long - 36");
            comboCellCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

            Label lblTransparentBorder = new Label(compositeMenu, SWT.NONE);
            lblTransparentBorder.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
            lblTransparentBorder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
            lblTransparentBorder.setText("Transparent border");

            Button btnTransparentBorder = new Button(compositeMenu, SWT.CHECK);
            btnTransparentBorder.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, false, false, 1, 1));
            btnTransparentBorder.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));

            Label lblWalls = new Label(compositeMenu, SWT.NONE);
            lblWalls.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
            lblWalls.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
            lblWalls.setText("Walls");

            Button btnWalls = new Button(compositeMenu, SWT.CHECK);
            btnWalls.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, false, false, 1, 1));
            btnWalls.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));




            Button btnExit = new Button(compositeButton, SWT.NONE);
            btnExit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            btnExit.setText("Exit");
            btnExit.addListener(SWT.Selection, e -> parent.setComposite(new SettingMenu(parent, SWT.NONE)));

            Button btnApply = new Button(compositeButton, SWT.NONE);
            btnApply.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            btnApply.setText("Apply");
            btnExit.addListener(SWT.Selection, e -> {});

        }

        @Override
        protected void checkSubclass() {
            // Disable the check that prevents subclassing of SWT components
        }
}
