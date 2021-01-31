package dev.sndy.spectre.gui;

import dev.sndy.spectre.player.NetworkPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class DeleteGui {

    public Inventory getInventory(Player player, int profile) {
        NetworkPlayerHandler.getPlayer(player).data.deletedProfile = profile;
        Inventory gui = Bukkit.createInventory(player, 27, "Delete Profile");
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§c§lAre you SURE you want to delete this profile?");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§cDeleted profiles cannot be recovered!");
        lore.add("§cLeft click to delete.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        gui.setItem(13, item);
        return gui;
    }

}
