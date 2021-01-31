package dev.sndy.spectre.commands;

import com.sun.istack.internal.NotNull;
import dev.sndy.spectre.gui.GuiEnum;
import dev.sndy.spectre.gui.GuiManager;
import dev.sndy.spectre.item.ItemRegistry;
import dev.sndy.spectre.player.NetworkPlayerHandler;
import dev.sndy.spectre.player.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Items implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player) {
            if(args.length == 0) {
                GuiManager.openGui(GuiEnum.ITEM_GUI, (Player) sender);
            }
            else if(args.length == 1 && args[0].equals("reload")){
                if(NetworkPlayerHandler.getPlayer(((Player) sender).getUniqueId()).meta.rank.equals(Rank.ADMIN)) {
                    ItemRegistry.registry.clear();
                    ItemRegistry.parseItems();
                    sender.sendMessage("§fItems reloaded!");
                }
            }
        }
        return true;
    }

}
