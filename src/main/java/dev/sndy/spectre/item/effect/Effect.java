package dev.sndy.spectre.item.effect;

import dev.sndy.spectre.item.stat.Format;
import dev.sndy.spectre.item.stat.Rarity;
import dev.sndy.spectre.item.stat.Stat;

import java.text.DecimalFormat;

public class Effect {

    public final Effect.Formatter format = new Effect.Formatter();
    public final EffectType type;
    public double value;
    public double chance;
    private int blessing;

    public Effect(EffectType type) {
        this.type = type;
        value = 0;
        chance = 0;
    }

    public Effect(String input) {
        EffectType type;
        input = input.replaceAll("§.|Ⅰ|Ⅱ|Ⅲ|Ⅳ|Ⅴ|Ⅵ|Ⅶ|Ⅷ|Ⅸ|Ⅼ", "").trim();
        if (input.contains(" ")) {
            String[] args = input.split(" ");
            switch (args[0]) {
                case "Health:":
                    type = EffectType.EFFECT_HEALTH;
                    break;
                case "Mana:":
                    type = EffectType.EFFECT_MANA;
                    break;
                case "Absorption":
                    type = EffectType.EFFECT_ABSORPTION;
                    break;
                default:
                    this.type = EffectType.INVALID;
                    return;
            }
            if (args.length > 1 && isDouble(args[1])) {
                value = Double.parseDouble(args[1].substring(1));
            } else {
                this.type = EffectType.INVALID;
                return;
            }
            if (args.length > 2) {
                if (args.length == 4){
                    if(isDouble((args[2].substring(1, args[2].indexOf("%")))) && isDouble(args[3])) {
                        chance = Double.parseDouble(args[2].substring(1, args[2].indexOf("%")));
                        blessing = new Double(args[3]).intValue();
                        value -= blessing;
                    } else {
                        this.type = EffectType.INVALID;
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
            this.type = EffectType.INVALID;
            return;
        }
        this.type = type;
    }

    public void bless() {
        //do stuff
    }

    public boolean isBlessed() {
        return blessing != 0;
    }

    public void merge(Effect effect) {
        if (type == effect.type) {
            value += effect.value;
            chance += effect.chance;
        }
    }

    public void override(Effect effect) {
        if (type == effect.type) {
            if (effect.value > value) {
                value = effect.value;
            }
        }
    }

    public class Formatter {

        public String get(Format format) {
            switch (format) {
                case NAME:
                    switch (type) {
                        case EFFECT_ABSORPTION:
                            return "Absorption";
                        case EFFECT_HEALTH:
                            return "Health";
                        case EFFECT_MANA:
                            return "Mana";
                    }
                case VALUE:
                    if (value * (blessing * 0.01 + 1) >= 0) {
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

        public String getFormattedBlessingCharacter(Rarity rarity) {
            int val = blessing / 10 - 1;
            if (val < 0) val = 0;
            if (blessing != 0) {
                final String LEVELS = "ⅠⅡⅢⅣⅤⅥⅦⅧⅨ";
                return val == 9 ? "§" + Stat.getRarityFormatting(rarity) + "§lⅬ" : "§8" + LEVELS.charAt(val);
            }
            return "";
        }

    }

    private boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}