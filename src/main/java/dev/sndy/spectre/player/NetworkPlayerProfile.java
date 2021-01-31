package dev.sndy.spectre.player;

import dev.sndy.spectre.Reference;
import dev.sndy.spectre.item.stat.StatBuilder;
import dev.sndy.spectre.item.stat.StatList;
import dev.sndy.spectre.item.stat.StatType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class NetworkPlayerProfile implements Serializable {

    public Location location;
    public StatList stats;
    public ItemStack[] inventory;
    public ProfileMeta meta;
    public double experience;
    public int level;
    public boolean created;

    public NetworkPlayerProfile(Player player, boolean created){
        location = Reference.SPAWN_LOCATION;
        stats = new StatBuilder().add(StatType.STAT_HEALTH, 20).add(StatType.STAT_MANA, 10).add(StatType.STAT_SPEED, 100).build();
        inventory = new ItemStack[41];
        meta = new ProfileMeta();
        this.created = created;
        level = 1;
    }

    public void update(Player player){
        location = player.getLocation();
        inventory = player.getInventory().getContents();
    }

}
