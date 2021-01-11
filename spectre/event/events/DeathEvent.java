package com.sndy.spectre.event.events;

import com.sndy.spectre.event.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DeathEvent extends EventBase {

    private final DamageCause deathCause;

    public DeathEvent(Player player, Set itemSet, DamageCause cause){
        this.player = player;
        this.itemSet = itemSet;
        deathCause = cause;
    }

    public DamageCause getCause(){
        return deathCause;
    }

}
