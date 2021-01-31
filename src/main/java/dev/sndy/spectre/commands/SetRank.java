package dev.sndy.spectre.commands;

import com.sun.istack.internal.NotNull;
import dev.sndy.spectre.player.NetworkPlayerHandler;
import dev.sndy.spectre.player.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetRank implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 2) {
            if (NetworkPlayerHandler.getNetworkPlayerUUID(strings[0]) != null) {
                if (commandSender instanceof Player && NetworkPlayerHandler.getPlayer(((Player) commandSender).getUniqueId()).meta.rank == Rank.ADMIN || commandSender instanceof ConsoleCommandSender) {
                    Rank rank;
                    switch (strings[1].toLowerCase()) {
                        case "default":
                            rank = Rank.DEFAULT;
                            break;
                        case "admin":
                            rank = Rank.ADMIN;
                            break;
                        case "builder":
                            rank = Rank.BUILDER;
                            break;
                        case "moderator":
                            rank = Rank.MODERATOR;
                            break;
                        case "helper":
                            rank = Rank.HELPER;
                            break;
                        default:
                            commandSender.sendMessage("§cInvalid Rank.");
                            return true;
                    }
                    NetworkPlayerHandler.getPlayer(NetworkPlayerHandler.getNetworkPlayerUUID(strings[0])).meta.rank = rank;
                    commandSender.sendMessage("§aSet " + strings[0] + "'s rank to: " + strings[1] + "!");
                }
                else{
                    commandSender.sendMessage("§cYou do not have permission to do run this command!");
                }
            }
            else{
                commandSender.sendMessage("§cPlayer either doesn't exist or has never joined the server!");
            }
        }
        else{
            commandSender.sendMessage("§cInvalid syntax. /setrank <player> <rank>!");
        }
        return true;
    }

}
