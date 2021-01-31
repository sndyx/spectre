package dev.sndy.spectre.event;

import dev.sndy.spectre.item.Set;
import org.bukkit.entity.Player;

public class DeathByPlayerEvent extends Event {

    private final Player killer;

    public DeathByPlayerEvent(Player player, Set set, Player killer){
        this.player = player;
        this.set = set;
        this.killer = killer;
    }

    public Player getKiller(){
        return killer;
    }

}
