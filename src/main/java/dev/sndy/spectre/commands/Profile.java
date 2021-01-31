package dev.sndy.spectre.commands;

import dev.sndy.spectre.player.NetworkPlayerHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Profile implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if (args.length == 1) {
                try {
                    int profile = Integer.parseInt(args[0]);
                    if(profile > 0 && profile < 4)
                    NetworkPlayerHandler.getPlayer(((Player)sender).getUniqueId()).switchProfile(profile - 1);
                    NetworkPlayerHandler.getPlayer(((Player)sender).getUniqueId()).syncWithProfile();
                } catch (IllegalArgumentException e) {
                    sender.sendMessage("§cProfile must be a valid number from 1-3!");
                }
            }
        }
        return true;
    }

}
