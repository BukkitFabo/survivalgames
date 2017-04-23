package de.debitorlp.server.survivalgames.editmode;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.database.dao.LocationDAO;
import de.debitorlp.server.survivalgames.database.dao.MapDAO;
import de.debitorlp.server.survivalgames.database.tables.Location;
import de.debitorlp.server.survivalgames.database.tables.Map;
import de.debitorlp.server.survivalgames.enm.Type;
import de.debitorlp.server.survivalgames.util.UnZip;
import de.debitorlp.server.survivalgames.util.Zip;

public class EditMode implements CopyOption {

    public static File copyMapToServer(String mapName, Path source) {
        if (Main.debugMode) {
            System.out.println("copy " + mapName + " from " + source.toString());
        }
        try {
            File file = new File(Main.getPlugin().getDataFolder() + "/Maps/" + mapName + ".zip");
            FileUtils.copyFile(new File(source.toString() + "/" + mapName + ".zip"), file);
            if (Main.debugMode) {
                System.out.println("Done!");
            }
            return file;
        } catch (IOException e) {
            if (Main.debugMode) {
                System.out.println("Failed!");
            }
            e.printStackTrace();

        }
        return null;
    }

    public static void copyMapToMapsFolder(String mapName, File zipFile, Path to) {
        if (Main.debugMode) {
            System.out.println("copy " + mapName + " to " + to.toString());
        }
        try {
            File file = new File(Main.getPlugin().getDataFolder() + "/Maps/" + mapName + ".zip");
            FileUtils.copyFile(file, new File(to.toString() + "/" + mapName + ".zip"));
            if (Main.debugMode) {
                System.out.println("Done!");
            }
        } catch (IOException e) {
            if (Main.debugMode) {
                System.out.println("Failed!");
            }
            e.printStackTrace();

        }
    }

    public static EditMap createMap(Type type, String mapName, String author, Player player) {

        Map map = new Map(type, mapName, "Unknown", 0);
        MapDAO mapDAO = Main.getSQL().attach(MapDAO.class);
        mapDAO.insertMap(type.name(), mapName, author, 0);
        EditMap editMap = new EditMap(map);

        return editMap;
    }

    public static EditMap loadMap(Player player, String mapName, Type type) {
        File file = new File(Main.getPlugin().getDataFolder(), "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        File mapfile = copyMapToServer(mapName, Paths.get(cfg.getString("standartMapFolder")));

        UnZip unZip = new UnZip(mapfile.getPath(), "./" + mapName);
        unZip.unZipIt();
        mapfile.delete();

        new WorldCreator(mapName).createWorld();

        MapDAO mapDAO = Main.getSQL().attach(MapDAO.class);
        Map map = mapDAO.getMap(type.name(), mapName);

        EditMap editMap = null;
        List<Location> locations = Main.getSQL().attach(LocationDAO.class).getLocations(type.name(), mapName);
        if (locations.isEmpty()) {
            System.out.println("Load map without locations");
            editMap = new EditMap(map);
            player.teleport(Bukkit.getWorld(map.getMapName()).getSpawnLocation());
        } else {
            System.out.println("Load map with locations");
            HashMap<Integer, Location> locs = new HashMap<Integer, Location>();
            for (Location location : locations) {
                locs.put(location.getNumber(), location);
            }
            editMap = new EditMap(map, locs);
            Location location = editMap.getLocation().get(1);
            player.teleport(new org.bukkit.Location(location.getWorld(), location.getX(), location.getY(),
                location.getZ(), location.getYaw(), location.getPitch()));
        }

        return editMap;
    }

    public static void updateMapLocation(Player player, EditMap editMap) {
        Main.getHoloAPI().destroyAll(player);
        for (Location location : editMap.getLocation().values()) {
            org.bukkit.Location loc = new org.bukkit.Location(location.getWorld(), location.getX(), location.getY(),
                location.getZ());
            ArrayList<String> lines = new ArrayList<String>();
            lines.add(location.getMapName() + " " + location.getNumber());
            Main.getHoloAPI().display(loc, lines, player);
        }
    }

    private static File convertMapToZipFile(String mapName) {
        File sourceFile = new File(mapName);
        File outputZipFile = new File(Main.getPlugin().getDataFolder() + "/Maps/" + mapName + ".zip");

        Zip zip = new Zip(sourceFile.getPath(), outputZipFile.getPath());
        zip.generateFileList(sourceFile);
        zip.zipIt();

        return outputZipFile;
    }

    private static void deleteMapFolder(String mapName) {
        try {
            FileUtils.deleteDirectory(new File(mapName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMap(Type type, String mapName, String author, EditMap editMap) {
        if (Main.debugMode) {
            System.out.println("Unloading map " + mapName);
        }
        Bukkit.getWorld(mapName).save();
        Bukkit.unloadWorld(mapName, true);
        if (Main.debugMode) {
            System.out.println("Done!");
        }

        LocationDAO locationDAO = Main.getSQL().attach(LocationDAO.class);
        for (Location location : editMap.getLocation().values()) {
            System.out.println("Location: " + location.getMapName() + " " + location.getNumber());
            if (locationDAO.getLocation(location.getType().name(), location.getMapName(),
                location.getNumber()) != null) {
                locationDAO.updateLocation(location.getType().name(), location.getMapName(), location.getNumber(),
                    location.getWorld().getName(), location.getX(), location.getY(), location.getZ(),
                    location.getYaw(), location.getPitch());
                System.out.println("update!");
            } else {
                locationDAO.insertLocation(location.getType().name(), location.getMapName(), location.getNumber(),
                    location.getWorld().getName(), location.getX(), location.getY(), location.getZ(),
                    location.getYaw(), location.getPitch());
                System.out.println("insert!");
            }
        }

        File file = new File(Main.getPlugin().getDataFolder(), "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        File zipFile = convertMapToZipFile(mapName);
        copyMapToMapsFolder(mapName, zipFile, Paths.get(cfg.getString("standartMapFolder")));

        if (Main.debugMode) {
            System.out.println("Deleting mapfolder and mapZip...");
        }
        new File(Main.getPlugin().getDataFolder().getPath() + "/Maps/" + mapName + ".zip").delete();
        deleteMapFolder(mapName);
        if (Main.debugMode) {
            System.out.println("Done!");
        }
    }

}
