package de.debitorlp.server.survivalgames.chest;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class ChestInteractListener implements Listener {

    @EventHandler
    public void onChestInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.SPECTATOR) {
            event.setCancelled(true);
        }

        if (Main.getSurvivalGames().getGameStatus() == GameStatus.WARMUP
            || Main.getSurvivalGames().getGameStatus() == GameStatus.INGAME
            || Main.getSurvivalGames().getGameStatus() == GameStatus.DEATHMATCH) {

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getClickedBlock() != null && event.getClickedBlock().getState().getType() == Material.CHEST) {
                    if (Main.getSurvivalGames().isAlive(player)) {
                        Chest chest = (Chest) event.getClickedBlock().getState();

                        if (!ChestManager.chest.contains(chest.getLocation())) {
                            // NEW CHEST INVENTORY
                            Inventory contents = getNewChest();
                            chest.getInventory().setContents(contents.getContents());
                            ChestManager.chest.add(chest.getLocation());
                        }
                    } else {
                        event.setCancelled(true);
                    }
                }

            }
        }
    }

    public Inventory getNewChest() {
        Inventory inv = Bukkit.createInventory(null, 27, "§bChest");

        Random rn = new Random();
        int size = 6;
        for (int i = 1; i <= size; i++) {
            int slot = rn.nextInt(27);
            ItemStack item = ChestManager.items.get(rn.nextInt(ChestManager.items.size()));
            inv.setItem(slot, item);
        }

        return inv;
    }

}
