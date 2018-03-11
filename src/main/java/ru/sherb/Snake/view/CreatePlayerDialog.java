package ru.sherb.Snake.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import ru.sherb.Snake.Main;
import ru.sherb.Snake.setting.Setting;
import ru.sherb.Snake.util.AwtToSwt;

import java.util.ArrayList;
import java.util.List;

public final class CreatePlayerDialog {

    private final Shell shell;
    @Getter
    private final List<Player> players = new ArrayList<>();

    public CreatePlayerDialog(Shell parent) {
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setText("Create new Player");
        shell.setLocation(parent.getClientArea().width / 2, parent.getClientArea().height / 2);
        shell.setLayout(new GridLayout(1, true));
    }

    public void open() {
        List<PlayerComposite> playerComposites = new ArrayList<>();
        String[] names = Setting.getInstance().getPlayerNames();
        // default pick first player
        PlayerComposite firstPlayer = new PlayerComposite(shell, SWT.BORDER);
        firstPlayer.customize(names[0]);
        firstPlayer.setLayoutData(new GridData(GridData.FILL_BOTH));
        playerComposites.add(firstPlayer);

        ButtonComposite controls = new ButtonComposite(shell, SWT.NONE);
        controls.getBtnApply().addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // TODO: 11.03.2018 validate....
                for (PlayerComposite composite : playerComposites) {
                    players.add(Player.of(composite.name.getText(), AwtToSwt.toAwtColor(composite.color)));
                }
                shell.dispose();
            }
        });
        controls.getBtnExit().addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.dispose();
            }
        });

        shell.pack();
        shell.open();
    }

    public boolean isDisposed() {
        return shell.isDisposed();
    }

    private static class PlayerComposite extends Composite {

        private final Shell parent;
        @Getter
        private Color color;
        @Getter
        private Text name;

        public PlayerComposite(Composite parent, int style) {
            super(parent, style);
            this.parent = parent.getShell();
        }

        public void customize(String defaultName) {
            GridLayout layout = new GridLayout(2, false);
            this.setLayout(layout);

            Label nameLabel = new Label(this, SWT.NONE);
            nameLabel.setText("Player name:");
            nameLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
            name = new Text(this, SWT.SINGLE);
            name.setLayoutData(new GridData(GridData.FILL_BOTH));
            name.setText(defaultName);
            name.selectAll();
            // TODO: 11.03.2018 добавить для остальных игроков при выборе имени выделять всю строку

            Label colorLabel = new Label(this, SWT.NONE);
            colorLabel.setText("snake color:");
            colorLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
            Composite changeColor = new Composite(this, SWT.BORDER);
            GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
            gd_composite.heightHint = 20;
            changeColor.setLayoutData(gd_composite);
            // TODO: 11.03.2018 при смене имени, искать цвет для такого игрока в настройках и заполнять его
            color = AwtToSwt.toSwtColor(Setting.getInstance().getPlayer_COLOR(defaultName));
            changeColor.setBackground(color);
            changeColor.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseDown(MouseEvent e) {
                    ColorDialog colorDialog = new ColorDialog(parent);
                    colorDialog.setRGB(color.getRGB());
                    colorDialog.setText("Choose the snake color");
                    RGB newColor = colorDialog.open();
                    if (newColor != null) {
                        color.dispose();
                        color = new Color(Main.display, newColor);
                        changeColor.setBackground(color);
                    }
                }
            });

            layout();
            pack();
        }
    }

    @Value
    @AllArgsConstructor(staticName = "of")
    public static class Player {
        String name;
        java.awt.Color color;
    }
}
