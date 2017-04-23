package de.debitorlp.server.survivalgames.chest;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.debitorlp.server.survivalgames.util.ItemManager;

public class ChestManager {

    public static ArrayList<Location> chest = new ArrayList<Location>();
    public static ArrayList<ItemStack> items = new ArrayList<>();

    @SuppressWarnings("deprecation")
    public static void loadItems() {

        // Waffen
        items.add(new ItemStack(Material.BOW, 1));
        items.add(new ItemStack(Material.BOW, 1));
        items.add(new ItemStack(Material.ARROW, 1));
        items.add(new ItemStack(Material.ARROW, 2));
        items.add(new ItemStack(Material.ARROW, 3));
        items.add(new ItemStack(Material.ARROW, 3));
        items.add(new ItemStack(Material.ARROW, 2));
        items.add(new ItemStack(Material.IRON_SWORD, 1));
        items.add(new ItemStack(Material.WOOD_SWORD, 1));
        items.add(new ItemStack(Material.WOOD_SWORD, 1));
        items.add(new ItemStack(Material.WOOD_SWORD, 1));
        items.add(new ItemStack(Material.STONE_SWORD, 1));
        items.add(new ItemStack(Material.STONE_SWORD, 1));
        items.add(new ItemStack(Material.STONE_PICKAXE, 1));
        items.add(new ItemStack(Material.STONE_PICKAXE, 1));
        items.add(new ItemStack(Material.GOLD_SWORD, 1));
        items.add(new ItemStack(Material.GOLD_SWORD, 1));
        items.add(new ItemStack(Material.GOLD_SWORD, 1));
        items.add(new ItemStack(Material.GOLD_SWORD, 1));
        items.add(new ItemStack(Material.FISHING_ROD, 1));
        items.add(new ItemStack(Material.FISHING_ROD, 1));
        items.add(new ItemStack(Material.IRON_AXE, 1));
        items.add(new ItemStack(Material.IRON_AXE, 1));
        items.add(new ItemStack(Material.STONE_AXE, 1));
        items.add(new ItemStack(Material.STONE_AXE, 1));
        items.add(new ItemStack(Material.DIAMOND_AXE, 1));
        items.add(new ItemStack(Material.GOLD_AXE, 1));
        items.add(new ItemStack(Material.WOOD_AXE, 1));
        items.add(new ItemStack(Material.WOOD_AXE, 1));
        items.add(new ItemStack(Material.WOOD_AXE, 1));
        items.add(new ItemStack(Material.FLINT_AND_STEEL, 1, (short) 11));
        items.add(new ItemStack(Material.IRON_PICKAXE, 1));
        items.add(new ItemStack(Material.DIAMOND_PICKAXE, 1));

        // R�stung
        items.add(new ItemStack(Material.LEATHER_HELMET));
        items.add(new ItemStack(Material.LEATHER_HELMET));
        items.add(new ItemStack(Material.LEATHER_HELMET));
        items.add(new ItemStack(Material.LEATHER_CHESTPLATE));
        items.add(new ItemStack(Material.LEATHER_CHESTPLATE));
        items.add(new ItemStack(Material.LEATHER_CHESTPLATE));
        items.add(new ItemStack(Material.LEATHER_LEGGINGS));
        items.add(new ItemStack(Material.LEATHER_LEGGINGS));
        items.add(new ItemStack(Material.LEATHER_LEGGINGS));
        items.add(new ItemStack(Material.LEATHER_BOOTS));
        items.add(new ItemStack(Material.LEATHER_BOOTS));
        items.add(new ItemStack(Material.LEATHER_BOOTS));
        items.add(new ItemStack(Material.GOLD_HELMET));
        items.add(new ItemStack(Material.GOLD_HELMET));
        items.add(new ItemStack(Material.GOLD_CHESTPLATE));
        items.add(new ItemStack(Material.GOLD_CHESTPLATE));
        items.add(new ItemStack(Material.GOLD_LEGGINGS));
        items.add(new ItemStack(Material.GOLD_LEGGINGS));
        items.add(new ItemStack(Material.GOLD_BOOTS));
        items.add(new ItemStack(Material.GOLD_BOOTS));
        items.add(new ItemStack(Material.CHAINMAIL_HELMET));
        items.add(new ItemStack(Material.CHAINMAIL_HELMET));
        items.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        items.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        items.add(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        items.add(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        items.add(new ItemStack(Material.CHAINMAIL_BOOTS));
        items.add(new ItemStack(Material.CHAINMAIL_BOOTS));
        items.add(new ItemStack(Material.IRON_HELMET));
        items.add(new ItemStack(Material.IRON_CHESTPLATE));
        items.add(new ItemStack(Material.IRON_LEGGINGS));
        items.add(new ItemStack(Material.IRON_BOOTS));
        items.add(new ItemStack(Material.DIAMOND_HELMET));
        items.add(new ItemStack(Material.DIAMOND_BOOTS));
        items.add(new ItemStack(Material.SHIELD));
        items.add(new ItemStack(Material.SHIELD));

        // Essen
        items.add(new ItemStack(Material.BREAD, 1));
        items.add(new ItemStack(Material.BREAD, 2));
        items.add(new ItemStack(Material.BREAD, 1));
        items.add(new ItemStack(Material.BREAD, 2));
        items.add(new ItemStack(Material.BREAD, 1));
        items.add(new ItemStack(Material.BREAD, 2));
        items.add(new ItemStack(Material.APPLE, 1));
        items.add(new ItemStack(Material.APPLE, 2));
        items.add(new ItemStack(Material.GOLDEN_APPLE, 1));
        items.add(new ItemStack(Material.GOLDEN_CARROT, 1));
        items.add(new ItemStack(Material.COOKED_BEEF, 1));
        items.add(new ItemStack(Material.COOKED_BEEF, 1));
        items.add(new ItemStack(Material.COOKED_BEEF, 1));
        items.add(new ItemStack(Material.COOKED_BEEF, 1));
        items.add(new ItemStack(Material.PORK, 1));
        items.add(new ItemStack(Material.PORK, 1));
        items.add(new ItemStack(Material.RAW_BEEF, 1));
        items.add(new ItemStack(Material.RAW_BEEF, 1));
        items.add(new ItemStack(Material.GRILLED_PORK, 1));
        items.add(new ItemStack(Material.GRILLED_PORK, 1));
        items.add(new ItemStack(Material.GRILLED_PORK, 1));
        items.add(new ItemStack(Material.GRILLED_PORK, 1));
        items.add(new ItemStack(Material.RAW_CHICKEN, 1));
        items.add(new ItemStack(Material.RAW_CHICKEN, 1));
        items.add(new ItemStack(Material.COOKED_CHICKEN, 1));
        items.add(new ItemStack(Material.COOKED_CHICKEN, 1));
        items.add(new ItemStack(Material.RAW_FISH, 1));
        items.add(new ItemStack(Material.RAW_FISH, 1));
        items.add(new ItemStack(Material.COOKED_FISH, 1));
        items.add(new ItemStack(Material.COOKED_FISH, 1));
        items.add(new ItemStack(Material.MELON, 4));
        items.add(new ItemStack(Material.MELON, 6));
        items.add(new ItemStack(Material.CARROT_ITEM, 1));
        items.add(new ItemStack(Material.CARROT_ITEM, 3));
        items.add(new ItemStack(Material.PUMPKIN_PIE, 1));
        items.add(new ItemStack(Material.BAKED_POTATO, 1));
        items.add(new ItemStack(Material.POTATO_ITEM, 1));
        items.add(new ItemStack(Material.MELON, 4));
        items.add(new ItemStack(Material.MELON, 6));
        items.add(new ItemStack(Material.CARROT_ITEM, 1));
        items.add(new ItemStack(Material.CARROT_ITEM, 3));
        items.add(new ItemStack(Material.PUMPKIN_PIE, 1));
        items.add(new ItemStack(Material.BAKED_POTATO, 1));
        items.add(new ItemStack(Material.POTATO_ITEM, 1));
        items.add(new ItemStack(Material.MELON, 4));
        items.add(new ItemStack(Material.MELON, 6));
        items.add(new ItemStack(Material.CARROT_ITEM, 1));
        items.add(new ItemStack(Material.CARROT_ITEM, 3));
        items.add(new ItemStack(Material.PUMPKIN_PIE, 1));
        items.add(new ItemStack(Material.BAKED_POTATO, 1));
        items.add(new ItemStack(Material.POTATO_ITEM, 1));
        items.add(new ItemStack(Material.CAKE, 1));
        items.add(new ItemStack(Material.BEETROOT, 1));
        items.add(new ItemStack(Material.BEETROOT, 2));
        items.add(new ItemStack(Material.BEETROOT, 3));
        items.add(new ItemStack(Material.BEETROOT_SOUP, 1));
        items.add(new ItemStack(Material.BEETROOT_SOUP, 1));

        // Tr�nke
        items.add(new ItemStack(373, 1, (short) 16385));
        items.add(new ItemStack(373, 1, (short) 16421));
        items.add(new ItemStack(373, 2, (short) 16421));
        items.add(new ItemStack(373, 1, (short) 16453));
        items.add(new ItemStack(373, 2, (short) 16453));
        items.add(new ItemStack(373, 1, (short) 16422));

        // Sonstiges
        ItemStack lapis = new ItemManager(new ItemStack(Material.INK_SACK, 2, (short) 4)).modify().build();
        items.add(lapis);
        items.add(lapis);
        items.add(lapis);
        items.add(lapis);
        items.add(new ItemStack(Material.DIAMOND, 1));
        items.add(new ItemStack(Material.IRON_INGOT, 1));
        items.add(new ItemStack(Material.STICK, 2));
        items.add(new ItemStack(Material.STICK, 2));
        items.add(new ItemStack(Material.STICK, 1));
        items.add(new ItemStack(Material.WHEAT, 1));
        items.add(new ItemStack(Material.WHEAT, 2));
        items.add(new ItemStack(Material.WHEAT, 4));
        items.add(new ItemStack(Material.EXP_BOTTLE, 3));
        items.add(new ItemStack(Material.EXP_BOTTLE, 2));
        items.add(new ItemStack(Material.EXP_BOTTLE, 1));
        items.add(new ItemStack(Material.TNT, 1));
        items.add(new ItemStack(Material.TNT, 2));
        items.add(new ItemStack(Material.COAL, 1));
        items.add(new ItemStack(Material.COAL, 1));
        items.add(new ItemStack(Material.STRING, 2));
        items.add(new ItemStack(Material.STRING, 3));
        items.add(new ItemStack(Material.WEB, 1));
        items.add(new ItemStack(Material.WEB, 1));
        items.add(new ItemStack(Material.WEB, 2));
        items.add(new ItemStack(Material.WEB, 3));
        // ItemStack compass = ItemManager.newItemStack(Material.COMPASS, 1,
        // (short) 0, "§56Tracker", "§7In der Hand halten um den nächsten
        // Spieler
        // zu orten.");
        // items.add(compass);
        // items.add(compass);

    }

}
