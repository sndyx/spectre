package dev.sndy.spectre;

import dev.sndy.spectre.item.Resource;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class Reference {

    public static final Resource NULL_RESOURCE = new Resource("Null", 0, Material.BARRIER, "", false);
    public static final Location SPAWN_LOCATION = new Location(Bukkit.getWorld("Main_World"), 175.5, 73, -731.5);
    public static final String JOIN_MESSAGE = "§e[name] §fhas joined the server!";
    public static final String SWITCH_PROFILE_MESSAGE = "§fSwitched to profile §e[profile]§f!";
    public static final String CREATE_PROFILE_MESSAGE = "§fCreated profile §e[profile]§f!";
    public static final String DELETE_PROFILE_MESSAGE = "§cDeleted profile §e[profile]§c!";
    public static final String LEVEL_UP_MESSAGE = "§fYou just leveled up to level §e§l[level]§f!";

}
