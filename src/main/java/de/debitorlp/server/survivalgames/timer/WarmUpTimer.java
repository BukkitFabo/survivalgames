package de.debitorlp.server.survivalgames.timer;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class WarmUpTimer extends BukkitRunnable {

    private int time;

    public WarmUpTimer(int time) {
        this.time = time;
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
            Main.getSurvivalGames().setGameStatus(GameStatus.INGAME);
            GameTimer gameTimer = new GameTimer(1200);
            Main.getSurvivalGames().setGameTimerClass(gameTimer);
            Main.getSurvivalGames().setGameTimer(gameTimer.runTaskTimer(Main.getPlugin(), 0L, 20L));
            Bukkit.broadcastMessage(Main.prefix + "§3Die Friedensphase ist beendet! Du bist nun verwundbar.");
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.playSound(all.getLocation(), Sound.BLOCK_ANVIL_LAND, 100, 0);
            }
        }

        if (time == 10 || time == 5 || time == 3 || time == 2 || time == 1) {
            Bukkit.broadcastMessage(Main.prefix + "§3Die Friedensphase endet in §6" + time + " §3"
                + (time == 1 ? "Sekunde." : "Sekunden."));
        }

    }

}
