package dev.sndy.spectre.event;

import dev.sndy.spectre.item.Set;
import org.bukkit.entity.Player;

public class DamagedByPlayerEvent extends Event {

    private double damage;
    public boolean died = false;
    private final Player damager;

    public DamagedByPlayerEvent(Player player, Player damager, Set set, double damage){
        this.player = player;
        this.damager = damager;
        this.set = set;
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
