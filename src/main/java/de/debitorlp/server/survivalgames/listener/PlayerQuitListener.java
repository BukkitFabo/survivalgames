package de.debitorlp.server.survivalgames.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (Main.getSurvivalGames().getGameStatus() == GameStatus.WAITING) {
            event.setQuitMessage(player.getDisplayName() + " §7hat die Runde verlassen.");

            if (Bukkit.getOnlinePlayers().size() - 1 < 2 && Main.getSurvivalGames().getLobbytimer() != null) {
                Bukkit.getScheduler().cancelTask(Main.getSurvivalGames().getLobbytimer().getTaskId());
                Main.getSurvivalGames().setLobbyTimerClass(null);
                Main.getSurvivalGames().setLobbytimer(null);
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.setLevel(0);
                    all.setExp(0);
                }
            }

            return;
        }

        if (Main.getSurvivalGames().isAlive(player)) {
            if (Main.getSurvivalGames().getGameStatus() == GameStatus.WARMUP) {
                event.setQuitMessage(player.getDisplayName() + " §7hat die Runde verlassen.");
                player.setHealth(0.0);
                return;
            }

            if (Main.getSurvivalGames().getGameStatus() == GameStatus.INGAME) {
                event.setQuitMessage(player.getDisplayName() + " §7hat die Runde verlassen.");
                player.setHealth(0.0);
                return;
            }

            if (Main.getSurvivalGames().getGameStatus() == GameStatus.DEATHMATCH) {
                event.setQuitMessage(player.getDisplayName() + " §7hat die Runde verlassen.");
                player.setHealth(0.0);
                return;
            }
        } else {
            event.setQuitMessage(null);
        }

        if (Main.getSurvivalGames().getGameStatus() == GameStatus.ENDING) {
            event.setQuitMessage(null);
            return;
        }

    }

}
