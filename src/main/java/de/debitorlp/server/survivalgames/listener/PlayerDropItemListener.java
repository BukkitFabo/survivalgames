package de.debitorlp.server.survivalgames.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (Main.editMode) {
            return;
        }

        if (Main.getSurvivalGames().getGameStatus() == GameStatus.INGAME
            || Main.getSurvivalGames().getGameStatus() == GameStatus.DEATHMATCH
            || Main.getSurvivalGames().getGameStatus() == GameStatus.WARMUP) {
            if (!Main.getSurvivalGames().isAlive(player)) {
                event.setCancelled(true);
            }
        } else {
            event.setCancelled(true);
        }

    }

}
