package dev.sndy.spectre.event;

import dev.sndy.spectre.item.Set;
import org.bukkit.entity.*;

public class HitEntityEvent extends Event {

    private final double damage;
    private final Entity entity;
    
    public HitEntityEvent(Player player, Entity entity, Set set, double damage){
        this.entity = entity;
        this.set = set;
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
