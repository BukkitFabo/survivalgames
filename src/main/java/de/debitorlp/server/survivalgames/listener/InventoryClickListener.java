package de.debitorlp.server.survivalgames.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (Main.editMode) {
            return;
        }

        if (Main.getSurvivalGames().getGameStatus() == GameStatus.WAITING
            || Main.getSurvivalGames().getGameStatus() == GameStatus.ENDING) {
            event.setCancelled(true);
        }

        if (event.getInventory() != null && event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()
            && event.getCurrentItem().getItemMeta().hasDisplayName()) {
            if (event.getInventory().getName().equals("§e§lTeleporter")) {
                event.setCancelled(true);
                Player target = Bukkit
                    .getPlayer(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                player.teleport(target);
                player.closeInventory();
            }
            if (event.getInventory().getName().equals("§e§lStatistiken")) {
                event.setCancelled(true);
            }
        }

    }

}
