package com.sndy.spectre.player.stat;

import org.bukkit.inventory.ItemStack;

public class Stat {

    private StatType type;
    private float value;
    private float chance;

    public Stat(StatType type){
        this.type = type;
        value = 0;
        chance = 0;
    }

    public Stat(String line){
        line = line.replaceAll("§.", "");
        try {
            if (line.contains(" ")) {
                String[] args = line.split(" ");
                switch (args[0]) {
                    case "Damage:":
                        type = StatType.DAMAGE_ALL;
                        break;
                    case "Flame-Damage:":
                        type = StatType.DAMAGE_FLAME;
                        break;
                    case "Frost-Damage:":
                        type = StatType.DAMAGE_FROST;
                        break;
                    case "Bleed-Damage:":
                        type = StatType.DAMAGE_BLEED;
                        break;
                    case "Lifesteal:":
                        type = StatType.DAMAGE_STEAL;
                        break;
                    case "Ultimate-Damage:":
                        type = StatType.DAMAGE_ULTIMATE;
                        break;
                    case "Defense:":
                        type = StatType.DEFENSE_ALL;
                        break;
                    case "Fire-Defense:":
                        type = StatType.DEFENSE_FIRE;
                        break;
                    case "Ice-Defense:":
                        type = StatType.DEFENSE_ICE;
                        break;
                    case "Bleed-Defense:":
                        type = StatType.DEFENSE_BLEED;
                        break;
                    case "Gilded-Defense:":
                        type = StatType.DEFENSE_GILDED;
                        break;
                    case "Ultimate-Defense:":
                        type = StatType.DEFENSE_ULTIMATE;
                        break;
                    case "Strength:":
                        type = StatType.SKILL_STRENGTH;
                        break;
                    case "Wisdom:":
                        type = StatType.SKILL_WISDOM;
                        break;
                    case "Agility:":
                        type = StatType.SKILL_AGILITY;
                        break;
                    case "Health:":
                        type = StatType.STAT_HEALTH;
                        break;
                    case "Mana:":
                        type = StatType.STAT_MANA;
                        break;
                    case "Strength-Min:":
                        type = StatType.REQUIREMENT_STRENGTH;
                        break;
                    case "Wisdom-Min:":
                        type = StatType.REQUIREMENT_WISDOM;
                        break;
                    case "Agility-Min:":
                        type = StatType.REQUIREMENT_AGILITY;
                        break;
                    default:
                        type = StatType.INVALID;
                        return;
                }
                if (args.length > 1) {
                    value = Float.parseFloat(args[1].substring(1));
                } else {
                    type = StatType.INVALID;
                    return;
                }
                if(args.length > 2){
                    chance = Float.parseFloat(args[2].substring(1, args[2].indexOf("%")));
                } else {
                    chance = 100; 
                }
            } else {
                type = StatType.INVALID;
            }
        } catch(Exception e){
            type = StatType.INVALID;
        }
    }

    public StatType getType(){
        return type;
    }

    public float getValue(){
        return value;
    }

    public float getChance(){
        return chance;
    }

    public void setValue(float value){
        this.value = value;
    }

    public void setChance(float chance){
        this.chance = chance;
    }

    public void merge(Stat stat){
        if(type == stat.getType()){
            value += stat.getValue();
            chance += stat.getChance();
        } else {
            type = StatType.INVALID;
        }
    }
    
    public void override(Stat stat) {
        if (type == stat.getType()) {
            if (stat.getValue() > value) {
                value = stat.getValue();
            }
        } else {
            type = StatType.INVALID;
        }
    }

    public static StatList parseItem(ItemStack item){
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
}
