package de.debitorlp.server.survivalgames.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {

    static ItemStack item;
    static ItemMeta itemmeta;

    public ItemManager(ItemStack item) {
        ItemManager.item = item;
        ItemManager.itemmeta = item.getItemMeta();
    }

    public Builder modify() {
        return new Builder();
    }

    public static final class Builder {

        public Builder setLore(String... lore) {
            List<String> loreList = new ArrayList<String>();
            for (String lores : lore) {
                loreList.add(lores);
            }
            itemmeta.setLore(loreList);
            return this;
        }

        public Builder addEnchantment(Enchantment enchantment, int level) {
            itemmeta.addEnchant(enchantment, level, false);
            return this;
        }

        public Builder removeEnchantment(Enchantment enchantment) {
            itemmeta.removeEnchant(enchantment);
            return this;
        }

        public Builder setDisplayName(String displayname) {
            itemmeta.setDisplayName(displayname);
            return this;
        }

        public Builder setUnbreakable(boolean unbreakable) {
            itemmeta.spigot().setUnbreakable(unbreakable);
            return this;
        }

        public Builder hideFlag(ItemFlag itemflag) {
            itemmeta.addItemFlags(itemflag);
            return this;
        }

        public Builder showFlag(ItemFlag itemflag) {
            itemmeta.removeItemFlags(itemflag);
            return this;
        }

        public Builder hideFlagsExcept(ItemFlag itemflag) {
            hideFlags();
            showFlag(itemflag);
            return this;
        }

        public Builder showFlagsExcept(ItemFlag itemflag) {
            showFlags();
            hideFlag(itemflag);
            return this;
        }

        public Builder hideFlags() {
            itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemmeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemmeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            itemmeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            itemmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            return this;
        }

        public Builder showFlags() {
            itemmeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemmeta.removeItemFlags(ItemFlag.HIDE_DESTROYS);
            itemmeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemmeta.removeItemFlags(ItemFlag.HIDE_PLACED_ON);
            itemmeta.removeItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            itemmeta.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            return this;
        }

        public ItemStack build() {
            item.setItemMeta(itemmeta);
            return item;
        }

    }

}
