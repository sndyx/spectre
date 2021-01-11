package com.sndy.spectre.player.stat;

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

    public StatList build(){
        return stats;
    }

}
