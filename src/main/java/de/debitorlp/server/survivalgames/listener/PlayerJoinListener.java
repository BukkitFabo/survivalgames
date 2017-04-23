package de.debitorlp.server.survivalgames.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;
import de.debitorlp.server.survivalgames.timer.LobbyTimer;
import de.debitorlp.server.survivalgames.util.ItemManager;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Main.getSurvivalGames().addKills(player, 0);

        if (Main.getSurvivalGames().getGameStatus() == GameStatus.WAITING) {
            event.setJoinMessage(player.getDisplayName() + " §7hat die Runde betreten.");
            giveInventory(player, false);
            player.setGameMode(GameMode.SURVIVAL);

            if (!Main.editMode) {
                Location location = Main.getSurvivalGames().getLobby().getLocations().get(1);
                location.getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
                player.teleport(location);

                if (Bukkit.getOnlinePlayers().size() >= 2) {
                    if (Main.getSurvivalGames().getLobbytimer() == null) {
                        LobbyTimer lobbytimer = new LobbyTimer(60);
                        Main.getSurvivalGames().setLobbyTimerClass(lobbytimer);
                        Main.getSurvivalGames().setLobbytimer(
                            Main.getSurvivalGames().getLobbyTimerClass().runTaskTimer(Main.getPlugin(), 0L, 20L));
                    }
                }
            }
        } else {
            // TODO spectator
            event.setJoinMessage(null);
            giveInventory(player, true);
            player.setGameMode(GameMode.SPECTATOR);
        }

    }

    private void giveInventory(Player player, boolean spectator) {
        ItemStack lobby = new ItemManager(new ItemStack(Material.GLOWSTONE_DUST)).modify()
            .setDisplayName("§7Zurück zur §eLobby")
            .setLore("§7Klicken um zurück", "§7zur Lobby zu gelangen.").hideFlags().build();
        ItemStack statistiks = new ItemManager(new ItemStack(Material.BOOK)).modify().setDisplayName("§eStatistiken")
            .setLore("§7Klicke um deine", "§7Statistiken zu sehen.").hideFlags().build();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setLevel(0);
        player.setExp(0);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.removePotionEffect(PotionEffectType.ABSORPTION);
        player.removePotionEffect(PotionEffectType.REGENERATION);
        player.removePotionEffect(PotionEffectType.SPEED);
        // player.removePotionEffect(PotionEffectType.GLOWING);

        if (!spectator) {
            player.getInventory().setItem(0, statistiks);
        } else {
            player.getInventory().setItem(7, statistiks);
        }
        player.getInventory().setItem(8, lobby);

    }

}
