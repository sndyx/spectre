package dev.sndy.spectre.item;

import dev.sndy.spectre.item.effect.Effect;
import dev.sndy.spectre.item.effect.EffectList;
import dev.sndy.spectre.item.effect.EffectType;
import dev.sndy.spectre.item.stat.Format;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Food implements IMaterial {

    public String name;
    public int amount;
    public Material material;
    public EffectList effects;
    public String description;
    public boolean enchanted;

    public Food(String name, int amount, Material material, EffectList effects, String description, boolean enchanted){
        this.name = name;
        this.amount = amount;
        this.material = material;
        this.effects = effects;
        this.description = description;
        this.enchanted = enchanted;
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = Bukkit.getItemFactory().getItemMeta(material);
        assert meta != null;
        meta.setDisplayName("§9" + name);
        meta.setLore(toLore(effects, description));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        item.setItemMeta(meta);
        if (enchanted) item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return item;
    }

    public static ArrayList<String> toLore(EffectList effects, String description){
        ArrayList<String> lore = new ArrayList<>();
        if(effects.getEffects().size() != 0){
            lore.add("");
            for(Effect effect : effects.getEffects()){
                lore.add("§f" + effect.format.get(Format.NAME) + ": §9" + effect.format.get(Format.VALUE) + " §8" + effect.format.get(Format.CHANCE));
            }
        }
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
        lore.add("§9§lFOOD");
        return lore;
    }

}
