package de.debitorlp.server.survivalgames.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
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
                if (event.getBlock().getType() == Material.LEAVES || event.getBlock().getType() == Material.LEAVES_2
                    || event.getBlock().getType() == Material.LONG_GRASS
                    || event.getBlock().getType() == Material.DOUBLE_PLANT
                    || event.getBlock().getType() == Material.CAKE_BLOCK
                    || event.getBlock().getType() == Material.WEB) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                }
            }
        } else {
            event.setCancelled(true);
        }

    }

}
