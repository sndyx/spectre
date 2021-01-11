package com.sndy.spectre.event.events;

import com.sndy.spectre.event.Set;
import org.bukkit.entity.Player;

public class DeathByPlayerEvent extends EventBase {

    private final Player killer;

    public DeathByPlayerEvent(Player player, Set itemSet, Player killer){
        this.player = player;
        this.itemSet = itemSet;
        this.killer = killer;
    }

    public Player getKiller(){
        return killer;
    }

}
