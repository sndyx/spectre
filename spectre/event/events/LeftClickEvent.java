package com.sndy.spectre.event.events;

import com.sndy.spectre.event.Set;
import org.bukkit.entity.Player;

public class LeftClickEvent extends EventBase {

    public LeftClickEvent(Player player, Set itemSet){
        this.player = player;
        this.itemSet = itemSet;
    }

}
