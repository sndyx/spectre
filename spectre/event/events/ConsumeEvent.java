package com.sndy.spectre.event.events;

import com.sndy.spectre.event.Set;
import org.bukkit.entity.Player;

public class ConsumeEvent extends EventBase {

    public ConsumeEvent(Player player, Set itemSet){
        this.player = player;
        this.itemSet = itemSet;
    }

}