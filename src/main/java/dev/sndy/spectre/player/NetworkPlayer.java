package dev.sndy.spectre.player;

import dev.sndy.spectre.Reference;
import dev.sndy.spectre.item.Equipment;
import dev.sndy.spectre.item.IMaterial;
import dev.sndy.spectre.item.Items;
import dev.sndy.spectre.item.stat.StatList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NetworkPlayer {

    public StatList stats = new StatList();
    public NetworkPlayerMeta meta;
    public Player player;
    public double mana = 0;
    public double health = 0;
    public RuntimeData data = new RuntimeData();

    public NetworkPlayer(Player player, NetworkPlayerMeta meta){
        this.player = player;
        this.meta = meta;
    }

    public NetworkPlayerProfile getProfile(){
        return meta.profiles[meta.currentProfile];
    }

    public void switchProfile(int profile){
        getProfile().update(player);
        meta.currentProfile = profile;
    }

    /**
     * Updates the player's stats.
     */
    public void updateStats(){
        stats = new StatList();
        IMaterial hand = Items.fromItemStack(player.getInventory().getItemInMainHand());
        if(hand instanceof Equipment){
            stats = ((Equipment)hand).stats;
        }
        for (ItemStack item : player.getInventory().getArmorContents()) {
            IMaterial armor = Items.fromItemStack(item);
            if(armor instanceof Equipment){
                stats.merge(((Equipment)armor).stats);
            }
        }
        health = (stats.getStats()[0].value + getProfile().stats.getStats()[0].value) * (player.getHealth() / 20);
    }

    /**
     * Syncs the player with their currently active
     * profile.
     */
    public void syncWithProfile(){
        player.teleport(getProfile().location);
        player.getInventory().setContents(getProfile().inventory);
    }

    /**
     * Returns the amount of experience a player
     * needs to advance to the next level.
     *
     * @return experience needed
     */
    public double getExperienceNeeded(){
        return (int)(63 * ((double)getProfile().level / ((double)getProfile().level + 50))) * 100;
    }

    /**
     * Adds experience to the player on their
     * active profile.
     *
     * @param experience experience given
     */
    public void addExperience(double experience){
        getProfile().experience += experience;
        while(getProfile().experience > getExperienceNeeded()){
            getProfile().experience -= getExperienceNeeded();
            getProfile().level += 1;
            player.sendMessage("");
            player.sendMessage(Reference.LEVEL_UP_MESSAGE.replace("[level]", String.valueOf(getProfile().level)));
            player.sendMessage("");
        }
        data.addedExperience += experience;
        data.experienceTurns = 4;
    }

}
