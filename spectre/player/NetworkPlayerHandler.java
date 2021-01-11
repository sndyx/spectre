package com.sndy.spectre.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NetworkPlayerHandler {

    public static final HashMap<UUID, NetworkPlayer> players = new HashMap<>();

    /**
     * Returns a NetworkPlayer object from a
     * Bukkit Player object.
     *
     * @param player Player
     * @return       NetworkPlayer
     */
    public static NetworkPlayer getPlayer(Player player){
        return players.get(player.getUniqueId());
    }

    /**
     * Returns a NetworkPlayer from a UUID.
     *
     * @param UUID UUID
     * @return     NetworkPlayer
     */
    public static NetworkPlayer getPlayer(UUID UUID){
        return players.get(UUID);
    }

    /**
     * Returns a NetworkPlayer from String UUID.
     *
     * ## UUID MUST BE FORMATTED AS SO: #######
     * bf8c0810-3dda-48ec-a573-43e162c0e79a
     * ## AND NOT THE FOLLOWING: ##############
     * bf8c08103dda48eca57343e162c0e79a
     *
     * @param UUID UUID String
     * @return     NetworkPlayer
     */
    public static NetworkPlayer getPlayer(String UUID){
        return players.get(java.util.UUID.fromString(UUID));
    }

    /**
     * Checks if a player has joined the server.
     *
     * @param UUID UUID
     * @return     HasJoined
     */
    public static boolean hasPlayer(UUID UUID){
        return players.containsKey(UUID);
    }

    /**
     * Override's a players data.
     *
     * @param player        Player
     * @param networkPlayer Network Player
     */
    public static void overridePlayer(Player player, NetworkPlayer networkPlayer){
        players.put(player.getUniqueId(), networkPlayer);
    }

    /**
     * Removes a player from the map.
     *
     * @param player Player
     */
    public static void removePlayer(Player player){
        players.remove(player.getUniqueId());
    }

    /**
     * Gets a player UUID by their username.
     * Player does not have to be online.
     *
     * ## RETURNS NULL IF PLAYER DOES NOT
     * ## EXIST, HAS NOT JOINED THE SERVER,
     * ## OR IS NOT IN THE HASHMAP.
     *
     * @param username Username
     * @return         NetworkPlayer
     */
    public static UUID getNetworkPlayerUUID(String username){
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
        if (offlinePlayer.hasPlayedBefore() && hasPlayer(offlinePlayer.getUniqueId())) {
            return offlinePlayer.getUniqueId();
        } else {
            return null;
        }
    }

    /**
     * Returns a skull with the player's face
     * on it!
     *
     * @param UUID Player UUID
     * @param name Name Of Item
     * @param lore Lore
     * @return Player Skull
     */
    public static ItemStack getPlayerSkull(UUID UUID, String name, List<String> lore){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD,1,(short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta)head.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID));
        meta.setLore(lore);
        meta.setDisplayName(name);
        head.setItemMeta(meta);
        return head;
    }

    /**
     * Gets a player skull from a UUID.
     *
     * @param UUID Player UUID
     * @param name Name Of Item
     * @return Player Skull
     */
    public static ItemStack getPlayerSkull(UUID UUID, String name){
        ArrayList<String> lore = new ArrayList<>();
        return getPlayerSkull(UUID, name, lore);
    }

}
