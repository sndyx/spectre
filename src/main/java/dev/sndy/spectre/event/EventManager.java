package dev.sndy.spectre.event;

import dev.sndy.spectre.event.events.SetBase;
import dev.sndy.spectre.item.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventManager implements Listener {

    static ArrayList<Object> registry = new ArrayList<>();

    /**
     * When given a class type, attempts to locate a class within
     * HashMap "registered" that has the key of the item stored within
     * Object "c". When successful, it scans all methods within the
     * class and attempts to locate ones with the extending {@link SetBase}.
     * All methods extending superclass {@link SetBase} are further narrowed
     * down t only methods containing parameter c(class). If it finds any,
     * it invokes said class (firing the event) and then checks if the
     * event is cancelled. If so, returns false. otherwise, always true.
     *
     * @param c Event Class
     * @return isCancelled
     */
    public static boolean invokeEvent(Object c, Class<?> type) {
        Event event = (Event) c;
        for (Object itemSet : registry) {
            if (((SetBase)itemSet).getSet().isMatch(event.getSet())) {
                Class<?> registeredClass = itemSet.getClass();
                if (registeredClass.getSuperclass().equals(SetBase.class)) {
                    for (Method method : registeredClass.getDeclaredMethods()) {
                        try {
                            if (method.getParameterTypes().length == 1 && method.getParameterTypes()[0].equals(type)) {
                                Runnable invoke = () -> {
                                    try {
                                        method.invoke(itemSet, c);
                                    } catch (IllegalAccessException | InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                };
                                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                                executor.schedule(invoke, 0, TimeUnit.SECONDS);
                                if (event.isCancelled()) {
                                    return true;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Failed call of event: " + c);
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }

    public static class Events {

        public static void register(Object c) {
            if(c.getClass().getSuperclass().equals(SetBase.class)) {
                registry.add(c);
            }
        }

    }

    /**
     * Handles detection of right clicks and left clicks regardless
     * of target or type. Calls LeftClickEvent and RightClickEvent. the
     * SwingEvent is cancelable.
     *
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (invokeEvent(new LeftClickEvent(event.getPlayer(), new Set(event.getPlayer().getInventory())), LeftClickEvent.class)) {
                event.setCancelled(true);
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (invokeEvent(new RightClickEvent(event.getPlayer(), new Set(event.getPlayer().getInventory())), RightClickEvent.class)) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * Handles detection of dropped items and fires event
     * DropEvent if it finds a specific item being
     * dropped. Can be cancelled.
     *
     * @param event PlayerDropItemEvent
     */
    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if (invokeEvent(new DropEvent(event.getPlayer(), new Set(new Set.Sword(Objects.requireNonNull(event.getItemDrop().getItemStack().getItemMeta()).getDisplayName()))), DropEvent.class)) {
            event.setCancelled(true);
        }
    }

    /**
     * Handles detection of item consuming and fires event
     * ConsumeEvent if it finds a specific item being
     * consumed. Can be cancelled.
     *
     * @param event PlayerItemConsumeEvent
     */
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (invokeEvent(new ConsumeEvent(event.getPlayer(), new Set(new Set.Sword(Objects.requireNonNull(event.getItem().getItemMeta()).getDisplayName()))), ConsumeEvent.class)) {
            event.setCancelled(true);
        }
    }

    /**
     * Handles detection of players damaging other players
     * and fires DamagedByPlayerEvent if it finds a
     * specific item being held whilst taking damage or
     * a piece of armor being worn. Is cancellable.
     *
     * @param event EntityDamageByEntityEvent
     */
    @EventHandler
    public void onDamageTakenByPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            DamagedByPlayerEvent dmgEvent = new DamagedByPlayerEvent((Player) event.getEntity(), (Player) event.getDamager(), new Set(((Player) event.getEntity()).getInventory()), event.getDamage());
            if (invokeEvent(dmgEvent, DamagedByPlayerEvent.class)) {
                event.setCancelled(true);
            } else if (dmgEvent.died && !event.isCancelled()) {
                if (invokeEvent(new DeathByPlayerEvent((Player) event.getEntity(), new Set(((Player) event.getEntity()).getInventory()), (Player) event.getDamager()), DeathByPlayerEvent.class)) {
                    event.setCancelled(true);
                }
            }
        }
        if (event.getDamager() instanceof Player) {
            if (invokeEvent(new HitEntityEvent((Player) event.getDamager(), event.getEntity(), new Set(((Player) event.getDamager()).getInventory()), event.getDamage()), HitEntityEvent.class)) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * Handles detection of any damage taken from any source
     * and fires DamagedEvent if it finds a specific
     * item being held or worn whilst specified player is
     * taking damage. Is Cancellable.
     *
     * @param event EntityDamageEvent
     */
    @EventHandler
    public void onDamageTaken(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            DamagedEvent dmgEvent = new DamagedEvent((Player) event.getEntity(), new Set(((Player) event.getEntity()).getInventory()), event.getCause(), event.getFinalDamage());
            if (invokeEvent(dmgEvent, DamagedEvent.class)) {
                event.setCancelled(true);
            } else if (dmgEvent.died && !event.isCancelled()) {
                if (invokeEvent(new DeathEvent((Player) event.getEntity(), new Set(((Player) event.getEntity()).getInventory()), event.getCause()), DeathEvent.class)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
