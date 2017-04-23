package de.debitorlp.server.survivalgames;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import de.debitorlp.server.survivalgames.database.dao.LocationDAO;
import de.debitorlp.server.survivalgames.database.dao.MapDAO;
import de.debitorlp.server.survivalgames.database.tables.Map;
import de.debitorlp.server.survivalgames.editmode.EditMode;
import de.debitorlp.server.survivalgames.enm.GameStatus;
import de.debitorlp.server.survivalgames.enm.Type;
import de.debitorlp.server.survivalgames.map.GameMap;
import de.debitorlp.server.survivalgames.timer.GameTimer;
import de.debitorlp.server.survivalgames.timer.LobbyTimer;
import de.debitorlp.server.survivalgames.util.UnZip;

public class SurvivalGames {

    private GameStatus gameStatus;
    private boolean onPoss;

    private LobbyTimer lobbyTimerClass = new LobbyTimer(60);
    private BukkitTask lobbytimer;
    private BukkitTask onPossTimer;
    private BukkitTask warmUpTimer;
    private GameTimer gameTimerClass = new GameTimer(1200);
    private BukkitTask gameTimer;
    private BukkitTask deathMatchTimer;
    private BukkitTask restartTimer;
    private int currentTime;

    private GameMap lobby;
    private GameMap arena;
    private GameMap deathMatchArena;

    private ArrayList<Player> alive = new ArrayList<Player>();
    private HashMap<Player, Integer> kills = new HashMap<Player, Integer>();

