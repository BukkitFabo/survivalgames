package de.debitorlp.server.survivalgames.editmode;

import java.util.HashMap;

import de.debitorlp.server.survivalgames.database.tables.Location;
import de.debitorlp.server.survivalgames.database.tables.Map;

public class EditMap {

    private Map map;
    private HashMap<Integer, Location> location = new HashMap<Integer, Location>();

    public EditMap(Map map) {
        this.map = map;
    }

    public EditMap(Map map, HashMap<Integer, Location> location) {
        this.map = map;
        this.location = location;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public HashMap<Integer, Location> getLocation() {
        return location;
    }

    public void setLocation(HashMap<Integer, Location> location) {
        this.location = location;
    }

    public void addLocation(int number, Location location) {
        this.location.put(number, location);
    }

    public void removeLocation(int number) {
        this.location.remove(number);
    }

    public boolean containsLocation(int number) {
        return this.location.containsKey(number);
    }

}
