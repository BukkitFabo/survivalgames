package de.debitorlp.server.survivalgames.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (Main.getSurvivalGames().getGameStatus() == GameStatus.ENDING
            || Main.getSurvivalGames().getGameStatus() == GameStatus.WAITING
            || Main.getSurvivalGames().getGameStatus() == GameStatus.WARMUP) {
            event.setCancelled(true);
        }
    }

}
