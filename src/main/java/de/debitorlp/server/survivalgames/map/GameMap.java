package de.debitorlp.server.survivalgames.map;

import java.util.HashMap;
import java.util.List;

import de.debitorlp.server.survivalgames.database.tables.Location;
import de.debitorlp.server.survivalgames.enm.Type;

public class GameMap {

    private Type type;
    private String mapName;
    private String author;

    private HashMap<Integer, org.bukkit.Location> locations = new HashMap<Integer, org.bukkit.Location>();

    public GameMap(List<Location> location, String author, Type type, String mapName) {
        this.type = type;
        this.mapName = mapName;
        this.author = author;
        for (Location loc : location) {
            locations.put(loc.getNumber(), new org.bukkit.Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(),
                loc.getYaw(), loc.getPitch()));
        }
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

    public HashMap<Integer, org.bukkit.Location> getLocations() {
        return locations;
    }

    public void setLocations(HashMap<Integer, org.bukkit.Location> locations) {
        this.locations = locations;
    }

    public void addLocations(Integer number, org.bukkit.Location location) {
        this.locations.put(number, location);
    }

    public void removeLocation(Integer number) {
        this.locations.remove(number);
    }

}
