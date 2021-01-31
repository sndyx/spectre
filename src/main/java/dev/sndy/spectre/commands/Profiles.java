package dev.sndy.spectre.commands;

import dev.sndy.spectre.gui.GuiEnum;
import dev.sndy.spectre.gui.GuiManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Profiles implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player && args.length == 0){
            GuiManager.openGui(GuiEnum.PROFILES_GUI, (Player)sender);
        }
        return true;
    }
}
