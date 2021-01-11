package com.sndy.spectre.event.events;

import com.sndy.spectre.event.Set;
import org.bukkit.entity.*;

public class HitEntityEvent extends EventBase {

    private final double damage;
    private final Entity entity;
    
    public HitEntityEvent(Player player, Entity entity, Set itemSet, double damage){
        this.entity = entity;
        this.itemSet = itemSet;
        this.damage = damage;
        this.player = player;
    }
    
    public double getDamage(){
        return damage;
    }
    
    public Entity getEntity(){
        return entity;
    }
}
