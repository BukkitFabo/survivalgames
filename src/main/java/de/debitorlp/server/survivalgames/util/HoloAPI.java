
package de.debitorlp.server.survivalgames.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.PacketPlayOutEntityDestroy;

public class HoloAPI {

    private List<String> lines;
    private Location loc;
    private static final double ABS = 0.23D;
    private static String path;
    private static String version;
    private static List<Integer> ids = new ArrayList<Integer>();

    static {
        path = Bukkit.getServer().getClass().getPackage().getName();
        version = path.substring(path.lastIndexOf(".") + 1, path.length());
    }

    public boolean display(Location loc, List<String> lines, Player player) {
        this.lines = lines;
        this.loc = loc;
        if (loc != null) {
            Location displayLoc = loc.clone().add(0, (ABS * lines.size()) - 1.97D, 0);
            for (int i = 0; i < lines.size(); i++) {
                Object packet = this.getPacket(this.loc.getWorld(), displayLoc.getX(), displayLoc.getY(),
                    displayLoc.getZ(),
                    this.lines.get(i));
                if (packet == null) {
                    return false;
                }
                this.sendPacket(player, packet);
                displayLoc.add(0, -ABS, 0);
            }
        }

        return true;
    }

    public void destroyAll(Player player) {
        int[] id = new int[2000];
        int o1 = 0;
        for (int i : ids) {
            id[o1] = i;
            o1++;
        }
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(id);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public Object getPacket(World world, double xpos, double ypos, double zpos, String text) {
        try {
            Class<?> armorStand = Class.forName("net.minecraft.server." + version + ".EntityArmorStand");
            Class<?> worldClass = Class.forName("net.minecraft.server." + version + ".World");
            Class<?> nmsEntity = Class.forName("net.minecraft.server." + version + ".Entity");
            Class<?> craftWorld = Class.forName("org.bukkit.craftbukkit." + version + ".CraftWorld");
            Class<?> packetClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutSpawnEntityLiving");
            Class<?> entityLivingClass = Class.forName("net.minecraft.server." + version + ".EntityLiving");
            Constructor<?> cww = armorStand.getConstructor(new Class<?>[] {
                worldClass });
            Object craftWorldObj = craftWorld.cast(world);
            Method getHandleMethod = craftWorldObj.getClass().getMethod("getHandle", new Class<?>[0]);
            Object entityObject = cww
                .newInstance(new Object[] {
                    getHandleMethod.invoke(craftWorldObj, new Object[0]) });
            Method setCustomName = entityObject.getClass().getMethod("setCustomName", new Class<?>[] {
                String.class });
            setCustomName.invoke(entityObject, new Object[] {
                text });
            Method setCustomNameVisible = nmsEntity.getMethod("setCustomNameVisible", new Class[] {
                boolean.class });
            setCustomNameVisible.invoke(entityObject, new Object[] {
                true });
            Method setGravity = entityObject.getClass().getMethod("setGravity", new Class<?>[] {
                boolean.class });
            setGravity.invoke(entityObject, new Object[] {
                false });
            Method setLocation = entityObject.getClass().getMethod("setLocation", new Class<?>[] {
                double.class, double.class, double.class, float.class, float.class });
            setLocation.invoke(entityObject, new Object[] {
                xpos, ypos, zpos, 0.0F, 0.0F });
            Method setInvisible = entityObject.getClass().getMethod("setInvisible", new Class<?>[] {
                boolean.class });
            setInvisible.invoke(entityObject, new Object[] {
                true });
            Method getId = entityObject.getClass().getMethod("getId");
            Object id = getId.invoke(entityObject);
            ids.add((Integer) id);
            Constructor<?> cw = packetClass.getConstructor(new Class<?>[] {
                entityLivingClass });
            Object packetObject = cw.newInstance(new Object[] {
                entityObject });
            return packetObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendPacket(Player player, Object packet) {
        String path = Bukkit.getServer().getClass().getPackage().getName();
        String version = path.substring(path.lastIndexOf(".") + 1, path.length());
        try {
            Method getgandle = player.getClass().getMethod("getHandle");
            Object entityplayer = getgandle.invoke(player);
            Object pconnection = entityplayer.getClass().getField("playerConnection").get(entityplayer);
            Class<?> packetClass = Class.forName("net.minecraft.server." + version + ".Packet");
            Method sendMethod = pconnection.getClass().getMethod("sendPacket", new Class[] {
                packetClass });
            sendMethod.invoke(pconnection, new Object[] {
                packet });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
