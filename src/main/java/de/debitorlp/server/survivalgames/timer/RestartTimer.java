package de.debitorlp.server.survivalgames.timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.debitorlp.server.survivalgames.Main;

public class RestartTimer extends BukkitRunnable {

    private int time;

    public RestartTimer(int time) {
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
            Bukkit.shutdown();
        }

        if (time <= 5) {
            Bukkit.broadcastMessage(
                Main.prefix + "§4Der Server restartet in " + time + (time == 1 ? " Sekunde." : " Sekunden."));
        }
    }

}
