package ru.sherb.Snake.util;

import org.eclipse.swt.SWT;
import ru.sherb.Snake.model.Controllable;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by sherb on 04.11.2016.
 */
public class SettingHelper {
    private static SettingHelper ourInstance = new SettingHelper();

    private String path;

    private Properties defaultSetting;
    private Properties setting;

    public static SettingHelper getInstance() {
        return ourInstance;
    }

    private SettingHelper() {
        setDefault();
        setting = new Properties(defaultSetting);
    }

    public void setPath(String pathToSetting) {
        path = pathToSetting;
    }

    public SettingHelper loadOrDefault() throws IOException {
        //TODO [DEBUG] если в настройках отсутствует хотя бы один параметр, происходит ошибка
        try (InputStream in = new FileInputStream(path)) {
            setting.load(in);
        } catch (FileNotFoundException e) {
            setting.putAll(defaultSetting);
        }
        return this;
    }


    public void store() throws IOException {
        try (OutputStream out = new FileOutputStream(path)) {
            setting.store(out, "Snake setting");
        }

    }

    public void storeDefault() throws IOException {
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

        defaultSetting.setProperty("Player1_" + Controllable.UP, String.valueOf(SWT.ARROW_UP));
        defaultSetting.setProperty("Player1_" + Controllable.DOWN, String.valueOf(SWT.ARROW_DOWN));
        defaultSetting.setProperty("Player1_" + Controllable.LEFT, String.valueOf(SWT.ARROW_LEFT));
        defaultSetting.setProperty("Player1_" + Controllable.RIGHT, String.valueOf(SWT.ARROW_RIGHT));

        defaultSetting.setProperty("Player1_COLOR", String.valueOf(Color.GREEN.getRGB()));

        defaultSetting.setProperty("Fruit_COLOR", String.valueOf(Color.RED.getRGB()));
        //...

        this.setDefault(defaultSetting);
    }

    private void setDefault(Properties defaultSetting) {
        this.defaultSetting = new Properties();
        this.defaultSetting.putAll(defaultSetting);
    }

    public int getScreenSizeX() {
        return Integer.valueOf(setting.getProperty("ScreenSizeX"));
    }

    public int getScreenSizeY() {
        return Integer.valueOf(setting.getProperty("ScreenSizeY"));
    }

    public boolean isFullscreen() {
        return Boolean.parseBoolean(setting.getProperty("Fullscreen"));
    }

    public int getGrid_HEIGHT() {
        return Integer.valueOf(setting.getProperty("Grid_HEIGHT"));
    }

    public HashMap<Integer, Integer> getControlOver(String playerName) {
        HashMap<Integer, Integer> control = new HashMap<>(4);
        if (setting.getProperty(playerName + "_" + Controllable.UP) != null)
        for (int j = 0; j < 4; j++) {
            control.put(j, Integer.valueOf(setting.getProperty(playerName + "_" + j)));

        }
        return control;
    }

    public Color getGrid_COLOR() {
        return Color.decode(setting.getProperty("Grid_COLOR"));
    }

    public Color getPlayer1_COLOR() {
        return Color.decode(setting.getProperty("Player1_COLOR"));
    }

    public Color getFruit_COLOR() {
        return Color.decode(setting.getProperty("Fruit_COLOR"));
    }
}
