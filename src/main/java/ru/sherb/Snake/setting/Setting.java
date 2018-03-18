package ru.sherb.Snake.setting;

import org.eclipse.swt.SWT;
import ru.sherb.Snake.model.Controllable;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by sherb on 04.11.2016.
 */
public final class Setting {
    private static final Setting ourInstance = new Setting();

    private String path;

    private Properties defaultSetting;
    private final Properties setting;

    public static Setting getInstance() {
        return ourInstance;
    }

    private Setting() {
        setDefault();
        setting = new Properties(defaultSetting);
    }

    public void setPath(String pathToSetting) {
        path = pathToSetting;
    }

    // загрузка настроек из файла, либо из {@link defaultSetting}
    public void loadOrDefault() throws IOException {
        try (InputStream in = new FileInputStream(path)) {
            setting.load(in);
        } catch (FileNotFoundException e) {
            setting.putAll(defaultSetting);
            storeDefault();
        }
    }

    // сохранение настроек в ПЗУ
    public void store() throws IOException {
        try (OutputStream out = new FileOutputStream(path)) {
            setting.store(out, "Snake setting");
        }

    }

    // сохранение стандартных настроек в ПЗУ
    private void storeDefault() throws IOException {
        try (OutputStream out = new FileOutputStream(path)) {
            defaultSetting.store(out, "Snake setting");
        }
    }

    public void resetToDefault() {
        setting.clear();
        setting.putAll(defaultSetting);
    }


    private void setDefault() {
        Properties defaultSetting = new Properties();

        defaultSetting.setProperty("ScreenSizeX", "864");
        defaultSetting.setProperty("ScreenSizeY", "486");
        defaultSetting.setProperty("Fullscreen", "false");

        defaultSetting.setProperty("Grid_HEIGHT", "18");
        defaultSetting.setProperty("Grid_COLOR", String.valueOf(Color.WHITE.getRGB()));

        defaultSetting.setProperty("Player1_NAME", "Player1");
        defaultSetting.setProperty("Player1_" + Controllable.UP, String.valueOf(SWT.ARROW_UP));
        defaultSetting.setProperty("Player1_" + Controllable.DOWN, String.valueOf(SWT.ARROW_DOWN));
        defaultSetting.setProperty("Player1_" + Controllable.LEFT, String.valueOf(SWT.ARROW_LEFT));
        defaultSetting.setProperty("Player1_" + Controllable.RIGHT, String.valueOf(SWT.ARROW_RIGHT));

        defaultSetting.setProperty("Player1_COLOR", String.valueOf(Color.GREEN.getRGB()));

        defaultSetting.setProperty("Fruit_COLOR", String.valueOf(Color.RED.getRGB()));

        defaultSetting.setProperty("PlayersStatisticPath", "players_statistic.xml");
        defaultSetting.setProperty("TransparentBorder", "true");
        //...

        this.setDefault(defaultSetting);
    }

    protected Properties getSetting() {
        return setting;
    }

    protected Properties getDefaultSetting() {
        return defaultSetting;
    }

    private void setDefault(Properties defaultSetting) {
        this.defaultSetting = new Properties();
        this.defaultSetting.putAll(defaultSetting);
    }

    public int getScreenSizeX() {
        return Integer.parseInt(setting.getProperty("ScreenSizeX"));
    }

    public int getScreenSizeY() {
        return Integer.parseInt(setting.getProperty("ScreenSizeY"));
    }

    public boolean isFullscreen() {
        return Boolean.parseBoolean(setting.getProperty("Fullscreen"));
    }

    public int getGrid_HEIGHT() {
        return Integer.parseInt(setting.getProperty("Grid_HEIGHT"));
    }

    public Map<Integer, Integer> getControlOver(String playerName) {
        HashMap<Integer, Integer> control = new HashMap<>(4);
        if (setting.getProperty(playerName + "_" + Controllable.UP) == null) {
            return Collections.emptyMap();
        }
        control.put(Controllable.UP, Integer.valueOf(setting.getProperty(playerName + "_" + Controllable.UP)));
        control.put(Controllable.DOWN, Integer.valueOf(setting.getProperty(playerName + "_" + Controllable.DOWN)));
        control.put(Controllable.LEFT, Integer.valueOf(setting.getProperty(playerName + "_" + Controllable.LEFT)));
        control.put(Controllable.RIGHT, Integer.valueOf(setting.getProperty(playerName + "_" + Controllable.RIGHT)));
        return control;
    }

    public String[] getPlayerNames() {
        ArrayList<String> result = new ArrayList<>();
        int i = 1;
        do {
            result.add(setting.getProperty("Player" + i++ + "_NAME"));
        } while (Objects.nonNull(setting.getProperty("Player" + i + "_NAME")));
        return result.toArray(new String[0]);
    }

    public Color getGrid_COLOR() {
        return Color.decode(setting.getProperty("Grid_COLOR"));
    }

    public Color getPlayer_COLOR(String playerName) {
        return Color.decode(setting.getProperty(playerName + "_COLOR"));
    }

    public Color getFruit_COLOR() {
        return Color.decode(setting.getProperty("Fruit_COLOR"));
    }

    public Path getStatisticPath() {
        return Paths.get(setting.getProperty("PlayersStatisticPath"));
    }

    public boolean getFullscreen() {
        return Boolean.parseBoolean(setting.getProperty("Fullscreen"));
    }

    public void setScreenSizeX(int width) {
        setting.setProperty("ScreenSizeX", String.valueOf(width));
    }

    public void setScreenSizeY(int height) {
        setting.setProperty("ScreenSizeY", String.valueOf(height));
    }

    public void setFullscreen(boolean fullscreen) {
        setting.setProperty("Fullscreen", String.valueOf(fullscreen));
    }

    public void setGrid_HEIGHT(int gridHeight) {
        setting.setProperty("Grid_HEIGHT", String.valueOf(gridHeight));
    }

    public void setControlOver(String playerName, Map<Integer, Integer> control) {
        setting.setProperty(playerName + "_" + Controllable.UP, String.valueOf(control.get(Controllable.UP)));
        setting.setProperty(playerName + "_" + Controllable.DOWN, String.valueOf(control.get(Controllable.DOWN)));
        setting.setProperty(playerName + "_" + Controllable.LEFT, String.valueOf(control.get(Controllable.LEFT)));
        setting.setProperty(playerName + "_" + Controllable.RIGHT, String.valueOf(control.get(Controllable.RIGHT)));
    }

    public void setGrid_COLOR(Color color) {
        setting.setProperty("Grid_COLOR", String.valueOf(color.getRGB()));
    }

    public void setPlayer_COLOR(String playerName, Color playerColor) {
        setting.setProperty(playerName + "_COLOR", String.valueOf(playerColor.getRGB()));
    }

    public void setFruit_COLOR(Color fruitColor) {
        setting.setProperty("Fruit_COLOR", String.valueOf(fruitColor.getRGB()));
    }

    public void setPlayerName(int playerNum, String playerName) {
        setting.setProperty("Player" + playerNum + "_NAME", playerName);
    }

    public void setStatisticPath(String path) {
        setting.setProperty("PlayersStatisticPath", path);
    }

    public boolean getTransparentBorder() {
        return Boolean.parseBoolean(setting.getProperty("TransparentBorder"));
    }

    public void setTransparentBorder(boolean transparentBorder) {
        setting.setProperty("TransparentBorder", String.valueOf(transparentBorder));
    }
}
