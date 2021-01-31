package dev.sndy.spectre.commands;

import dev.sndy.spectre.player.NetworkPlayerHandler;
import dev.sndy.spectre.player.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddExperience implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if (NetworkPlayerHandler.getPlayer(((Player) sender).getUniqueId()).meta.rank.equals(Rank.ADMIN)) {
                if (args.length == 2) {
                    if (NetworkPlayerHandler.hasPlayer(NetworkPlayerHandler.getNetworkPlayerUUID(args[0]))) {
                        if (isDouble(args[1])) {
                            NetworkPlayerHandler.getPlayer(NetworkPlayerHandler.getNetworkPlayerUUID(args[0])).addExperience(new Double(args[1]));
                            sender.sendMessage("§fGave §e§l" + args[0] + " §e" + args[1] + " §fexperience!");
                        } else {
                            sender.sendMessage("§cInvalid syntax. do /addexperience <player> <experience>!");
                        }
                    } else {
                        sender.sendMessage("§cThat player is not online!");
                    }
                } else if (args.length == 1 && isDouble(args[0])) {
                    NetworkPlayerHandler.getPlayer(((Player) sender).getUniqueId()).addExperience(new Double(args[0]));
                    sender.sendMessage("§fGave you §e" + args[0] + " §fexperience!");
                } else {
                    sender.sendMessage("§cInvalid syntax. do /addexperience <player> <experience>!");
                }
            }
            else{
                sender.sendMessage("§cYou do not have permission to do this!");
            }
        }
        return true;
    }

    private boolean isDouble(String arg){
        try{
            Double.valueOf(arg);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }

}
