package dev.sndy.spectre.player;

import dev.sndy.spectre.Reference;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;

public class FileManager implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        loadPlayer(player);
        event.setJoinMessage(Reference.JOIN_MESSAGE.replace("[name]", event.getPlayer().getName()));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        savePlayer(event.getPlayer());
        NetworkPlayerHandler.removePlayer(event.getPlayer());
    }

    /**
     * Save player's UserMeta.
     *
     * @param player player
     */
    public static void savePlayer(Player player) {
        try {
            File file = new File("rpg/players/" + player.getUniqueId().toString() + ".dat");
            if(!file.exists()) file.createNewFile();
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new FileOutputStream(file));
            out.writeObject(NetworkPlayerHandler.getPlayer(player.getUniqueId()).meta);
        } catch(IOException e){e.printStackTrace();}
    }

    /**
     * Loads a player from the save files.
     *
     * @param player player
     */
    public void loadPlayer(Player player){
        String UUID = player.getUniqueId().toString();
        File file = new File("rpg/players/" + UUID + ".dat");
        if(file.exists()){
            try {
                BukkitObjectInputStream in = new BukkitObjectInputStream(new FileInputStream(file));
                NetworkPlayerHandler.players.put(player.getUniqueId(), new NetworkPlayer(player, (NetworkPlayerMeta)in.readObject()));
            } catch(IOException | ClassNotFoundException e) { e.printStackTrace();}
        }
        else{
            NetworkPlayerMeta meta = new NetworkPlayerMeta(player);
            NetworkPlayerHandler.overridePlayer(player, new NetworkPlayer(player, meta));
        }
    }

}