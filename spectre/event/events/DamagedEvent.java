package com.sndy.spectre.event.events;

import com.sndy.spectre.event.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DamagedEvent extends EventBase {

    public boolean died = false;
    private final DamageCause damageCause;
    private final double damage;

    public DamagedEvent(Player player, Set itemSet, DamageCause damageCause, double damage){
        this.player = player;
        this.itemSet = itemSet;
        this.damageCause = damageCause;
        this.damage = damage;
        if(player.getHealth() - damage <= 0){
            died = true;
        }
    }

    public DamageCause getDamageCause(){
        return damageCause;
    }

    public double getDamage(){
        return damage;
    }

}
