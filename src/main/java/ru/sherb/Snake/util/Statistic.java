package ru.sherb.Snake.util;

//import org.xml.sax.


import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Вспомогательный класс для сохранения и загрузки статистики по игре в файл.
 * <p>
 * Created by sherb on 19.12.2016.
 */
public final class Statistic {
    private static final Statistic ourInstance = new Statistic();

    private String path;
    private DocumentBuilder documentBuilder;
    private Document document;

    public Statistic() {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        } catch (ParserConfigurationException e) {
            //TODO [REFACTOR] необрабатываемая ошибка, по сути она никогда не должна возникнуть, т.к. загружается со стандартными параметрами
        }
    }

    public void setPath(String path) throws IOException, SAXException {
        this.path = path;
        document = documentBuilder.parse(path);
    }

    public void setScore(String playerName, String score, long time) {

    }

    public String[] getBestScore() {
        //...
        return null;
    }




}
