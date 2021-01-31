package dev.sndy.spectre.event.events;

import dev.sndy.spectre.event.*;
import dev.sndy.spectre.item.Set;

/**
 * @author Sandy
 *
 * Event classes must extend this class to be picked
 * up by {@link Event}. Override methods are
 * all Spectre event classes.
 * <p>
 * {@link Event} is the generic event class
 * extended by all other event classes.
 * <p>
 * {@link Event} contains the set equipped
 * {@link Event#getSet()} and the player
 * {@link Event#getPlayer()}
 * <p>
 * All events can be cancelled by using
 * {@link Event#setCancelled(boolean)} ()}. Cancelling an event
 * such as {@link DamagedByPlayerEvent} will also
 * cancel a future {@link DeathByPlayerEvent} if
 * the damage taken was enough to kill the player.
 * <p>
 * An example of a set:
 * <code>
 *    public class ExampleSet extends SetBase{
 *       @ Override
 *       public void damagedByPlayerEvent(DamagedByPlayerEvent event){
 *           if(event.getDamage() > 5){
 *               event.cancel();
 *               event.getDamager().damage(10000);
 *           }
 *       }
 *    }
 * </code>
 * <p>
 * To register your new set, use
 * {@link EventManager.Events#register(Set, Object)}.
 * with {@link Set} being a new Set object
 * containing your specified item names, and
 * {@link Object} being the set class.
 * An example of registering a set:
 * <code>
 *    EventManager.Events.register(
 *         new Set(
 *         new Set.Sword("Sandy's Sword!")),
 *         new ExampleSet());
 * </code>
 */
public abstract class SetBase implements ISetBase {

    /**
     * Called when the player consumes an item
     * while the specified set is equipped.
     * <p>
     * {@link Set.Sword} is consumed item.
     * Consumed item is included in set as sword.
     *
     * @param event generic event {@link Event}
     */
    public void onConsume(ConsumeEvent event){}

    /**
     * Called when the player takes damage
     * from another player while the specified
     * set is equipped.
     * <p>
     * Called alongside {@link DamagedEvent}
     *
     * @param event event containing the damage
     *              {@link DamagedByPlayerEvent#getDamage()}
     *              and the damage damaging player
     *              {@link DamagedByPlayerEvent#getDamager()} ()}
     */
    public void damagedByPlayerEvent(DamagedByPlayerEvent event){}

    /**
     * Called when the player takes damage
     * while the specified set is equipped.
     *
     * @param event event containing the damage
     *              {@link DamagedEvent#getDamage()}
     *              and the damage cause
     *              {@link DamagedEvent#getDamageCause()}
     */
    public void damagedEvent(DamagedEvent event){}

    /**
     * Called when a player takes dies while the
     * specified set is equipped.
     * <p>
     * Called alongside {@link DamagedEvent}.
     *
     * @param event event containing the killer
     *              {@link DeathByPlayerEvent#getKiller()}
     */
    public void deathByPlayerEvent(DeathByPlayerEvent event){}

    /**
     * Called when a player dies and is wearing
     * the specified set.
     * <p>
     * Called alongside {@link DamagedEvent}.
     *
     * @param event event containing death cause
     *              {@link DeathEvent#getCause()}
     */
    public void deathEvent(DeathEvent event){}

    /**
     * Called when a player drops an item on the
     * ground while the specified set is equipped.
     * <p>
     * {@link Set.Sword} is dropped item. Dropped
     * item is included in set as sword.
     *
     * @param event generic event {@link Event}
     */
    public void dropEvent(DropEvent event){}

    /**
     * Called every second while the specified set
     * is equipped.
     *
     * @param event generic event {@link Event}
     */
    public void everySecondEvent(EverySecondEvent event){}

    /**
     * Called when a player hits an entity while the
     * specified set is equipped.
     * <p>
     * Called alongside {@link LeftClickEvent}.
     *
     * @param event event containing damage dealt
     *              {@link HitEntityEvent#getDamage()}
     *              and the entity that was hit
     *              {@link HitEntityEvent#getEntity()}
     */
    public void hitEntityEvent(HitEntityEvent event){}

    /**
     * Called when a player left clicks while the
     * specified set is equipped.
     *
     * @param event generic event {@link Event}
     */
    public void leftClickEvent(LeftClickEvent event){}

    /**
     * Called when a player right clicks while the
     * specified set is equipped.
     *
     * @param event generic event {@link Event}
     */
    public void rightClickEvent(RightClickEvent event){}

}

interface ISetBase {

    Set getSet();

}