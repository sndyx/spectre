package com.sndy.spectre.event.sets;

import com.sndy.spectre.event.SetBase;
import com.sndy.spectre.event.events.RightClickEvent;
import com.sndy.spectre.main.Spectre;
import com.sndy.spectre.player.stat.Rarity;
import com.sndy.spectre.player.stat.StatBuilder;
import com.sndy.spectre.player.stat.StatType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class SetThunderStaff extends SetBase {

    Spectre rpg;

    public SetThunderStaff(Spectre rpg){
        this.rpg = rpg;
    }

    public void rightClickEvent(RightClickEvent event) {
        Location loc = event.getPlayer().rayTraceBlocks(50).getHitBlock().getLocation();
        try {
            ArrayList<Vector> points = new ArrayList<>();
            for (double i = 0; i < 2 * Math.PI; i += 0.10) {
                double x = Math.cos(i) * 10;
                double z = Math.sin(i) * 10;
                points.add(new Vector(loc.getX() + x, 0, loc.getZ() + z));
                Objects.requireNonNull(loc.getWorld()).spawnParticle(Particle.FLAME, loc.getX() + x, loc.getY() + 2, loc.getZ() + z, 5);
                Objects.requireNonNull(loc.getWorld()).spawnParticle(Particle.FIREWORKS_SPARK, loc.getX() + x, loc.getY() + 2, loc.getZ() + z, 5);
                for (Vector point : points) {
                    Objects.requireNonNull(loc.getWorld()).spawnParticle(Particle.FIREWORKS_SPARK, point.getX(), loc.getY() + 2, point.getZ(), 0, 0, 0, 0, 1);
                }
                Thread.sleep(50);
            }
            Thread.sleep(1000);
            Random rndGen = new Random();
            for (int count = 0; count < 50; count++) {
                int r = rndGen.nextInt(9) + 1;
                int x = rndGen.nextInt(r);
                int z = (int) Math.sqrt(Math.pow(r, 2) - Math.pow(x, 2));
                if (rndGen.nextBoolean()) {
                    x *= -1;
                }
                if (rndGen.nextBoolean()) {
                    z *= -1;
                }
                Location strikeLoc = new Location(loc.getWorld(), x + loc.getX(), loc.getY(), z + loc.getZ());
                while(Objects.requireNonNull(strikeLoc.getWorld()).getBlockAt(strikeLoc).isEmpty() && strikeLoc.getY() > 0){
                    strikeLoc.add(0, -1, 0);
                }
                int finalX = (int)strikeLoc.getX();
                int finalZ = (int)strikeLoc.getZ();
                Bukkit.getScheduler().scheduleSyncDelayedTask(rpg, () -> loc.getWorld().strikeLightning(new Location(loc.getWorld(), finalX, strikeLoc.getY(), finalZ)));
                Thread.sleep(50);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
