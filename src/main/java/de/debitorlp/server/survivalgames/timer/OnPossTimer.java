package de.debitorlp.server.survivalgames.timer;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.debitorlp.server.survivalgames.Main;

public class OnPossTimer extends BukkitRunnable {

    int time;
    int maxtime;

    public OnPossTimer(int time) {
        this.time = time;
        this.maxtime = time;
        Main.getSurvivalGames().setOnPoss(true);
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
            Main.getSurvivalGames().setOnPoss(false);
            Main.getSurvivalGames().setWarmUpTimer(new WarmUpTimer(30).runTaskTimer(Main.getPlugin(), 0L, 20L));
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.sendTitle("§4Go!", "");
                all.playSound(all.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 100, 0);
            }
        }

        if (time == 10 || time == 5 || time == 3 || time == 2 || time == 1) {
            Bukkit.broadcastMessage(
                Main.prefix + "§3Die Runde startet in §6" + time + " §3" + (time == 1 ? "Sekunde." : "Sekunden."));
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.playSound(all.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
                if (time <= 3) {
                    all.sendTitle("§4" + time, "");
                } else {
                    all.sendTitle("", "§4" + time);
                }
            }
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel(time);
            all.setExp((float) time / maxtime);
        }

    }

}
