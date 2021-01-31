package dev.sndy.spectre.event;

import dev.sndy.spectre.item.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DamagedEvent extends Event {

    public boolean died = false;
    private final DamageCause damageCause;
    private final double damage;

    public DamagedEvent(Player player, Set set, DamageCause damageCause, double damage){
        this.player = player;
        this.set = set;
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
