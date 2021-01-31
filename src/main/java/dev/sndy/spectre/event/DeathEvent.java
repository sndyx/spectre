package dev.sndy.spectre.event;

import dev.sndy.spectre.item.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DeathEvent extends Event {

    private final DamageCause deathCause;

    public DeathEvent(Player player, Set set, DamageCause cause){
        this.player = player;
        this.set = set;
        deathCause = cause;
    }

    public DamageCause getCause(){
        return deathCause;
    }

}
