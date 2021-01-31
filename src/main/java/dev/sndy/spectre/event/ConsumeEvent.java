package dev.sndy.spectre.event;

import dev.sndy.spectre.item.Set;
import org.bukkit.entity.Player;

public class ConsumeEvent extends Event {

    public ConsumeEvent(Player player, Set set){
        this.player = player;
        this.set = set;
    }

}