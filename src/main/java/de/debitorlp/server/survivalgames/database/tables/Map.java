package de.debitorlp.server.survivalgames.database.tables;

import de.debitorlp.server.survivalgames.enm.Type;

public class Map {

    private Type type;
    private String mapName;
    private String author;
    private int size;

    public Map(Type type, String mapName, String author, int size) {
        this.type = type;
        this.mapName = mapName;
        this.author = author;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
