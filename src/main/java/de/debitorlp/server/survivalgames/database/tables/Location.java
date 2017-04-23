package de.debitorlp.server.survivalgames.database.tables;

import org.bukkit.World;

import de.debitorlp.server.survivalgames.enm.Type;

public class Location {

    private Type type;
    private String mapName;
    private int number;

    private World world;
    private double xloc;
    private double yloc;
    private double zloc;
    private float yaw;
    private float pitch;

    /**
     * @param type
     * @param mapName
     * @param number
     * @param world
     * @param xloc
     * @param yloc
     * @param zloc
     * @param yaw
     * @param pitch
     */
    public Location(Type type, String mapName, int number, World world, double xloc, double yloc, double zloc,
        float yaw, float pitch) {
        this.type = type;
        this.mapName = mapName;
        this.number = number;

        this.world = world;
        this.xloc = xloc;
        this.yloc = yloc;
        this.zloc = zloc;
        this.yaw = yaw;
        this.pitch = pitch;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public double getX() {
        return xloc;
    }

    public void setX(double xloc) {
        this.xloc = xloc;
    }

    public double getY() {
        return yloc;
    }

    public void setY(double yloc) {
        this.yloc = yloc;
    }

    public double getZ() {
        return zloc;
    }

    public void setZ(double zloc) {
        this.zloc = zloc;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

}
