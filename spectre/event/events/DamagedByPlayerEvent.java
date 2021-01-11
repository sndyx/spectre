package com.sndy.spectre.event.events;

import com.sndy.spectre.event.Set;
import org.bukkit.entity.Player;

public class DamagedByPlayerEvent extends EventBase {

    private double damage;
    public boolean died = false;
    private final Player damager;
    public DamagedByPlayerEvent(Player player, Player damager, Set itemSet, double damage){
        this.player = player;
        this.damager = damager;
        this.itemSet = itemSet;
        if(player.getHealth() - damage <= 0){
            died = true;
        }
    }

    public Player getDamager(){
        return damager;
    }
    public double getDamage(){
        return damage;
    }

}
