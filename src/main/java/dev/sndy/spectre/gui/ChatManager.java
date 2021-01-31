package dev.sndy.spectre.gui;

import dev.sndy.spectre.player.NetworkPlayerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        String rank;
        switch(NetworkPlayerHandler.getPlayer(event.getPlayer().getUniqueId()).meta.rank){
            case ADMIN:
                rank = "§c§lADMIN §c";
                break;
            case BUILDER:
                rank = "§9§lBUILDER §9";
                break;
            case MODERATOR:
                rank = "§2§lMOD §2";
                break;
            case HELPER:
                rank = "§3§lHELPER §3";
                break;
            default:
                rank = "";
                break;
        }
        event.setFormat(rank + event.getPlayer().getName() + " §8§l> §f%2$s");
    }

}
