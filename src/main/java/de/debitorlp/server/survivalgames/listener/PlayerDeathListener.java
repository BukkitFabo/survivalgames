package de.debitorlp.server.survivalgames.listener;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;
import de.debitorlp.server.survivalgames.timer.RestartTimer;
import de.debitorlp.server.survivalgames.util.imagemessage.ImageChar;
import de.debitorlp.server.survivalgames.util.imagemessage.ImageMessage;
import net.minecraft.server.v1_9_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_9_R1.PacketPlayInClientCommand.EnumClientCommand;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(null);

        player.getWorld().strikeLightningEffect(player.getLocation());

        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try {
                    PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
                    ((CraftPlayer) player).getHandle().playerConnection.a(packet);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }, 10L);

        // TODO STATS
        if (player.getKiller() instanceof Player) {
            Player killer = player.getKiller();

            Main.getSurvivalGames().addKills(killer, Main.getSurvivalGames().getKills().get(player) + 1);
            Bukkit.broadcastMessage(Main.prefix + "§3Der Spieler §r" + player.getDisplayName() + " §3wurde von §r"
                + killer.getDisplayName() + " §3getötet.");

        } else {
            Bukkit.broadcastMessage(Main.prefix + "§3Der Spieler §r" + player.getDisplayName() + " §3ist gestorben.");
        }

        Main.getSurvivalGames().removeAlive(player);
        Bukkit.broadcastMessage(
            Main.prefix + "§3Es " + (Main.getSurvivalGames().getAlive().size() == 1 ? "lebt" : "leben") + " noch §6"
                + Main.getSurvivalGames().getAlive().size() + " §3Spieler.");

        for (Player all : Bukkit.getOnlinePlayers()) {
            Main.getSurvivalGames().updateScoreboard(all, Main.getSurvivalGames().getKills().get(player));
        }

        if (Main.getSurvivalGames().getAlive().size() == 1) {
            Main.getSurvivalGames().setRestartTimer(new RestartTimer(15).runTaskTimer(Main.getPlugin(), 0L, 20L));
            Main.getSurvivalGames().setGameStatus(GameStatus.ENDING);

            Main.getSurvivalGames().getGameTimer().cancel();
            if (Main.getSurvivalGames().getDeathMatchTimer() != null) {
                Main.getSurvivalGames().getDeathMatchTimer().cancel();
            }

            for (Player alive : Main.getSurvivalGames().getAlive()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BufferedImage image = null;
                        try {
                            URL url = new URL("http://cravatar.eu/helmavatar/"
                                + ChatColor.stripColor(alive.getDisplayName()) + "/64.png");
                            image = ImageIO.read(url);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            new ImageMessage(image, 8, ImageChar.MEDIUM_SHADE.getChar()).appendCenteredText(
                                " ", " ", "§3Der Spieler", alive.getDisplayName(), "§3hat die SurvivalGames",
                                "§3gewonnen.").sendToPlayer(all);
                        }

                    }
                }).start();

            }
        }

        if (Main.getSurvivalGames().getAlive().size() <= 4
            && Main.getSurvivalGames().getGameStatus() != GameStatus.DEATHMATCH
            && !(Main.getSurvivalGames().getGameTimerClass().getTime() <= 31)) {
            Main.getSurvivalGames().getGameTimerClass().setTime(31);
        }

    }

}
