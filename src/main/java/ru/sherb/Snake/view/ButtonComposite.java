package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Composite;
import ru.sherb.Snake.Main;

/**
 * Created by sherb on 29.11.2016.
 */
public class ButtonComposite extends Composite {
    private Button btnExit;
    private Button btnApply;

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
    public ButtonComposite(Composite parent, int style) {
        super(parent, style);
        setLayout(new GridLayout(2, true));

        setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));

        createComponents();

    }

    protected void createComponents() {
        btnExit = new Button(this, SWT.NONE);
        btnExit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnExit.setText("Exit");

        btnApply = new Button(this, SWT.NONE);
        btnApply.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnApply.setText("Apply");
    }

    public Button getBtnExit() {
        return btnExit;
    }

    public Button getBtnApply() {
        return btnApply;
    }
}
