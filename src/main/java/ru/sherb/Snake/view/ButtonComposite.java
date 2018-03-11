package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import ru.sherb.Snake.Main;

/**
 * Created by sherb on 29.11.2016.
 */
class ButtonComposite extends Composite {
    private Button btnExit;
    private Button btnApply;

    public ButtonComposite(Composite parent, int style) {
        super(parent, style);
        setLayout(new GridLayout(2, true));

        setBackground(Main.display.getSystemColor(SWT.COLOR_TRANSPARENT));

        createComponents();

    }

    private void createComponents() {
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
