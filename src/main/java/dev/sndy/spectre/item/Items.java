package dev.sndy.spectre.item;

import dev.sndy.spectre.item.effect.Effect;
import dev.sndy.spectre.item.effect.EffectList;
import dev.sndy.spectre.item.stat.Stat;
import dev.sndy.spectre.item.stat.StatList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Items {

    public static IMaterial fromItemStack(ItemStack item){
        if(item != null && item.getItemMeta() != null && item.getItemMeta().getLore() != null){
            String lastLine = item.getItemMeta().getLore().get(item.getItemMeta().getLore().size() - 1);
            if(lastLine.equals("FOOD")){
                EffectList effects = new EffectList();
                StringBuilder description = new StringBuilder();
                for(String line : item.getItemMeta().getLore()){
                    effects.add(new Effect(line));
                    if(line.startsWith("§8")){
                        description.append(line.substring(2)).append(' ');
                    }
                }
                return new Food(item.getItemMeta().getDisplayName().substring(2), item.getAmount(), item.getType(), effects, description.toString(), item.getItemMeta().hasEnchants());
            }
            else if(lastLine.equals("RESOURCE")){
                StringBuilder description = new StringBuilder();
                for(String line : item.getItemMeta().getLore()){
                    if(line.startsWith("§8")){
                        description.append(line.substring(2)).append(' ');
                    }
                }
                return new Resource(item.getItemMeta().getDisplayName().substring(2), item.getAmount(), item.getType(), description.toString(), item.getItemMeta().hasEnchants());
            }
            else if(lastLine.contains("COMMON") || lastLine.contains("UNCOMMON") || lastLine.contains("RARE") || lastLine.contains("EPIC") || lastLine.contains("LEGENDARY") || lastLine.contains("DIVINE")){
                StatList stats = new StatList();
                StringBuilder description = new StringBuilder();
                for(String line : item.getItemMeta().getLore()){
                    stats.add(new Stat(line));
                    if(line.startsWith("§8")){
                        description.append(line.substring(2)).append(' ');
                    }
                }
                return new Equipment(item.getItemMeta().getDisplayName().substring(2), item.getAmount(), item.getType(), stats, Stat.getFormattingRarity(lastLine.substring(0, 2)), description.toString(), item.getItemMeta().hasEnchants());
            }
            else{
                return new Resource("Null", 0, Material.BARRIER, "", false);
            }
        }
        else{
            return new Resource("Null", 0, Material.BARRIER, "", false);
        }
    }

    public static ItemType getType(ItemStack item){
        String lastLine = item.getItemMeta().getLore().get(item.getItemMeta().getLore().size() - 1);
        if(lastLine.equals("RESOURCE")){
            return ItemType.RESOURCE;
        }
        if(lastLine.equals("FOOD")){
            return ItemType.FOOD;
        }
        if(lastLine.contains("COMMON") || lastLine.contains("UNCOMMON") || lastLine.contains("RARE") || lastLine.contains("EPIC") || lastLine.contains("LEGENDARY") || lastLine.contains("DIVINE")){
            return ItemType.EQUIPMENT;
        }
        return ItemType.RESOURCE;
    }

}
