package de.debitorlp.server.survivalgames.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.database.dao.MapDAO;
import de.debitorlp.server.survivalgames.database.tables.Map;
import de.debitorlp.server.survivalgames.enm.Type;

public class ForceMapCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("SurvivalGames.ForceMap")) {
            if (args.length == 1) {
                ArrayList<String> arena = new ArrayList<String>();
                List<Map> maps = Main.getSQL().attach(MapDAO.class).getMaps(Type.DEATHMATCH.name());
                for (Map map : maps) {
                    arena.add(map.getMapName());
                }

                if (arena.contains(args[0])) {
                    Main.getSurvivalGames().loadMap(args[0], Type.ARENA);
                    sender.sendMessage(Main.prefix + "§3Es wird nun auf der Map §6" + args[0] + " §3gespielt.");
                    return true;
                } else {
                    sender.sendMessage(Main.prefix + "§cDiese Map existiert nicht. Hier siehst du alle Maps:");
                    for (String arenaName : arena) {
                        sender.sendMessage("§6" + arenaName);
                    }
                    return true;
                }

            } else {
                sender.sendMessage("§cSyntax: /forcemap <MapName>");
            }
        }

        return false;
    }

}
