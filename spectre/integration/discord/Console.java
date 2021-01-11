package com.sndy.spectre.integration.discord;

import com.sndy.spectre.main.Spectre;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Console extends ListenerAdapter {

    private boolean initialized = false;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(event.getChannel().getId().equals(Channels.CONSOLE)) {
            String message = event.getMessage().getContentRaw();
            if(message.startsWith("/")){
                if(message.equals("/init")){
                    if(!initialized) {
                        init();
                        initialized = true;
                        EmbedBuilder embed = new EmbedBuilder();
                        embed.setTitle("Console Initialized.");
                        embed.setDescription("Server console feed will now be rerouted here.");
                        embed.setColor(Integer.parseInt("00C09A", 16));
                        Objects.requireNonNull(SpectreBot.getContext().getTextChannelById(Channels.CONSOLE)).sendMessage(embed.build()).queue();
                    }
                    else{
                        EmbedBuilder embed = new EmbedBuilder();
                        embed.setTitle("Console Already Initialized.");
                        embed.setDescription("Command failed because console is already initialized.");
                        embed.setColor(Integer.parseInt("ff3f65", 16));
                        Objects.requireNonNull(SpectreBot.getContext().getTextChannelById(Channels.CONSOLE)).sendMessage(embed.build()).queue();
                    }
                }
                else {
                    if(initialized) {
                        try {
                            event.getMessage().delete().queue();
                            EmbedBuilder embed = new EmbedBuilder();
                            embed.setDescription("[" + event.getAuthor().getName() + "] Scheduling server command <" + event.getMessage().getContentRaw() + ">");
                            embed.setColor(Integer.parseInt("00C09A", 16));
                            Objects.requireNonNull(SpectreBot.getContext().getTextChannelById(Channels.CONSOLE)).sendMessage(embed.build()).queue();
                            Bukkit.getScheduler().callSyncMethod(Spectre.getProvidingPlugin(getClass()), () -> Bukkit.dispatchCommand(Spectre.console, message.substring(1))).get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        EmbedBuilder embed = new EmbedBuilder();
                        embed.setTitle("Console Not Initialized.");
                        embed.setDescription("Run /init to initialize the console.");
                        embed.setColor(Integer.parseInt("ff3f65", 16));
                        Objects.requireNonNull(SpectreBot.getContext().getTextChannelById(Channels.CONSOLE)).sendMessage(embed.build()).queue();
                    }
                }
            }
        }
    }

    public static void init() {
       Spectre.logger.addAppender(new Appender());
    }
}