package de.debitorlp.server.survivalgames.editmode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.debitorlp.server.survivalgames.Main;
import de.debitorlp.server.survivalgames.database.dao.MapDAO;
import de.debitorlp.server.survivalgames.database.tables.Location;
import de.debitorlp.server.survivalgames.database.tables.Map;
import de.debitorlp.server.survivalgames.enm.Type;

public class EditCommand implements CommandExecutor {

    private HashMap<Player, EditMap> editplayer = new HashMap<Player, EditMap>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Du musst ein Spieler sein.");
            return true;
        }
        Player player = (Player) sender;

        if (player.hasPermission("SurvivalGames.Edit")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("save")) {

                    if (!editplayer.containsKey(player)) {
                        player
                            .sendMessage(
                                Main.prefix + "§cDu musst erst eine Map laden, bevor du diese editieren kannst.");
                        player.sendMessage(Main.prefix + "§cDies kannst du mit /edit map <TYPE> <MapName>");
                    }

                    EditMap editMap = editplayer.get(player);
                    player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                    EditMode.saveMap(editMap.getMap().getType(), editMap.getMap().getMapName(),
                        editMap.getMap().getAuthor(), editMap);

                    player.sendMessage(
                        Main.prefix + "§3Die §6" + editMap.getMap().getType().name() + " §3mit dem Namen §6"
                            + editMap.getMap().getMapName() + " §3wurde gespeichert.");
                    player.setGameMode(GameMode.SURVIVAL);

                    editplayer.remove(player);

                }

            } else if (args.length == 3) {

                if (args[0].equalsIgnoreCase("create")) {

                    if (editplayer.containsKey(player)) {
                        player.sendMessage(Main.prefix
                            + "§cSpeichere erst deine aktuelle Map mit §6/edit save §3um eine neue Map zu laden.");
                        return true;
                    }

                    if (args[1].equalsIgnoreCase("LOBBY") || args[1].equalsIgnoreCase("ARENA")
                        || args[1].equalsIgnoreCase("DEATHMATCH")) {
                        Type type = Type.valueOf(args[1]);

                        ArrayList<String> arena = new ArrayList<String>();
                        List<Map> maps = Main.getSQL().attach(MapDAO.class).getMaps(type.name());
                        for (Map map : maps) {
                            arena.add(map.getMapName());
                        }

                        if (!maps.contains(args[2])) {
                            EditMap editMap = EditMode.createMap(type, args[2], "Unknown", player);
                            editplayer.put(player, editMap);
                            player.sendMessage(Main.prefix + "§3Du hast die §6" + type.name() + " §3mit dem Namen §6"
                                + args[2] + " §3erstellt.");

                        } else {
                            player.sendMessage(Main.prefix + "§cDie §6" + type.name() + " mit dem Namen §6" + args[2]
                                + " §cgibt es schon.");
                        }

                    } else {
                        player.sendMessage(Main.prefix + "§cDiesen Type gibt es nicht. (LOBBY, ARENA, DEATHMATCH)");
                    }
                }

                if (args[0].equalsIgnoreCase("load")) {

                    if (editplayer.containsKey(player)) {
                        player.sendMessage(Main.prefix
                            + "§cSpeichere erst deine aktuelle Map mit §6/edit save §3um eine neue Map zu laden.");
                        return true;
                    }

                    if (args[1].equalsIgnoreCase("LOBBY") || args[1].equalsIgnoreCase("ARENA")
                        || args[1].equalsIgnoreCase("DEATHMATCH")) {
                        Type type = Type.valueOf(args[1]);

                        ArrayList<String> arena = new ArrayList<String>();
                        List<Map> maps = Main.getSQL().attach(MapDAO.class).getMaps(type.name());
                        for (Map map : maps) {
                            arena.add(map.getMapName());
                        }

                        if (arena.contains(args[2])) {
                            // create map and tp to it
                            editplayer.put(player, EditMode.loadMap(player, args[2], type));
                            EditMode.updateMapLocation(player, editplayer.get(player));

                        } else {
                            player.sendMessage(Main.prefix + "§cDiese Map existiert nicht. Hier siehst du alle Maps:");
                            for (String arenaName : arena) {
                                sender.sendMessage("§6" + arenaName);
                            }
                        }

                        player.setGameMode(GameMode.CREATIVE);
                    } else {
                        player.sendMessage(Main.prefix + "§cDiesen Type gibt es nicht. (LOBBY, ARENA, DEATHMATCH)");
                    }
                }

                if (args[0].equalsIgnoreCase("set")) {

                    if (!editplayer.containsKey(player)) {
                        player
                            .sendMessage(
                                Main.prefix + "§cDu musst erst eine Map laden, bevor du diese editieren kannst.");
                        player.sendMessage(Main.prefix + "§cDies kannst du mit /edit map <TYPE> <MapName>");
                        return true;
                    }

                    if (args[1].equalsIgnoreCase("point")) {
                        if (isInt(args[2])) {
                            int number = Integer.valueOf(args[2]);
                            EditMap editMap = editplayer.get(player);
                            editMap.addLocation(number,
                                new Location(
                                    editMap.getMap().getType(),
                                    editMap.getMap().getMapName(),
                                    number,
                                    player.getWorld(),
                                    player.getLocation().getX(),
                                    player.getLocation().getY(),
                                    player.getLocation().getZ(),
                                    player.getLocation().getYaw(),
                                    player.getLocation().getPitch()));
                            player.sendMessage(
                                Main.prefix + "§3Du hast den Punkt mit der Nummer §6" + number + " §3gesetzt.");
                            EditMode.updateMapLocation(player, editMap);
                        } else {
                            player.sendMessage(Main.prefix + "§cDer Punkt muss eine Nummer haben.");
                        }
                    }
                }

                if (args[0].equalsIgnoreCase("remove")) {

                    if (!editplayer.containsKey(player)) {
                        player.sendMessage(
                            Main.prefix + "§cDu musst erst eine Map laden, bevor du diese editieren kannst.");
                        player.sendMessage(Main.prefix + "§cDies kannst du mit /edit map <TYPE> <MapName>");
                    }

                    if (args[1].equalsIgnoreCase("point")) {
                        if (isInt(args[2])) {
                            EditMap editMap = editplayer.get(player);

                            if (editMap.getLocation().containsKey(Integer.valueOf(args[2]))) {
                                editMap.removeLocation(Integer.valueOf(args[2]));
                                player.sendMessage(
                                    Main.prefix + "§3Du hast den Punkt mit der Nummer §6" + args[2] + " §3entfernt.");
                                EditMode.updateMapLocation(player, editMap);
                            } else {
                                player
                                    .sendMessage(
                                        Main.prefix + "§cDen Punkt mit der Nummer §6" + args[2] + " §cgibt es nicht.");
                            }
                        } else {
                            player.sendMessage(Main.prefix + "§cDer Punkt muss eine Nummer haben.");
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean isInt(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
