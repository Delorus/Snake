package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import ru.sherb.Snake.Main;

/**
 * Created by sherb on 09.11.2016.
 */
public class GraphicSetting extends Composite {
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
    public GraphicSetting(MainShell parent, int style) {
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


        Label lblResolution = new Label(compositeMenu, SWT.NONE);
        lblResolution.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        lblResolution.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblResolution.setText("Resolution");

        Combo comboCellCount = new Combo(compositeMenu, SWT.READ_ONLY);
        comboCellCount.setItems("wait...");
        comboCellCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Label lblFullscreen = new Label(compositeMenu, SWT.NONE);
        lblFullscreen.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));
        lblFullscreen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        lblFullscreen.setText("Fullscreen");

        Button btnFullscreen = new Button(compositeMenu, SWT.CHECK);
        btnFullscreen.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, false, false, 1, 1));
        btnFullscreen.setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));



        Button btnExit = new Button(compositeButton, SWT.NONE);
        btnExit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnExit.setText("Exit");
        btnExit.addListener(SWT.Selection, e -> parent.setComposite(new SettingMenu(parent, SWT.NONE)));

        Button btnApply = new Button(compositeButton, SWT.NONE);
        btnApply.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnApply.setText("Apply");
        btnExit.addListener(SWT.Selection, e -> {
        });
    }


    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }
}
