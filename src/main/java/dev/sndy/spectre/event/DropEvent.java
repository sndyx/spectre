package dev.sndy.spectre.event;

import dev.sndy.spectre.item.Set;
import org.bukkit.entity.Player;

public class DropEvent extends Event {

    public DropEvent(Player player, Set set){
        this.player = player;
        this.set = set;
    }

}
