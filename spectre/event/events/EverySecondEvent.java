package com.sndy.spectre.event.events;

import com.sndy.spectre.event.Set;
import org.bukkit.entity.Player;

public class EverySecondEvent extends EventBase {

    public EverySecondEvent(Player player, Set itemSet){
        this.player = player;
        this.itemSet = itemSet;
    }

}
