package dev.sndy.spectre.gui;

import dev.sndy.spectre.player.NetworkPlayer;
import dev.sndy.spectre.player.NetworkPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ProfilesGui {

    public Inventory getInventory(Player player) {
        Inventory gui = Bukkit.createInventory(player, 27, "Profiles");
        NetworkPlayer nplayer = NetworkPlayerHandler.getPlayer(player.getUniqueId());
        if(nplayer.meta.profiles[0].created) {
            if(nplayer.meta.currentProfile != 0) {
                ItemStack item = new ItemStack(Material.RED_DYE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§cProfile 1");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§fLevel §e" + nplayer.meta.profiles[0].level);
                lore.add("§cRight click to delete.");
                meta.setLore(lore);
                item.setItemMeta(meta);
                gui.setItem(11, item);
            }
            else{
                ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§c(Current) Profile 1");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§fLevel §e" + nplayer.meta.profiles[0].level);
                lore.add("§cSwitch off this profile to delete.");
                meta.setLore(lore);
                item.setItemMeta(meta);
                gui.setItem(11, item);
            }
        }
        else{
            ItemStack noProfile = new ItemStack(Material.POLISHED_BLACKSTONE_BUTTON);
            ItemMeta meta = noProfile.getItemMeta();
            meta.setDisplayName("§eClick to create!");
            noProfile.setItemMeta(meta);
            gui.setItem(11, noProfile);
        }
        if(nplayer.meta.profiles[1].created) {
            if(nplayer.meta.currentProfile != 1) {
                ItemStack item = new ItemStack(Material.ORANGE_DYE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§6Profile 2");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§fLevel §e" + nplayer.meta.profiles[1].level);
                lore.add("§cRight click to delete.");
                meta.setLore(lore);
                item.setItemMeta(meta);
                gui.setItem(13, item);
            }
            else{
                ItemStack item = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§6(Current) Profile 2");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§fLevel §e" + nplayer.meta.profiles[1].level);
                lore.add("§cSwitch off this profile to delete.");
                meta.setLore(lore);
                item.setItemMeta(meta);
                gui.setItem(13, item);
            }
        }
        else{
            ItemStack noProfile = new ItemStack(Material.POLISHED_BLACKSTONE_BUTTON);
            ItemMeta meta = noProfile.getItemMeta();
            meta.setDisplayName("§eClick to create!");
            noProfile.setItemMeta(meta);
            gui.setItem(13, noProfile);
        }
        if(nplayer.meta.profiles[2].created) {
            if(nplayer.meta.currentProfile != 2) {
                ItemStack item = new ItemStack(Material.YELLOW_DYE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§eProfile 3");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§fLevel §e" + nplayer.meta.profiles[2].level);
                lore.add("§cRight click to delete.");
                meta.setLore(lore);
                item.setItemMeta(meta);
                gui.setItem(15, item);
            }
            else{
                ItemStack item = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§e(Current) Profile 3");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§fLevel §e" + nplayer.meta.profiles[2].level);
                lore.add("§cSwitch off this profile to delete.");
                meta.setLore(lore);
                item.setItemMeta(meta);
                gui.setItem(15, item);
            }
        }
        else{
            ItemStack noProfile = new ItemStack(Material.POLISHED_BLACKSTONE_BUTTON);
            ItemMeta meta = noProfile.getItemMeta();
            meta.setDisplayName("§eClick to create!");
            noProfile.setItemMeta(meta);
            gui.setItem(15, noProfile);
        }
        return gui;
    }


}
