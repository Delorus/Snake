package ru.sherb.Snake.statistic;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "players")
public final class PlayerList {

    @XmlElement(name = "player")
    private List<Player> players = new ArrayList<>();

    public boolean add(Player player) {
        return players.add(player);
    }
}
