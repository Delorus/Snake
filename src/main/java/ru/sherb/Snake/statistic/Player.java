package ru.sherb.Snake.statistic;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sherb on 19.12.2016.
 */
@Getter
@Setter
@XmlRootElement(name = "player")
public final class Player {
    private String name;
    private int score;
    private long time;

    @Override
    public String toString() {
        return name + ", you score = " + score + "\n";
    }
}
