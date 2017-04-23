package de.debitorlp.server.survivalgames.timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class DeathMatchTimer extends BukkitRunnable {

    private int time;

    public DeathMatchTimer(int time) {
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

            Main.getSurvivalGames().setRestartTimer(new RestartTimer(15).runTaskTimer(Main.getPlugin(), 0L, 20L));
            Main.getSurvivalGames().setGameStatus(GameStatus.ENDING);

            Main.getSurvivalGames().getGameTimer().cancel();
            Main.getSurvivalGames().getDeathMatchTimer().cancel();

            Bukkit.broadcastMessage(Main.prefix + "§3Das Spiel ist beendet und niemand hat gewonnen.");

        }

    }

}
