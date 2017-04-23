package de.debitorlp.server.survivalgames;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import de.debitorlp.server.survivalgames.chest.ChestInteractListener;
import de.debitorlp.server.survivalgames.chest.ChestManager;
import de.debitorlp.server.survivalgames.commands.ForceMapCommand;
import de.debitorlp.server.survivalgames.commands.ForceStartCommand;
import de.debitorlp.server.survivalgames.commands.SurvivalGamesCommand;
import de.debitorlp.server.survivalgames.database.Driver;
import de.debitorlp.server.survivalgames.database.dao.LocationDAO;
import de.debitorlp.server.survivalgames.database.dao.UserDAO;
import de.debitorlp.server.survivalgames.editmode.EditCommand;
import de.debitorlp.server.survivalgames.enm.GameStatus;
import de.debitorlp.server.survivalgames.enm.Type;
import de.debitorlp.server.survivalgames.listener.BlockBreakListener;
import de.debitorlp.server.survivalgames.listener.BlockPlaceListener;
import de.debitorlp.server.survivalgames.listener.CreatureSpawnListener;
import de.debitorlp.server.survivalgames.listener.EntityDamageListener;
import de.debitorlp.server.survivalgames.listener.EntityExplodeListener;
import de.debitorlp.server.survivalgames.listener.FoodLevelChangeListener;
import de.debitorlp.server.survivalgames.listener.InventoryClickListener;
import de.debitorlp.server.survivalgames.listener.PlayerDeathListener;
import de.debitorlp.server.survivalgames.listener.PlayerDropItemListener;
import de.debitorlp.server.survivalgames.listener.PlayerInteractAtEntityListener;
import de.debitorlp.server.survivalgames.listener.PlayerInteractEntityListener;
import de.debitorlp.server.survivalgames.listener.PlayerInteractListener;
import de.debitorlp.server.survivalgames.listener.PlayerJoinListener;
import de.debitorlp.server.survivalgames.listener.PlayerMoveListener;
import de.debitorlp.server.survivalgames.listener.PlayerPickUpItemListener;
import de.debitorlp.server.survivalgames.listener.PlayerQuitListener;
import de.debitorlp.server.survivalgames.listener.PlayerRespawnListener;
import de.debitorlp.server.survivalgames.util.HoloAPI;

public class Main extends JavaPlugin {

    public static String prefix = "§7[§l§bSurvivalGames§r§7] ";
    public static boolean editMode;
    public static boolean debugMode;

    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    private static HoloAPI holoAPI;

    public static HoloAPI getHoloAPI() {
        return holoAPI;
    }

    private static Handle SQL;

    public static Handle getSQL() {
        return SQL;
    }

    private static SurvivalGames survivalGames;

    public static SurvivalGames getSurvivalGames() {
        return survivalGames;
    }

    @Override
    public void onEnable() {
        Main.plugin = this;
        Main.survivalGames = new SurvivalGames(GameStatus.WAITING);
        Main.holoAPI = new HoloAPI();

        loadConfigurationFile();

        // check for editmode
        File file = new File(getDataFolder(), "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        editMode = cfg.getBoolean("editmode");
        debugMode = cfg.getBoolean("debugmode");

        // register some BungeeCordMessageChannel
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // register commands
        getCommand("SurvivalGames").setExecutor(new SurvivalGamesCommand());
        getCommand("ForceMap").setExecutor(new ForceMapCommand());
        getCommand("ForceStart").setExecutor(new ForceStartCommand());

        // register listener
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerRespawnListener(), this);
        pm.registerEvents(new PlayerDropItemListener(), this);
        pm.registerEvents(new PlayerPickUpItemListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PlayerInteractEntityListener(), this);
        pm.registerEvents(new PlayerInteractAtEntityListener(), this);
        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new CreatureSpawnListener(), this);
        pm.registerEvents(new EntityDamageListener(), this);
        pm.registerEvents(new EntityExplodeListener(), this);
        pm.registerEvents(new FoodLevelChangeListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);

        // load SurvivalGames chest
        pm.registerEvents(new ChestInteractListener(), this);
        ChestManager.loadItems();

        // load driver for MySQL database (we need this one because Bukkit ships
        // with its own, deprecated driver that
        // causes errors)
        try {
            new Driver();
        } catch (ClassNotFoundException | SQLException e) {
            getLogger().warning("Could not load MySQL driver. Plugin loading stopped");
            return;
        }

        // create connection with the database
        loadDatabase();

        if (!Main.editMode) {
            getSurvivalGames().loadMap("SGLobby", Type.LOBBY);
            getSurvivalGames().loadRandomArena();
            getSurvivalGames().loadRandomDeathMatchArena();
        } else {
            getCommand("Edit").setExecutor(new EditCommand());
        }

    }

    @Override
    public void onDisable() {
        // Delete World Lobby

        File lobby = getSurvivalGames().getLobby().getLocations().get(1).getWorld().getWorldFolder();
        Bukkit.unloadWorld(getSurvivalGames().getLobby().getMapName(), false);
        try {
            FileUtils.deleteDirectory(lobby);
            System.out.println("Delete Map: Lobby");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to delete: Lobby");
        }

        Main.plugin = null;
    }

    private void loadDatabase() {
        File file = new File(getDataFolder(), "mysql.yml");
        FileConfiguration mysql = YamlConfiguration.loadConfiguration(file);
        mysql.addDefault("MySQL_Host", "localhost");
        mysql.addDefault("MySQL_Database", "database");
        mysql.addDefault("MySQL_User", "user");
        mysql.addDefault("MySQL_Password", "password");
        mysql.options().copyDefaults(true);
        try {
            mysql.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DBI dbi = new DBI("jdbc:db://" + mysql.getString("MySQL_Host") + "/" + mysql.getString("MySQL_Database")
            + "?user=" + mysql.getString("MySQL_User") + "&password=" + mysql.getString("MySQL_Password")
            + "&autoReconnect=true");
        SQL = dbi.open();

        UserDAO userDAO = getSQL().attach(UserDAO.class);
        userDAO.createTable();

        LocationDAO locationDAO = getSQL().attach(LocationDAO.class);
        locationDAO.createTable();

    }

    private void loadConfigurationFile() {
        File file = new File(getDataFolder(), "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.addDefault("editmode", true);
        cfg.addDefault("debugmode", true);
        cfg.addDefault("standartMapFolder", "/home/Maps");
        cfg.options().copyDefaults(true);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
