package de.debitorlp.server.survivalgames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.debitorlp.server.survivalgames.Main;

public class SurvivalGamesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("SurvivalGames.Help")) {
            sender.sendMessage(Main.prefix
                + "§3Hilfe zur Einrichtung und Benutzung dieses Plugins findest du in der beigelegten README.pdf");
        }

        return false;
    }

}
