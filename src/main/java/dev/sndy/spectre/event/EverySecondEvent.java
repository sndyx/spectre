package dev.sndy.spectre.event;

import dev.sndy.spectre.item.Set;
import org.bukkit.entity.Player;

public class EverySecondEvent extends Event {

    public EverySecondEvent(Player player, Set set){
        this.player = player;
        this.set = set;
    }

}
