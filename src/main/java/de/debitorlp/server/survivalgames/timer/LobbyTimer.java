package de.debitorlp.server.survivalgames.timer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class LobbyTimer extends BukkitRunnable {

    private int time;
    private int maxtime;
    private int playerNumber = 1;

    private boolean running;

    public LobbyTimer(int time) {
        this.time = time;
        this.maxtime = time;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        time--;

        Main.getSurvivalGames().setCurrentTime(time);
        for (Player all : Bukkit.getOnlinePlayers()) {
            Main.getSurvivalGames().updateScoreboard(all, Main.getSurvivalGames().getKills().get(all));
        }

        if (time == 0) {
            cancel();
            Main.getSurvivalGames().setGameStatus(GameStatus.WARMUP);
            Main.getSurvivalGames().setOnPoss(true);
            Main.getSurvivalGames().setOnPossTimer(new OnPossTimer(20).runTaskTimer(Main.getPlugin(), 0L, 20L));

            Main.getSurvivalGames().getArena().getLocations().get(1).getWorld().setTime(0);
            for (Player all : Bukkit.getOnlinePlayers()) {
                Main.getSurvivalGames().addAlive(all);
                all.getInventory().clear();
                Location loc = Main.getSurvivalGames().getArena().getLocations().get(playerNumber);
                all.teleport(loc);
                playerNumber++;
            }
        }

        if (time == 60 || time == 40 || time == 20 || time == 10 || time == 5 || time == 3 || time == 2 || time == 1) {
            Bukkit.broadcastMessage(
                Main.prefix + "§3Das Spiel startet in §6" + time + " §3" + (time == 1 ? "Sekunde." : "Sekunden."));
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
            }
        }

        if (time == 10) {
            Bukkit.broadcastMessage(Main.prefix + "§3Es wird auf der Map §6"
                + Main.getSurvivalGames().getArena().getMapName() + " §3von §6"
                + Main.getSurvivalGames().getArena().getAuthor() + " §3gespielt.");
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.playSound(all.getLocation(), Sound.BLOCK_ANVIL_LAND, 100, 0);
                all.sendTitle("§b§l" + Main.getSurvivalGames().getArena().getMapName(),
                    "§e" + Main.getSurvivalGames().getArena().getAuthor());
            }

        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel(time);
            all.setExp((float) time / maxtime);
        }

    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMaxtime() {
        return maxtime;
    }

    public void setMaxtime(int maxtime) {
        this.maxtime = maxtime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void forcestart() {
        this.time = 6;
    }

}
