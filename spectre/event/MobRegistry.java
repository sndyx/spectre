package com.sndy.spectre.event;

import com.sndy.spectre.player.stat.StatList;
import com.sndy.spectre.player.stat.StatBuilder;

import java.util.HashMap;

public class MobRegistry {

    public static HashMap<String, StatList> registry = new HashMap<>();

    /**
     * Register a new mob to the registry.
     *
     * @param stats mob stats (use {@link StatBuilder})
     * @param mobs mob name(s)
     */
    public static void register(StatList stats, String... mobs){
        for(String mob : mobs){
            registry.put(mob, stats);
        }
    }

}
