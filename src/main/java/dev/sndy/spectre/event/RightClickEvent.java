package dev.sndy.spectre.event;

import dev.sndy.spectre.item.Set;
import org.bukkit.entity.Player;

public class RightClickEvent extends Event {

    public RightClickEvent(Player player, Set set){
        this.player = player;
        this.set = set;
    }

}
