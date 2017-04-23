package de.debitorlp.server.survivalgames.listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (Main.editMode) {
            return;
        }

        if (event.getAction() == Action.PHYSICAL) {
            if (event.getClickedBlock().getType() == Material.SOIL) {
                event.setCancelled(true);
            }
        }

        if (Main.getSurvivalGames().getGameStatus() == GameStatus.WAITING || !Main.getSurvivalGames().isAlive(player)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getItem() != null && event.getItem().hasItemMeta()
                    && event.getItem().getItemMeta().hasDisplayName()) {
                    if (event.getItem().getItemMeta().getDisplayName().equals("§7Zurück zur §eLobby")) {
                        ByteArrayOutputStream bout = new ByteArrayOutputStream();
                        DataOutputStream out = new DataOutputStream(bout);
                        try {
                            out.writeUTF("Connect");
                            out.writeUTF("lobby");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        player.sendPluginMessage(Main.getPlugin(), "BungeeCord", bout.toByteArray());
                    }
                    if (event.getItem().getItemMeta().getDisplayName().equals("§eMapauswahl")) {
                        // TODO
                    }
                    if (event.getItem().getItemMeta().getDisplayName().equals("§eStatistiken")) {
                        // TODO
                    }
                }
            }
        }
    }

}
