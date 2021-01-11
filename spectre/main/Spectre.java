package com.sndy.spectre.main;

import com.sndy.spectre.event.EventManager;
import com.sndy.spectre.event.MobRegistry;
import com.sndy.spectre.event.Set;
import com.sndy.spectre.event.sets.*;
import com.sndy.spectre.integration.discord.SpectreBot;
import com.sndy.spectre.player.FileManager;
import com.sndy.spectre.player.NetworkPlayer;
import com.sndy.spectre.player.NetworkPlayerHandler;
import com.sndy.spectre.player.interaction.InteractionManager;
import com.sndy.spectre.player.stat.StatBuilder;
import com.sndy.spectre.player.stat.StatType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.security.auth.login.LoginException;

public final class Spectre extends JavaPlugin implements Listener {

    public static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
    public static final Logger logger = (Logger)LogManager.getRootLogger();

    @Override
    public void onEnable() {
        try {
            SpectreBot.initBot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        registerSets();
        registerMobs();
        registerListeners();
        everyTick();
    }

    @Override
    public void onDisable() {

    }

    public void registerSets(){
        EventManager.Events.register(
                new Set(
                        new Set.Sword("Thunder Staff")),
                new SetThunderStaff(this));
    }

    public void registerMobs(){
        MobRegistry.register(new StatBuilder().add(StatType.DAMAGE_ALL, 2, 50).build(), "Skeleton", "Zombie");
    }

    public void registerListeners(){
        getServer().getPluginManager().registerEvents(new EventManager(), this);
        getServer().getPluginManager().registerEvents(new FileManager(), this);
        getServer().getPluginManager().registerEvents(new InteractionManager(), this);
    }

    public void everyTick(){
        new BukkitRunnable(){
            public void run(){
                for(NetworkPlayer player : NetworkPlayerHandler.players.values()){
                    player.mana += 0.1;
                    if(player.mana > 20){
                        player.mana = 20;
                    }
                }
            }
        }.runTaskTimer(this, 0L, 1L);
    }

}
