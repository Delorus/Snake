package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import ru.sherb.Snake.statistic.Player;
import ru.sherb.Snake.statistic.PlayerStatisticLoader;

import java.util.Comparator;
import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.Stream;

public class StatisticShell extends Shell {

    public StatisticShell(Display display) {
        super(display, SWT.SHELL_TRIM);
        setText("Statistics");
        setLayout(new FillLayout());
    }

    public void customize() {
        Table table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
        table.setHeaderVisible(true);
        final int NAME_INDEX = 0;
        new TableColumn(table, SWT.NONE, NAME_INDEX).setText("Name");
        final int SCORE_INDEX = 1;
        new TableColumn(table, SWT.NONE, SCORE_INDEX).setText("Score");
        final int TIME_INDEX = 2;
        new TableColumn(table, SWT.NONE, TIME_INDEX).setText("Time");

        // TODO: 13.03.2018 move to controller
        PlayerStatisticLoader statisticLoader = PlayerStatisticLoader.getInstance();
        List<Player> players = statisticLoader.getAllPlayers();

        final Comparator<? super Player> defaultComparator = Comparator
                .comparingInt(Player::getScore)
                .reversed()
                .thenComparing(Comparator.comparingLong(Player::getTime));
        players.sort(defaultComparator);

        final LongFunction<String> defaultTimeFormatter = (time) ->
                String.valueOf(time / 1000) + " sec.";

        for (Player player : players) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(NAME_INDEX, player.getName());
            item.setText(SCORE_INDEX, String.valueOf(player.getScore()));
            item.setText(TIME_INDEX, defaultTimeFormatter.apply(player.getTime()));
        }

        Stream.of(NAME_INDEX, SCORE_INDEX, TIME_INDEX).forEach(i -> table.getColumn(i).pack());
        pack();
    }

    @Override
    protected void checkSubclass() {

    }
}
