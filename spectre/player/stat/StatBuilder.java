package com.sndy.spectre.player.stat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author Sandy
 *
 * Utility class for creating a new StatList.
 * <p>
 * Use {@link #add(StatType, float)} or
 * {@link #add(StatType, float, float)} to
 * add values.
 * <p>
 * Example use:
 * <code>
 *    StatBuilder stats = new StatBuilder()
 *       .add(StatType.DAMAGE_ALL, 100)
 *       .add(StatType.DEFENSE_ICE, 50, 66.6)
 *       .build();
 * </code>
 */
public class StatBuilder {

    final StatList stats = new StatList();

    /**
     * Adds a stat to the {@link StatList}.
     * <p>
     * Automatically assigns 100% to
     * {@link Stat#setChance(float)}.
     *
     * @param type stat type
     * @param value stat value
     * @return      stats
     */
    public StatBuilder add(StatType type, float value){
        Stat stat = new Stat(type);
        stat.setValue(value);
        stat.setChance(100);
        stats.add(stat);
        return this;
    }

    /**
     * Adds a stat to the {@link StatList}.
     *
     * @param type stat type
     * @param value stat value
     * @param chance stat chance
     * @return       stats
     */
    public StatBuilder add(StatType type, float value, float chance){
        Stat stat = new Stat(type);
        stat.setValue(value);
        stat.setChance(chance);
        stats.add(stat);
        return this;
    }

    /**
     * Builds the specified stats into a
     * {@link StatList}.
     *
     * @return stats
     */
    public StatList build(){
        return stats;
    }

    /**
     * Returns a {@link StatList} from an
     * item('s lore).
     *
     * @param item item
     * @return     stats
     */
    public static StatList fromLore(ItemStack item){
        StatList stats = new StatList();
        if(item != null){
            if(item.getItemMeta() != null){
                if(item.getItemMeta().getLore() != null){
                    for(String line : item.getItemMeta().getLore()){
                        stats.add(new Stat(line));
                    }
                }
            }
        }
        return stats;
    }

    /**
     * Returns a {@link ArrayList<String>} of lore when
     * given stats, rarity, and item description.
     * <p>
     * Leave description blank ("") for no description.
     *
     * @param stats       item stats
     * @param rarity      item rarity
     * @param description item description
     * @return            item lore
     */
    public static ArrayList<String> toLore(StatList stats, Rarity rarity, String description){
        float blessingPercentage = 0;
        ArrayList<String> lore = new ArrayList<>();
        ArrayList<String> damageStats = new ArrayList<>();
        for(Stat stat : stats.getDamage()){
            if(stat.getChance() != 100) {
                damageStats.add("§f" + Stat.getFormattedName(stat.getType()) + ": §" + Stat.getRarityFormatting(rarity) + "+" + stat.getValueString() + " §8(" + stat.getChanceString() + "%) " + getFormattedBlessing(stat.getBlessingValue()));
            }
            else{
                damageStats.add("§f" + Stat.getFormattedName(stat.getType()) + ": §" + Stat.getRarityFormatting(rarity) + "+" + stat.getValueString() + " " + getFormattedBlessing(stat.getBlessingValue()));
            }
        }
        ArrayList<String> defenseStats = new ArrayList<>();
        for(Stat stat : stats.getDefense()){
            if(stat.getChance() != 100) {
                defenseStats.add("§f" + Stat.getFormattedName(stat.getType()) + ": §" + Stat.getRarityFormatting(rarity) + "+" + stat.getValueString() + " §8(" + stat.getChanceString() + "%) " + getFormattedBlessing(stat.getBlessingValue()));
            }
            else{
                defenseStats.add("§f" + Stat.getFormattedName(stat.getType()) + ": §" + Stat.getRarityFormatting(rarity) + "+" + stat.getValueString() + " " + getFormattedBlessing(stat.getBlessingValue()));
            }
        }
        ArrayList<String> skillStats = new ArrayList<>();
        for(Stat stat : stats.getStats()){
            if(stat.getValue() != 0) {
                skillStats.add("§f" + Stat.getFormattedName(stat.getType()) + ": §" + Stat.getRarityFormatting(rarity) + "+" + stat.getValueString());
            }
        }
        ArrayList<String> requirementStats = new ArrayList<>();
        for(Stat stat : stats.getRequirements()){
            if(stat.getValue() != 0) {
                requirementStats.add("§f" + Stat.getFormattedName(stat.getType()) + ": " + stat);
            }
        }
        if(!damageStats.isEmpty()){
            lore.add(" ");
            lore.addAll(damageStats);
        }
        if(!defenseStats.isEmpty()){
            lore.add(" ");
            lore.addAll(defenseStats);
        }
        if(!skillStats.isEmpty()){
            lore.add(" ");
            lore.addAll(skillStats);
        }
        if(!description.equals("")){
            lore.add(" ");
            int MAX_CHARS = 30;
            StringTokenizer tok = new StringTokenizer(description, " ");
            StringBuilder output = new StringBuilder(description.length());
            int lineLen = 0;
            while (tok.hasMoreTokens()) {
                String word = tok.nextToken();

                while(word.length() > MAX_CHARS){
                    output.append(word.substring(0, MAX_CHARS-lineLen) + "\n");
                    word = word.substring(MAX_CHARS-lineLen);
                    lineLen = 0;
                }

                if (lineLen + word.length() > MAX_CHARS) {
                    output.append("\n");
                    lineLen = 0;
                }
                output.append(word + " ");

                lineLen += word.length() + 1;
            }
            for(String line : output.toString().split("\n")){
                lore.add("§8" + line);
            }
        }
        if(!requirementStats.isEmpty()){
            lore.add(" ");
            lore.addAll(requirementStats);
        }
        damageStats.addAll(defenseStats());
        int stats = 0;
        for(Stat stat : damageStats){
            if(stat.isBlessed()){
                stats++;
                blessingPercentage += stat.getBlessingValue();
            }
        }
        lore.add(" ");
        lore.add("§" + Stat.getRarityFormatting(rarity) + "§l" + rarity + "§8(" + (blessingPercentage / stats) + ") " + getFormattedBlessing(blessingPercentage / stats));
        return lore;
    }

    /**
     * Takes a set of item parameters and returns
     * an {@link ItemStack} with RPG formatted lore.
     *
     * @param name        item name
     * @param material    item material
     * @param stats       item stats
     * @param rarity      item rarity
     * @param description item description
     * @return            formatted item
     */
    public static ItemStack toItem(String name, Material material, StatList stats, Rarity rarity, String description){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = Bukkit.getItemFactory().getItemMeta(material);
        meta.setDisplayName("§" + Stat.getRarityFormatting(rarity) + name);
        meta.setLore(toLore(stats, rarity, description));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        item.setItemMeta(meta);
        return item;
    }
    
    public String getFormattedBlessing(int blessingPercentage){
        if(blessingPercentage != 0){
            String chars = "ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅬ";
            if(blessingPercentage / 10 == 10){
                return "§6" + String.valueOf(chars.charAt(blessingValue / 10));
            }
            return "§8" + String.valueOf(chars.charAt(blessingValue / 10));
        }
        return "";
    }

}
