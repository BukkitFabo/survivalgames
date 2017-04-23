package de.debitorlp.server.survivalgames.util;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerListHeaderFooter;

public class TabListAPI {

    public static void sendTablist(Player player, String header, String footer) {
        if (header == null) {
            header = "";
        }

        if (footer == null) {
            footer = "";
        }

        header = ChatColor.translateAlternateColorCodes('&', header);
        footer = ChatColor.translateAlternateColorCodes('&', footer);

        IChatBaseComponent tabheader = ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent tabfooter = ChatSerializer.a("{\"text\": \"" + footer + "\"}");

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabheader);

        try {
            Field field = packet.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(packet, tabfooter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }

    }

}
