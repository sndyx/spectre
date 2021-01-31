package dev.sndy.spectre.item;

import dev.sndy.spectre.item.stat.Format;
import dev.sndy.spectre.item.stat.Rarity;
import dev.sndy.spectre.item.stat.Stat;
import dev.sndy.spectre.item.stat.StatList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Equipment implements IMaterial {

    public String name;
    public int amount;
    public Material material;
    public StatList stats;
    public Rarity rarity;
    public String description;
    public boolean enchanted;

    public Equipment(String name, int amount, Material material, StatList stats, Rarity rarity, String description, boolean enchanted){
        this.name = name;
        this.amount = amount;
        this.material = material;
        this.stats = stats;
        this.rarity = rarity;
        this.description = description;
        this.enchanted = enchanted;
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = Bukkit.getItemFactory().getItemMeta(material);
        assert meta != null;
        meta.setDisplayName("§" + Stat.getRarityFormatting(rarity) + name);
        meta.setLore(toLore(stats, rarity, description));
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

    public static ArrayList<String> toLore(StatList stats, Rarity rarity, String description){
        ArrayList<String> lore = new ArrayList<>();
        if(stats.getDamage().size() != 0){
            lore.add("");
            for(Stat stat : stats.getDamage()){
                lore.add("§f" + stat.format.get(Format.NAME) + ": §" + Stat.getRarityFormatting(rarity) +  stat.format.get(Format.VALUE) + " §8" + stat.format.get(Format.CHANCE));
            }
        }
        if(stats.getDefense().size() != 0){
            lore.add("");
            for(Stat stat : stats.getDefense()){
                lore.add("§f" + stat.format.get(Format.NAME) + ": §" + Stat.getRarityFormatting(rarity) +  stat.format.get(Format.VALUE) + " §8" + stat.format.get(Format.CHANCE));
            }
        }
        boolean space = true;
        for(Stat stat : stats.getStats()){
            if(stat.value != 0){
                if(space){
                    space = false;
                    lore.add("");
                }
                lore.add("§f" + stat.format.get(Format.NAME) + ": §" + Stat.getRarityFormatting(rarity) +  stat.format.get(Format.VALUE));
            }
        }
        space = true;
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
        for(Stat stat : stats.getRequirements()){
            if(stat.value != 0){
                if(space){
                    space = false;
                    lore.add("");
                }
                lore.add("§f" + stat.format.get(Format.NAME) + ": " +  stat.format.get(Format.VALUE));
            }
        }
        lore.add("");
        lore.add("§" + Stat.getRarityFormatting(rarity) + "§l" + rarity);
        return lore;
    }

}
