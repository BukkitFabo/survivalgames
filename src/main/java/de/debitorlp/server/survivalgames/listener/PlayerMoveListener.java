package de.debitorlp.server.survivalgames.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (Main.getSurvivalGames().getGameStatus() == GameStatus.WARMUP && Main.getSurvivalGames().isOnPoss()) {
            if (Main.getSurvivalGames().isAlive(player)) {
                if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY()
                    || event.getFrom().getZ() != event.getTo().getZ()) {
                    event.getPlayer().teleport(
                        new Location(event.getFrom().getWorld(), event.getFrom().getX(), event.getFrom().getY(),
                            event.getFrom().getZ(),
                            event.getPlayer().getLocation().getYaw(), event.getPlayer().getLocation().getPitch()));
                }
            }
        }

    }

}
