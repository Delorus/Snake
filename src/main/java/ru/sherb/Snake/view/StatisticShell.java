package ru.sherb.Snake.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
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

    public static final int NAME_INDEX = 0;
    public static final int SCORE_INDEX = 1;
    public static final int TIME_INDEX = 2;
    public static final LongFunction<String> DEFAULT_TIME_FORMATTER = (time) ->
            String.valueOf(time / 1000) + " sec.";

    public StatisticShell(Display display) {
        super(display, SWT.SHELL_TRIM);
        setText("Statistics");
        setLayout(new FillLayout());
    }

    public void customize() {
        Table table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
        table.setHeaderVisible(true);
        TableColumn nameColumn = new TableColumn(table, SWT.NONE, NAME_INDEX);
        nameColumn.setText("Name");
        TableColumn scoreColumn = new TableColumn(table, SWT.NONE, SCORE_INDEX);
        scoreColumn.setText("Score");
        TableColumn timeColumn = new TableColumn(table, SWT.NONE, TIME_INDEX);
        timeColumn.setText("Time");

        // TODO: 13.03.2018 move to controller
        PlayerStatisticLoader statisticLoader = PlayerStatisticLoader.getInstance();
        List<Player> players = statisticLoader.getAllPlayers();

        players.sort(Comparator
                .comparingInt(Player::getScore)
                .reversed()
                .thenComparing(Comparator.comparingLong(Player::getTime)));

        mapPlayersToTableItems(players, table);

        final Listener onSortColumn = (e) -> {
            TableColumn column = (TableColumn) e.widget;

            Comparator<Player> comparator = getComparator(getColumnIndex(column, table));
            if (table.getSortColumn() != null && table.getSortColumn().getText().equals(column.getText())) {
                int sortDirection = table.getSortDirection();
                if (sortDirection == SWT.DOWN || sortDirection == SWT.NONE) {
                    table.setSortDirection(SWT.UP);
                } else {
                    table.setSortDirection(SWT.DOWN);
                    comparator = comparator.reversed();
                }
            } else {
                table.setSortDirection(SWT.UP);
            }
            table.setSortColumn(column);

            for (TableItem item : table.getItems()) {
                item.dispose();
            }
            players.sort(comparator);
            mapPlayersToTableItems(players, table);
        };

        nameColumn.addListener(SWT.Selection, onSortColumn);
        scoreColumn.addListener(SWT.Selection, onSortColumn);
        timeColumn.addListener(SWT.Selection, onSortColumn);

        Stream.of(NAME_INDEX, SCORE_INDEX, TIME_INDEX).forEach(i -> table.getColumn(i).pack());
        pack();
    }

    private static int getColumnIndex(TableColumn column, Table table) {
        TableColumn[] columns = table.getColumns();
        for (int i = 0; i < columns.length; i++) {
            if (column.getText().equals(columns[i].getText())) {
                return i;
            }
        }
        return -1;
    }

    private static Comparator<Player> getComparator(int columnIndex) {
        switch (columnIndex) {
            case NAME_INDEX:
                return Comparator.comparing(Player::getName);
            case SCORE_INDEX:
                return Comparator.comparingInt(Player::getScore).reversed();
            case TIME_INDEX:
                return Comparator.comparingLong(Player::getTime).reversed();
            default:
                throw new IllegalArgumentException("column with index " + columnIndex + " not found");
        }
    }

    private void mapPlayersToTableItems(List<Player> players, Table table) {
        for (Player player : players) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(NAME_INDEX, player.getName());
            item.setText(SCORE_INDEX, String.valueOf(player.getScore()));
            item.setText(TIME_INDEX, DEFAULT_TIME_FORMATTER.apply(player.getTime()));
        }
    }

    @Override
    protected void checkSubclass() {

    }
}
