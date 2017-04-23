package de.debitorlp.server.survivalgames.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import de.debitorlp.server.survivalgames.Main;

public class PlayerInteractAtEntityListener implements Listener {

    @EventHandler
    public void inPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {

        if (Main.editMode) {
            return;
        }

        event.setCancelled(true);
    }

}
