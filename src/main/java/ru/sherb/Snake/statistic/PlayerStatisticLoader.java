package ru.sherb.Snake.statistic;

import ru.sherb.Snake.Main;
import ru.sherb.Snake.setting.Setting;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

public final class PlayerStatisticLoader {

    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private PlayerList players;

    private PlayerStatisticLoader() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(PlayerList.class);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            if (Main.isDebug()) e.printStackTrace();
        }
    }

    public static PlayerStatisticLoader getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void addAllRecord(PlayerList currentPlayers) {
        if (ensureLoad()) {
            players.getPlayers().addAll(currentPlayers.getPlayers());
        }
    }

    public boolean addRecord(Player record) {
        return ensureLoad() && players.add(record);
    }

    // TODO: 10.03.2018 very slow, but who cares ¯\_(ツ)_/¯
    public Optional<Player> getPlayerWithBestScore() {
        if (ensureLoad()) {
            return players.getPlayers().stream()
                    .max(Comparator.comparingInt(Player::getScore));
        }
        return Optional.empty();
    }

    public boolean save() {
        if (players == null) {
            return false;
        }

        Path statisticFile = Setting.getInstance().getStatisticPath();
        try (Writer writer = Files.newBufferedWriter(statisticFile, CREATE, WRITE)) {
            JAXBContext.newInstance(PlayerList.class).createMarshaller().marshal(players, writer);
        } catch (JAXBException | IOException e) {
            if (Main.isDebug()) e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean ensureLoad() {
        if (players == null) {
            Path statisticFile = Setting.getInstance().getStatisticPath();
            if (!Files.exists(statisticFile)) {
                players = new PlayerList();
            } else {
                try {
                    players = (PlayerList) unmarshaller.unmarshal(Files.newInputStream(statisticFile));
                } catch (JAXBException | IOException e) {
                    if (Main.isDebug()) e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    private static class InstanceHolder {
        private static final PlayerStatisticLoader INSTANCE = new PlayerStatisticLoader();
    }
}
