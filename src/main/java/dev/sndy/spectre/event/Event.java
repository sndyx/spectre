package dev.sndy.spectre.event;

import dev.sndy.spectre.item.Set;
import org.bukkit.entity.Player;

public class Event {

    public Set set;
    public Player player;
    private boolean cancelled = false;

    public Set getSet(){
        return set;
    }

    public Player getPlayer() {
        return player;
    }

    public void setCancelled(boolean cancelled){
        this.cancelled = cancelled;
    }

    public boolean isCancelled(){
        return cancelled;
    }

}
