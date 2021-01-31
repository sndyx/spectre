package dev.sndy.spectre.item.stat;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Stat implements Serializable {

    public final Formatter format = new Formatter();
    public final StatType type;
    public double value;
    public double chance;
    private int blessing;

    public Stat(StatType type){
        this.type = type;
        value = 0;
        chance = 0;
    }

    public Stat(String input) {
        StatType type;
        input = input.replaceAll("§.|Ⅰ|Ⅱ|Ⅲ|Ⅳ|Ⅴ|Ⅵ|Ⅶ|Ⅷ|Ⅸ|Ⅼ", "").trim();
        if (input.contains(" ")) {
            String[] args = input.split(" ");
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
                case "Speed":
                    type = StatType.STAT_SPEED;
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
                    this.type = StatType.INVALID;
                    return;
            }
            if (args.length > 1 && isDouble(args[1])) {
                value = Double.parseDouble(args[1].substring(1));
            } else {
                this.type = StatType.INVALID;
                return;
            }
            if (args.length > 2) {
                if (args.length == 4){
                    if(isDouble((args[2].substring(1, args[2].indexOf("%")))) && isDouble(args[3])) {
                        chance = Double.parseDouble(args[2].substring(1, args[2].indexOf("%")));
                        blessing = new Double(args[3]).intValue();
                        value -= blessing;
                    } else {
                        this.type = StatType.INVALID;
                        return;
                    }
                } else if(args[2].contains("%")) {
                    if(isDouble(args[2].substring(1, args[2].indexOf("%")))) chance = Double.parseDouble(args[2].substring(1, args[2].indexOf("%")));
                } else if(isDouble(args[2].substring(1))){
                    blessing = (int) Double.parseDouble(args[2].substring(1));
                    value -= blessing;
                }
            } else {
                chance = 100;
            }
        } else {
            this.type = StatType.INVALID;
            return;
        }
        this.type = type;
    }

    public void bless(){
        //do stuff
    }

    public boolean isBlessed() {
        return blessing != 0;
    }

    public void merge(Stat stat){
        if(type == stat.type){
            value += stat.value;
            chance += stat.chance;
        }
    }
    
    public void override(Stat stat) {
        if (type == stat.type) {
            if (stat.value > value) {
                value = stat.value;
            }
        }
    }

    /**
     * Returns the color code formatting index
     * for a given {@link Rarity}.
     *
     * @param rarity rarity
     * @return       color formatting code
     */
    public static String getRarityFormatting(Rarity rarity){
        switch(rarity){
            default:
            case COMMON:
                return "a";
            case UNCOMMON:
                return "e";
            case RARE:
                return "6";
            case EPIC:
                return "c";
            case LEGENDARY:
                return "d";
            case DIVINE:
                return "b";
        }
    }

    /**
     * Gets the rarity of a formatting code
     *
     * @param formatting formatting code
     * @return           rarity
     */
    public static Rarity getFormattingRarity(String formatting){
        switch(formatting){
            default:
            case "a":
                return Rarity.COMMON;
            case "e":
                return Rarity.UNCOMMON;
            case "6":
                return Rarity.RARE;
            case "c":
                return Rarity.EPIC;
            case "d":
                return Rarity.LEGENDARY;
            case "b":
                return Rarity.DIVINE;
        }
    }

    public class Formatter implements Serializable{

        public String get(Format format){
            switch(format) {
                case NAME:
                    switch (type) {
                        case DAMAGE_ALL:
                            return "Damage";
                        case DAMAGE_FLAME:
                            return "Flame-Damage";
                        case DAMAGE_FROST:
                            return "Frost-Damage";
                        case DAMAGE_BLEED:
                            return "Bleed-Damage";
                        case DAMAGE_STEAL:
                            return "Lifesteal";
                        case DAMAGE_ULTIMATE:
                            return "Ultimate-Damage";
                        case DEFENSE_ALL:
                            return "Defense";
                        case DEFENSE_FIRE:
                            return "Fire-Defense";
                        case DEFENSE_ICE:
                            return "Ice-Defense";
                        case DEFENSE_BLEED:
                            return "Bleed-Defense";
                        case DEFENSE_GILDED:
                            return "Gilded-Defense";
                        case DEFENSE_ULTIMATE:
                            return "Ultimate-Defense";
                        case SKILL_STRENGTH:
                            return "Strength";
                        case SKILL_WISDOM:
                            return "Wisdom";
                        case SKILL_AGILITY:
                            return "Agility";
                        case STAT_HEALTH:
                            return "Health";
                        case STAT_MANA:
                            return "Mana";
                        case STAT_SPEED:
                            return "Speed";
                        case REQUIREMENT_STRENGTH:
                            return "Strength-Min";
                        case REQUIREMENT_WISDOM:
                            return "Wisdom-Min";
                        case REQUIREMENT_AGILITY:
                            return "Agility-Min";
                        default:
                            return "Invalid-Stat";
                    }
                case VALUE:
                    if(value * (blessing * 0.01 + 1) >= 0) {
                        return "+" + new DecimalFormat("#.##").format(value * (blessing * 0.01 + 1));
                    }
                    return new DecimalFormat("#.##").format(value * (blessing * 0.01 + 1));
                case CHANCE:
                    double fixedChance = chance * (blessing * 0.005 + 1);
                    if (fixedChance >= 100) {
                        return "";
                    }
                    return "(" + new DecimalFormat("#.##").format(fixedChance) + "%)";
                case BLESSING:
                    return blessing != 0 ? new DecimalFormat("#.##").format(blessing) : "";
            }
            return "Invalid-Format";
        }

        public String getFormattedBlessingCharacter(Rarity rarity){
            int val = blessing / 10 - 1;
            if(val < 0) val = 0;
            if(blessing != 0){
                final String LEVELS = "ⅠⅡⅢⅣⅤⅥⅦⅧⅨ";
                return val == 9 ? "§" + Stat.getRarityFormatting(rarity) + "§lⅬ" : "§8" + LEVELS.charAt(val);
            }
            return "";
        }

    }

    private boolean isDouble(String input){
        try{
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

}