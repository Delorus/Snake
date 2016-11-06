package ru.sherb.Snake.util;

import org.eclipse.swt.SWT;
import ru.sherb.Snake.Main;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by sherb on 04.11.2016.
 */
public class SettingHelper {
    private String path;
    private Properties defaultSetting;

    private int ScreenSizeX;
    private int ScreenSizeY;
    private boolean Fullscreen;

    private int Grid_HEIGHT;
    private Color Grid_COLOR;

    //TODO придумать где хранить управление змейками
    private int Player1_UP;
    private int Player1_DOWN;
    private int Player1_RIGHT;
    private int Player1_LEFT;
    private Color Player1_COLOR;

    private Color Fruit_COLOR;


    public SettingHelper(String pathToSetting) {
        path = pathToSetting;
        HashMap<Integer, Integer> test = new HashMap<>();
    }

    public SettingHelper loadOrDefault() throws IOException {
        if (defaultSetting == null) this.setDefault();
        Properties setting = new Properties(defaultSetting);
        try (InputStream in = new FileInputStream(path)) {
            setting.load(in);
        } catch (FileNotFoundException e) {
            setting.putAll(defaultSetting);
        }

        ScreenSizeX = Integer.valueOf(setting.getProperty("ScreenSizeX"));
        ScreenSizeY = Integer.valueOf(setting.getProperty("ScreenSizeY"));
        Fullscreen = Boolean.parseBoolean(setting.getProperty("Fullscreen"));

        Grid_HEIGHT = Integer.valueOf(setting.getProperty("Grid_HEIGHT"));
        Grid_COLOR = Color.decode(setting.getProperty("Grid_COLOR"));

        Player1_UP = Integer.valueOf(setting.getProperty("Player1_UP"));
        Player1_DOWN = Integer.valueOf(setting.getProperty("Player1_DOWN"));
        Player1_RIGHT = Integer.valueOf(setting.getProperty("Player1_RIGHT"));
        Player1_LEFT = Integer.valueOf(setting.getProperty("Player1_LEFT"));
        Player1_COLOR = Color.decode(setting.getProperty("Player1_COLOR"));

        Fruit_COLOR = Color.decode(setting.getProperty("Fruit_COLOR"));
        //...

        return this;
    }

    public void storeOrDefault() throws IOException {
        if (defaultSetting == null) this.setDefault();

        Properties setting = new Properties(defaultSetting);
        setting.setProperty("ScreenSizeX", String.valueOf(ScreenSizeX));
        setting.setProperty("ScreenSizeY", String.valueOf(ScreenSizeY));
        setting.setProperty("Fullscreen", String.valueOf(Fullscreen));

        setting.setProperty("Grid_HEIGHT", String.valueOf(Grid_HEIGHT));
        setting.setProperty("Grid_COLOR", String.valueOf(Grid_COLOR));

        setting.setProperty("Player1_UP", String.valueOf(Player1_UP));
        setting.setProperty("Player1_DOWN", String.valueOf(Player1_DOWN));
        setting.setProperty("Player1_RIGHT", String.valueOf(Player1_RIGHT));
        setting.setProperty("Player1_LEFT", String.valueOf(Player1_LEFT));
        setting.setProperty("Player1_COLOR", String.valueOf(Player1_COLOR));

        setting.setProperty("Fruit_COLOR", String.valueOf(Fruit_COLOR));
        //...

        try (OutputStream out = new FileOutputStream(path)) {
            setting.store(out, "Snake setting");
        }

    }

    public void setDefault() {
        Properties setting = new Properties();

        setting.setProperty("ScreenSizeX", "864");
        setting.setProperty("ScreenSizeY", "486");
        setting.setProperty("Fullscreen", "false");

        setting.setProperty("Grid_HEIGHT", "18");
        setting.setProperty("Grid_COLOR", String.valueOf(Color.WHITE.getRGB()));

        setting.setProperty("Player1_UP", String.valueOf(SWT.ARROW_UP));
        setting.setProperty("Player1_DOWN", String.valueOf(SWT.ARROW_DOWN));
        setting.setProperty("Player1_RIGHT", String.valueOf(SWT.ARROW_RIGHT));
        setting.setProperty("Player1_LEFT", String.valueOf(SWT.ARROW_LEFT));
        setting.setProperty("Player1_COLOR", String.valueOf(Color.GREEN.getRGB()));

        setting.setProperty("Fruit_COLOR", String.valueOf(Color.RED.getRGB()));
        //...

        this.setDefault(setting);
    }

    public void setDefault(Properties defaultSetting) {
        this.defaultSetting = new Properties();
        this.defaultSetting.putAll(defaultSetting);
    }

    public int getScreenSizeX() {
        return ScreenSizeX;
    }

    public int getScreenSizeY() {
        return ScreenSizeY;
    }

    public boolean isFullscreen() {
        return Fullscreen;
    }

    public int getGrid_HEIGHT() {
        return Grid_HEIGHT;
    }

    public int getPlayer1_UP() {
        return Player1_UP;
    }

    public int getPlayer1_DOWN() {
        return Player1_DOWN;
    }

    public int getPlayer1_RIGHT() {
        return Player1_RIGHT;
    }

    public int getPlayer1_LEFT() {
        return Player1_LEFT;
    }

    public Color getGrid_COLOR() {
        return Grid_COLOR;
    }

    public Color getPlayer1_COLOR() {
        return Player1_COLOR;
    }

    public Color getFruit_COLOR() {
        return Fruit_COLOR;
    }
}
