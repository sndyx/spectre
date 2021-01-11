package com.sndy.spectre.player;

import com.sndy.spectre.player.stat.StatList;
import org.bukkit.entity.Player;

public class NetworkPlayer {

    public StatList stats;
    public final NetworkPlayerMeta meta;
    public final Player player;
    public boolean summon = false;
    public float mana = 20;

    /**
     * Creates a NetworkPlayer object with the
     * given metadata and player.
     * @param player Player
     * @param meta   NPMeta
     */
    public NetworkPlayer(Player player, NetworkPlayerMeta meta){
        this.meta = meta;
        this.player = player;
    }

}