    public SurvivalGames(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        this.onPoss = false;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public boolean isOnPoss() {
        return onPoss;
    }

    public void setOnPoss(boolean onPoss) {
        this.onPoss = onPoss;
    }

    public LobbyTimer getLobbyTimerClass() {
        return lobbyTimerClass;
    }

    public void setLobbyTimerClass(LobbyTimer lobbyTimerClass) {
        this.lobbyTimerClass = lobbyTimerClass;
    }

    public BukkitTask getLobbytimer() {
        return lobbytimer;
    }

    public void setLobbytimer(BukkitTask lobbytimer) {
        this.lobbytimer = lobbytimer;
    }

    public BukkitTask getOnPossTimer() {
        return onPossTimer;
    }

    public void setOnPossTimer(BukkitTask onPossTimer) {
        this.onPossTimer = onPossTimer;
    }

    public BukkitTask getWarmUpTimer() {
        return warmUpTimer;
    }

    public void setWarmUpTimer(BukkitTask warmUpTimer) {
        this.warmUpTimer = warmUpTimer;
    }

    public GameTimer getGameTimerClass() {
        return gameTimerClass;
    }

    public void setGameTimerClass(GameTimer gameTimerClass) {
        this.gameTimerClass = gameTimerClass;
    }

    public BukkitTask getGameTimer() {
        return gameTimer;
    }

    public void setGameTimer(BukkitTask gameTimer) {
        this.gameTimer = gameTimer;
    }

    public BukkitTask getDeathMatchTimer() {
        return deathMatchTimer;
    }

    public void setDeathMatchTimer(BukkitTask deathMatchTimer) {
        this.deathMatchTimer = deathMatchTimer;
    }

    public BukkitTask getRestartTimer() {
        return restartTimer;
    }

    public void setRestartTimer(BukkitTask restartTimer) {
        this.restartTimer = restartTimer;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public GameMap getLobby() {
        return lobby;
    }

    public void setLobby(GameMap lobby) {
        this.lobby = lobby;
    }

    public GameMap getArena() {
        return arena;
    }

    public void setArena(GameMap arena) {
        this.arena = arena;
    }

    public GameMap getDeathMatchArena() {
        return deathMatchArena;
    }

    public void setDeathMatchArena(GameMap deathMatchArena) {
        this.deathMatchArena = deathMatchArena;
    }

    public ArrayList<Player> getAlive() {
        return alive;
    }

    public void setAlive(ArrayList<Player> alive) {
        this.alive = alive;
    }

    public void addAlive(Player player) {
        this.alive.add(player);
    }

    public void removeAlive(Player player) {
        this.alive.remove(player);
    }

    public boolean isAlive(Player player) {
        return this.alive.contains(player);
    }

    public HashMap<Player, Integer> getKills() {
        return kills;
    }

    public void setKills(HashMap<Player, Integer> kills) {
        this.kills = kills;
    }

    public void addKills(Player player, int amount) {
        this.kills.put(player, amount);
    }

    public void loadMap(String mapName, Type type) {
        File file = new File(Main.getPlugin().getDataFolder(), "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        File mapfile = EditMode.copyMapToServer(mapName, Paths.get(cfg.getString("standartMapFolder")));

        UnZip unZip = new UnZip(mapfile.getPath(), "./" + mapName);
        unZip.unZipIt();
        mapfile.delete();

        new WorldCreator(mapName).createWorld();

        MapDAO mapDAO = Main.getSQL().attach(MapDAO.class);
        Map map = mapDAO.getMap(type.name(), mapName);

        LocationDAO locationDAO = Main.getSQL().attach(LocationDAO.class);
        if (type == Type.LOBBY) {
            setLobby(new GameMap(locationDAO.getLocations(map.getType().name(), map.getMapName()), map.getAuthor(),
                map.getType(), map.getMapName()));
        } else if (type == Type.ARENA) {
            setArena(new GameMap(locationDAO.getLocations(map.getType().name(), map.getMapName()), map.getAuthor(),
                map.getType(), map.getMapName()));
        } else if (type == Type.DEATHMATCH) {
            setDeathMatchArena(
                new GameMap(locationDAO.getLocations(map.getType().name(), map.getMapName()), map.getAuthor(),
                    map.getType(), map.getMapName()));
        }
        System.out.println(type.name() + " set: " + map.getType().name() + "|" + map.getMapName());
    }

    public void loadRandomArena() {
        ArrayList<String> arena = new ArrayList<String>();
        List<Map> maps = Main.getSQL().attach(MapDAO.class).getMaps(Type.ARENA.name());
        for (Map map : maps) {
            arena.add(map.getMapName());
        }
        String mapName = arena.get(new Random().nextInt(arena.size()));
        loadMap(mapName, Type.ARENA);
    }

    public void loadRandomDeathMatchArena() {
        ArrayList<String> arena = new ArrayList<String>();
        List<Map> maps = Main.getSQL().attach(MapDAO.class).getMaps(Type.DEATHMATCH.name());
        for (Map map : maps) {
            arena.add(map.getMapName());
        }
        String mapName = arena.get(new Random().nextInt(arena.size()));
        loadMap(mapName, Type.DEATHMATCH);
    }

    public void updateScoreboard(Player player, int kills) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("SurvivalGames", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§3§l--SurvivalGames--");

        Score phasescore = objective.getScore("§bPhase:");
        phasescore.setScore(11);
        Score phasevalue = objective.getScore("§e" + getGameStatus().name());
        phasevalue.setScore(10);
        Score space1 = objective.getScore("§1");
        space1.setScore(9);

        Score timescore = objective.getScore("§bZeit:");
        timescore.setScore(8);
        Score timevalue = objective.getScore("§e" + getCurrentTime());
        timevalue.setScore(7);
        Score space2 = objective.getScore("§2");
        space2.setScore(6);

        Score playersalivescore = objective.getScore("§bSpieler:");
        playersalivescore.setScore(5);
        Score playersalivevalue = objective.getScore("§e" + getAlive().size() + "/" + Bukkit.getMaxPlayers());
        playersalivevalue.setScore(4);
        Score space3 = objective.getScore("§3");
        space3.setScore(3);

        Score killsscore = objective.getScore("§cKills:");
        killsscore.setScore(2);
        Score killsscorevalue = objective.getScore("§e" + kills);
        killsscorevalue.setScore(1);

        player.setScoreboard(board);
    }

}
