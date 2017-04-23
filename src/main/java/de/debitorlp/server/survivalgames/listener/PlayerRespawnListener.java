package de.debitorlp.server.survivalgames.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import de.debitorlp.server.survivalgames.Main;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        player.setGameMode(GameMode.SPECTATOR);
        event.setRespawnLocation(Main.getSurvivalGames().getAlive().get(0).getLocation());

    }

}
