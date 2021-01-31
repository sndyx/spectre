package dev.sndy.spectre.player;

import org.bukkit.entity.Player;

import java.io.Serializable;

public class NetworkPlayerMeta implements Serializable {

    public Rank rank = Rank.DEFAULT;
    public int currentProfile = 0;

    public NetworkPlayerMeta(Player player){
        profiles[0] = new NetworkPlayerProfile(player, false);
        profiles[1] = new NetworkPlayerProfile(player, false);
        profiles[2] = new NetworkPlayerProfile(player, false);
    }

    public NetworkPlayerProfile[] profiles = new NetworkPlayerProfile[3];

}
