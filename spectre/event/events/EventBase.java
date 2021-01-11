package com.sndy.spectre.event.events;

import com.sndy.spectre.event.Set;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventBase {

    Set itemSet;
    Player player;
    public Player getPlayer() {
        return player;
    }
    public Location playerPos(){
        return player.getLocation();
    }

    private boolean cancelled = false;
    public void cancel(){
        cancelled = true;
    }

    public boolean isCancelled(){
        return cancelled;
    }

    /**
     * Spawns particle(s) where the specified.
     *
     * @param particle Particle Type
     * @param loc      Location
     * @param count    Count
     */
    public void spawnParticle(Particle particle, Location loc, int count){
        player.getWorld().spawnParticle(particle, loc, count);
    }

    /**
     * Spawns a helix of particles from specified particle
     * type, location, and radius.
     *
     * MultiThreaded
     *
     * @param particle Particle Type
     * @param loc      Location
     * @param r   Radius
     */
    public void spawnParticleHelix(Particle particle, Location loc, double r, int d) {
        Runnable run = new Runnable() {
            double radius = r;
            int delay = d;
            @Override
            public void run() {
                for (double y = 5; y >= 0; y -= 0.05) {
                    radius = y / 3;
                    double x = radius * Math.cos(3 * y);
                    double z = radius * Math.sin(3 * y);

                    double y2 = 5 - y;

                    Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
                    player.getWorld().spawnParticle(particle, loc2, 0, 0, 0, 0, 1);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (double y = 5; y >= 0; y -= 0.05) {
                    radius = y / 3;
                    double x = -(radius * Math.cos(3 * y));
                    double z = -(radius * Math.sin(3 * y));

                    double y2 = 5 - y;

                    Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
                    player.getWorld().spawnParticle(particle, loc2, 0, 0, 0, 0, 1);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.execute(run);
    }

    /**
     * Spawns a sphere of particles where specified, with
     * a particular radius.
     *
     * Multithreaded.
     *
     * @param particle Particle Type
     * @param l        Location
     * @param r        Radius
     */

    public void spawnParticleSphere(Particle particle, Location l, double r, int delay){
        Runnable run = new Runnable() {
            @Override
            public void run() {
                for (double phi = 0; phi <= Math.PI; phi += Math.PI / 15) {
                    double y = r * Math.cos(phi) + 1.5;
                    for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 30) {
                        double x = r * Math.cos(theta) * Math.sin(phi);
                        double z = r * Math.sin(theta) * Math.sin(phi);
                        l.add(x, y, z);
                        Objects.requireNonNull(l.getWorld()).spawnParticle(particle, l, 1, 0F, 0F, 0F, 0.001);
                        l.subtract(x, y, z);
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        ExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.execute(run);
    }

    /**
     * Spawns a 2d particle circle at a specified location
     * and density.
     *
     * @param particle Particle Type
     * @param loc      Location
     * @param scaleX   ScaleX
     * @param scaleY   ScaleY
     * @param density  Density
     */
    public void spawnParticleCircle(Particle particle, Location loc, int scaleX, int scaleY, double density){
        for (double i=0; i < 2 * Math.PI ; i +=density) {
            double x = Math.cos(i) * scaleX;
            double y = Math.sin(i) * scaleY;
            Objects.requireNonNull(loc.getWorld()).spawnParticle(particle, x, y, loc.getZ(), 1);
        }
    }

    public void playSound(Sound sound, Location location, float volume, float pitch){
        player.getWorld().playSound(location, sound, volume, pitch);
    }

    public Set getSet(){
        return itemSet;
    }

}
