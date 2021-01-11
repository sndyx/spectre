package com.sndy.spectre.player;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManager implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        loadPlayer(player);
        event.setJoinMessage(Messages.JOIN_MESSAGE.replace("[name]", event.getPlayer().getName()));
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
    public void savePlayer(Player player) {
        try { ArrayList<String> lines = new ArrayList<>();
            Gson gson = new Gson();
            lines.add(gson.toJson(NetworkPlayerHandler.getPlayer(player).meta));
            Path file = Paths.get("rpg/players/" + player.getUniqueId().toString() + ".json");
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch(IOException e){e.printStackTrace();}
    }

    /**
     * Loads a player from the save files.
     *
     * @param player player
     */
    public void loadPlayer(Player player){
        String UUID = player.getUniqueId().toString();
        File file = new File("rpg/players/" + UUID + ".json");
        if(file.exists()){
            try { JsonElement read = new JsonParser().parse(new FileReader(file));
                NetworkPlayerMeta meta = new Gson().fromJson(read, NetworkPlayerMeta.class);
                NetworkPlayerHandler.overridePlayer(player, new NetworkPlayer(player, meta));
            } catch(FileNotFoundException e) { e.printStackTrace();}
        }
        else{
            NetworkPlayerMeta userMeta = new NetworkPlayerMeta();
            NetworkPlayerHandler.overridePlayer(player, new NetworkPlayer(player, userMeta));
        }
    }

}
