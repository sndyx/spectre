package dev.sndy.spectre.item.stat;

import java.io.Serializable;
import java.util.ArrayList;

public class StatList implements Serializable {

    private final Stat[] requirements = {new Stat(StatType.REQUIREMENT_AGILITY), new Stat(StatType.REQUIREMENT_STRENGTH), new Stat(StatType.REQUIREMENT_WISDOM)};
    private final ArrayList<Stat> damageStats = new ArrayList<>();
    private final ArrayList<Stat> defenseStats = new ArrayList<>();
    private final Stat[] stats = {new Stat(StatType.STAT_HEALTH), new Stat(StatType.STAT_MANA), new Stat(StatType.STAT_SPEED), new Stat(StatType.SKILL_AGILITY), new Stat(StatType.SKILL_STRENGTH), new Stat(StatType.SKILL_WISDOM)};

    public void add(Stat stat){
        switch(stat.type){
            case DAMAGE_ALL:
            case DAMAGE_BLEED:
            case DAMAGE_FLAME:
            case DAMAGE_FROST:
            case DAMAGE_STEAL:
            case DAMAGE_ULTIMATE:
                damageStats.add(stat);
                break;
            case DEFENSE_ALL:
            case DEFENSE_BLEED:
            case DEFENSE_FIRE:
            case DEFENSE_GILDED:
            case DEFENSE_ICE:
            case DEFENSE_ULTIMATE:
                defenseStats.add(stat);
                break;
            case STAT_HEALTH:
                stats[0].merge(stat);
                break;
            case STAT_MANA:
                stats[1].merge(stat);
                break;
            case STAT_SPEED:
                stats[2].merge(stat);
                break;
            case SKILL_AGILITY:
                stats[3].merge(stat);
                break;
            case SKILL_STRENGTH:
                stats[4].merge(stat);
                break;
            case SKILL_WISDOM:
                stats[5].merge(stat);
                break;
            case REQUIREMENT_AGILITY:
                requirements[0].override(stat);
                break;
            case REQUIREMENT_STRENGTH:
                requirements[1].override(stat);
                break;
            case REQUIREMENT_WISDOM:
                requirements[2].override(stat);
            default:
        }
    }

    public void merge(StatList stats){
        damageStats.addAll(stats.getDamage());
        defenseStats.addAll(stats.getDefense());
        for(int i = 0; i < 5; i++){ this.stats[i].merge(stats.stats[i]); }
        if(this.stats[0].value < 0){
            this.stats[0].value = 0;
        }
        for(int z = 0; z < 3; z++){
            if(requirements[z].value < stats.getRequirements()[z].value){
                requirements[z].value = (stats.getRequirements()[z].value);
            }
        }
    }

    public ArrayList<Stat> getDamage(){
        return damageStats;
    }

    public ArrayList<Stat> getDefense(){
        return defenseStats;
    }

    public Stat[] getStats(){
        return stats;
    }

    public Stat[] getRequirements(){return requirements; }

    public boolean isBlessed(){
        if(!damageStats.isEmpty()){
            return damageStats.get(0).isBlessed();
        }
        if(!defenseStats.isEmpty()){
            return defenseStats.get(0).isBlessed();
        }
        return false;
    }

    public double getTotalDefense(){
        double total = 0;
        for(Stat stat : defenseStats){
            total += stat.value;
        }
        return total;
    }

    public double getTotalDamage(){
        double total = 0;
        for(Stat stat : damageStats){
            total += stat.value;
        }
        return total;
    }
}
