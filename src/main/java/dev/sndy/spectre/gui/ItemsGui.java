package dev.sndy.spectre.gui;

import dev.sndy.spectre.item.IMaterial;
import dev.sndy.spectre.item.ItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemsGui {

    public int currentPage;

    public Inventory getInventory(Player player) {
        return getInventory(player,1);
    }

    public Inventory getInventory(Player player, int page) {
        currentPage = page;
        Inventory gui = Bukkit.createInventory(player, 45, "Items");
        ArrayList<IMaterial> items = new ArrayList<>(ItemRegistry.registry.values());
        ItemStack[] displays = new ItemStack[27];
        for(int index = (page - 1) * 27; index < page * 27 && index < items.size(); index++){
            displays[index] = items.get(index).toItemStack();
        }
        for(int index = 0; index < displays.length; index++){
            gui.setItem(index + 9, displays[index]);
        }
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "Page " + page + "/" + getPages(items.size()));
        ItemStack topDisplay = new ItemStack(Material.NAME_TAG);
        ItemMeta iMeta1 = topDisplay.getItemMeta();
        iMeta1.setDisplayName(ChatColor.GREEN + "Items");
        iMeta1.setLore(lore);
        topDisplay.setItemMeta(iMeta1);
        gui.setItem(4, topDisplay);
        if(page != 1) {
            ItemStack backArrow = new ItemStack(Material.ARROW);
            ItemMeta iMeta2 = backArrow.getItemMeta();
            iMeta2.setDisplayName(ChatColor.GREEN + "Previous Page");
            backArrow.setItemMeta(iMeta2);
            gui.setItem(36, backArrow);
        }
        if(page != getPages(items.size())){
            ItemStack forwardArrow = new ItemStack(Material.ARROW);
            ItemMeta iMeta3 = forwardArrow.getItemMeta();
            iMeta3.setDisplayName(ChatColor.GREEN + "Next Page");
            forwardArrow.setItemMeta(iMeta3);
            gui.setItem(44, forwardArrow);
        }
        return gui;
    }

    public int getPages(int size){
        int pages = 0;
        while(true){
            size -= 27;
            pages++;
            if(size <= 0){
                return pages;
            }
        }
    }

}
