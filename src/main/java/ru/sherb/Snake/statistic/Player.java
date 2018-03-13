package ru.sherb.Snake.statistic;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sherb on 19.12.2016.
 */
@Data
@XmlRootElement(name = "player")
public final class Player {
    private String name;
    private int score;
    /**
     * Max game time (ms)
     */
    private long time;

    @Override
    public String toString() {
        return name + ", you score = " + score + "\n";
    }
}
