package dev.sndy.spectre.item;

import dev.sndy.spectre.item.stat.Rarity;
import dev.sndy.spectre.item.stat.Stat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Resource implements IMaterial {

    public String name;
    public int amount;
    public Material material;
    public String description;
    public boolean enchanted;

    public Resource(String name, int amount, Material material, String description, boolean enchanted){
        this.name = name;
        this.amount = amount;
        this.material = material;
        this.description = description;
        this.enchanted = enchanted;
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = Bukkit.getItemFactory().getItemMeta(material);
        assert meta != null;
        meta.setDisplayName("§9" + name);
        meta.setLore(toLore(description));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        item.setItemMeta(meta);
        if(enchanted) item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return item;
    }

    public static ArrayList<String> toLore(String description){
        ArrayList<String> lore = new ArrayList<>();
        if(!description.equals("")){
            lore.add(" ");
            final int MAX_CHARS = 30;
            StringTokenizer tok = new StringTokenizer(description, " ");
            StringBuilder output = new StringBuilder(description.length());
            int lineLen = 0;
            while (tok.hasMoreTokens()) {
                String word = tok.nextToken();

                if(word.equals("\n")){
                    output.append("\n");
                    word = tok.nextToken();
                    lineLen = 0;
                }

                while(word.length() > MAX_CHARS){
                    output.append(word, 0, MAX_CHARS - lineLen).append("\n");
                    word = word.substring(MAX_CHARS-lineLen);
                    lineLen = 0;
                }

                if (lineLen + word.length() > MAX_CHARS) {
                    output.append("\n");
                    lineLen = 0;
                }
                output.append(word).append(" ");

                lineLen += word.length() + 1;
            }
            for(String line : output.toString().split("\n")){
                lore.add("§8" + line);
            }
        }
        lore.add("");
        lore.add("§9§lRESOURCE");
        return lore;
    }

}
