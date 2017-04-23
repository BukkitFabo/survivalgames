package de.debitorlp.server.survivalgames.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.enm.GameStatus;

public class ForceStartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("SurvivalGames.ForceStart")) {
            if (Main.getSurvivalGames().getGameStatus() == GameStatus.WAITING) {
                if (Bukkit.getOnlinePlayers().size() < 2) {
                    sender.sendMessage(
                        Main.prefix + "§cUm eine Runde zu starten müssen mindestends 2 Spieler auf dem Server sein.");
                    return true;
                }
                Main.getSurvivalGames().getLobbyTimerClass().forcestart();
                sender.sendMessage(Main.prefix + "§3Du hast das Spiel gestartet.");
            } else {
                sender.sendMessage(Main.prefix + "§cDas Spiel hat schon begonnen.");
            }
        }

        return false;
    }

}
