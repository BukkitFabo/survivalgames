package de.debitorlp.server.survivalgames.timer;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.chest.ChestManager;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class GameTimer extends BukkitRunnable {

    private int time;
    private int maxtime;

    public GameTimer(int sec) {
        this.time = sec;
        this.maxtime = sec;
    }

    @Override
    public void run() {
        time--;

        Main.getSurvivalGames().setCurrentTime(time);
        for (Player all : Bukkit.getOnlinePlayers()) {
            Main.getSurvivalGames().updateScoreboard(all, Main.getSurvivalGames().getKills().get(all));
        }

        if (time == 0) {
            cancel();

            Main.getSurvivalGames()
                .setDeathMatchTimer(new DeathMatchTimer(300).runTaskTimer(Main.getPlugin(), 0L, 20L));
            Main.getSurvivalGames().setGameStatus(GameStatus.DEATHMATCH);

            int number = 1;
            for (Player alive : Main.getSurvivalGames().getAlive()) {
                Location location = Main.getSurvivalGames().getDeathMatchArena().getLocations().get(number);
                alive.teleport(location);
                number++;
            }

            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.getGameMode() == GameMode.SPECTATOR) {
                    all.teleport(Main.getSurvivalGames().getAlive().get(0));
                }
            }
        }

        if (time == maxtime / 2) {
            ChestManager.chest.clear();
            Bukkit.broadcastMessage(Main.prefix + "§3Alle Kisten wurden wieder aufgefüllt!");
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.playSound(all.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 0);
            }
        }

        if (time == 30 || time == 10 || time == 5 || time == 3 || time == 2 || time == 1) {
            Bukkit.broadcastMessage(
                Main.prefix + "§3Das DeathMatch startet in §6" + time + " §3" + (time == 1 ? "Sekunde." : "Sekunden."));
        }

    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return this.time;
    }

}
