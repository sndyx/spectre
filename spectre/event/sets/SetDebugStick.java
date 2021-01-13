package com.sndy.spectre.event.sets;

import com.sndy.spectre.event.SetBase;
import com.sndy.spectre.event.events.LeftClickEvent;
import com.sndy.spectre.event.events.RightClickEvent;
import com.sndy.spectre.player.stat.Rarity;
import com.sndy.spectre.player.stat.StatBuilder;
import com.sndy.spectre.player.stat.StatType;
import org.bukkit.Material;

public class SetDebugStick extends SetBase {

    @Override
    public void rightClickEvent(RightClickEvent event) {
        event.getPlayer().getInventory().setItemInOffHand(StatBuilder.toItem("Sandy's Legendary Debug Sword", Material.DIAMOND_SWORD, new StatBuilder()
                .add(StatType.DAMAGE_ALL, 10)
                .add(StatType.DAMAGE_ALL, 11.111111f)
                .add(StatType.DAMAGE_ALL, 101131313.1111f)
                .add(StatType.DAMAGE_ALL, 11.113832f)
                .add(StatType.DAMAGE_ALL, 10.3292f)
                .add(StatType.DAMAGE_ALL, 10.0400420f)
                .add(StatType.DAMAGE_ALL, 10.00320f)
                .add(StatType.DAMAGE_ALL, 10.353f)
                .add(StatType.SKILL_WISDOM, 1000)
                .add(StatType.SKILL_STRENGTH, 10)
                .add(StatType.DEFENSE_ULTIMATE, 100)
                .add(StatType.DAMAGE_STEAL, 10).build(), Rarity.LEGENDARY, "Sandy's legendary testing sword!"));
    }

    @Override
    public void leftClickEvent(LeftClickEvent event) {
        event.getPlayer().getInventory().setItemInOffHand(StatBuilder.toItem("Sandy's Legendary Debug Sword", Material.DIAMOND_SWORD, new StatBuilder()
                .add(StatType.DAMAGE_ALL, 10)
                .add(StatType.SKILL_WISDOM, 1000)
                .add(StatType.SKILL_STRENGTH, 10)
                .add(StatType.DEFENSE_ULTIMATE, 100)
                .add(StatType.DAMAGE_STEAL, 10).build(), Rarity.LEGENDARY, "Sandy's legendary testing sword but the description is overly long and very boring to read. This is because one of my tests pertains to making sure the descriptions work."));
    }

}
