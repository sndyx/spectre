package dev.sndy.spectre;

import dev.sndy.spectre.commands.*;
import dev.sndy.spectre.event.EventManager;
import dev.sndy.spectre.event.EverySecondEvent;
import dev.sndy.spectre.gui.ChatManager;
import dev.sndy.spectre.gui.GuiManager;
import dev.sndy.spectre.item.ItemRegistry;
import dev.sndy.spectre.item.Set;
import dev.sndy.spectre.player.FileManager;
import dev.sndy.spectre.player.NetworkPlayer;
import dev.sndy.spectre.player.NetworkPlayerHandler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.Objects;

public final class Spectre extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(Bukkit.getPluginCommand("setrank")).setExecutor(new SetRank());
        Objects.requireNonNull(Bukkit.getPluginCommand("items")).setExecutor(new Items());
        Objects.requireNonNull(Bukkit.getPluginCommand("profile")).setExecutor(new Profile());
        Objects.requireNonNull(Bukkit.getPluginCommand("profiles")).setExecutor(new Profiles());
        Objects.requireNonNull(Bukkit.getPluginCommand("addexperience")).setExecutor(new AddExperience());
        getServer().getPluginManager().registerEvents(new FileManager(), this);
        getServer().getPluginManager().registerEvents(new EventManager(), this);
        getServer().getPluginManager().registerEvents(new ChatManager(), this);
        getServer().getPluginManager().registerEvents(new GuiManager(), this);
        ItemRegistry.parseItems();
        tick();
    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()){
            FileManager.savePlayer(player);
        }
    }

    public void tick(){
        new BukkitRunnable(){
            public void run(){
                for(NetworkPlayer player : NetworkPlayerHandler.players.values()){
                    player.updateStats();
                    double maxMana = player.stats.getStats()[1].value + player.getProfile().stats.getStats()[1].value;
                    player.mana += 0.5 + maxMana / 1000;
                    if(player.data.experienceTurns == 0) {
                        player.data.addedExperience = 0;
                        if (player.mana > maxMana) {
                            player.mana = maxMana;
                        }
                        double maxHealth = player.stats.getStats()[0].value + player.getProfile().stats.getStats()[0].value;
                        player.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b" + new DecimalFormat("#.##").format(player.mana) + "/" + new DecimalFormat("#.##").format(maxMana) + " Mana §f/ §a" + new DecimalFormat("#.##").format(player.health) + "/" + new DecimalFormat("#.##").format(maxHealth) + " Health"));
                    }
                    else{
                        player.data.experienceTurns -= 1;
                        double experience = player.data.addedExperience;
                        player.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+" + experience + " EXP §fGained §e| §a" + new DecimalFormat("#.##").format(player.getProfile().experience) + "/" + new DecimalFormat("#.##").format(player.getExperienceNeeded()) + " EXP"));
                    }
                }
            }
        }.runTaskTimer(this, 0L, 10L);
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        EventManager.invokeEvent(new EverySecondEvent(player, new Set(player.getInventory())), EverySecondEvent.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.runTaskTimer(this, 0L, 20L);
    }

}
