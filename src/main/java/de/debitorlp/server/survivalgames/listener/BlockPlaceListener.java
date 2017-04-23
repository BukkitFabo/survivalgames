package de.debitorlp.server.survivalgames.listener;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (Main.editMode) {
            return;
        }

        if (Main.getSurvivalGames().getGameStatus() == GameStatus.INGAME
            || Main.getSurvivalGames().getGameStatus() == GameStatus.DEATHMATCH
            || Main.getSurvivalGames().getGameStatus() == GameStatus.WARMUP) {
            if (!Main.getSurvivalGames().isAlive(player)) {
                event.setCancelled(true);
            } else {
                if (event.getBlock().getType() == Material.WEB || event.getBlock().getType() == Material.CAKE_BLOCK
                    || event.getBlock().getType() == Material.CAKE
                    || event.getBlock().getType() == Material.FIRE
                    || event.getBlock().getType() == Material.FLINT_AND_STEEL) {
                    event.setCancelled(false);
                } else if (event.getBlock().getType() == Material.TNT) {
                    event.getBlock().setType(Material.AIR);
                    TNTPrimed tnt = (TNTPrimed) event.getBlock().getWorld().spawnEntity(event.getBlock().getLocation(),
                        EntityType.PRIMED_TNT);
                    tnt.setFuseTicks(40);
                } else {
                    event.setCancelled(true);
                }
            }
        } else {
            event.setCancelled(true);
        }

    }

}
