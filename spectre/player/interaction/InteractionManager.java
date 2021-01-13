package com.sndy.spectre.player.interaction;

import com.sndy.spectre.event.MobRegistry;
import com.sndy.spectre.player.NetworkPlayerHandler;
import com.sndy.spectre.player.stat.Stat;
import com.sndy.spectre.player.stat.StatBuilder;
import com.sndy.spectre.player.stat.StatList;
import com.sndy.spectre.player.stat.StatType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class InteractionManager implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
            LivingEntity damager = (LivingEntity)event.getDamager();
            LivingEntity damaged = (LivingEntity)event.getEntity();
            DamageMeta meta = calculateDamage(damager, damaged, event);
            event.setDamage(meta.heartsDealt);
            damager.setHealth(meta.heartsStolen > damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue() ? damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue() : meta.heartsStolen);
            damaged.setAbsorptionAmount(damaged.getAbsorptionAmount() + meta.heartsGilded);
            if(meta.isBleeding){
                damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 5, 1));
            }
            if(meta.isBurned){
                damaged.setFireTicks(50);
            }
            if(meta.isFrozen){
                damaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 5, 1));
            }
        }
    }

    final float DAMAGE_REDUCTION = 100; //Amount of slope on damage reduction of armor
    final float DAMAGE_PER_HEART = 1; //Hearts dealt in game per damage dealt in code
    final float DAMAGE_REDUCTION_BACKED = 2;

    public DamageMeta calculateDamage(LivingEntity damager, LivingEntity damaged, EntityDamageByEntityEvent event){

        ArrayList<Stat> damage;
        ArrayList<Stat> defense;

        if(damager instanceof Player){
            updatePlayer((Player)damager);
            damage = NetworkPlayerHandler.getPlayer(((Player)damager)).stats.getDamage();
        }
        else if(MobRegistry.registry.containsKey(damager.getCustomName())){
            damage = MobRegistry.registry.get(damager.getCustomName()).getDamage();
        }
        else{
            Stat damageStat = new Stat(StatType.DAMAGE_ALL);
            damageStat.setValue((float)event.getDamage() * 20);
            ArrayList<Stat> dummyStat = new ArrayList<>();
            dummyStat.add(damageStat);
            damage = dummyStat;
        }
        if(damaged instanceof Player){
            updatePlayer((Player)damaged);
            defense = NetworkPlayerHandler.getPlayer(((Player)damaged)).stats.getDefense();
        }
        else if(MobRegistry.registry.containsKey(damaged.getCustomName())){
            defense = MobRegistry.registry.get(damaged.getCustomName()).getDefense();
        }
        else{
            defense = new ArrayList<>();
        }

        ArrayList<Stat> landedAttacks = new ArrayList<>();
        Random random = new Random();
        damage.addAll(defense);
        for(Stat stat : damage){
            if(random.nextInt(100) < stat.getChance()){
                landedAttacks.add(stat);
            }
        }
        HashMap<String, Float> val = new HashMap<>();
        val.put("DAMAGE_ALL", 0f);
        val.put("DAMAGE_FLAME", 0f);
        val.put("DAMAGE_FROST", 0f);
        val.put("DAMAGE_BLEED", 0f);
        val.put("DAMAGE_STEAL", 0f);
        val.put("DAMAGE_ULTIMATE", 0f);
        val.put("DEFENSE_ALL", 0f);
        val.put("DEFENSE_FIRE", 0f);
        val.put("DEFENSE_ICE", 0f);
        val.put("DEFENSE_BLEED", 0f);
        val.put("DEFENSE_GILDED", 0f);
        val.put("DEFENSE_ULTIMATE", 0f);
        for(Stat stat : landedAttacks){
            val.put(stat.getType().toString(), val.get(stat.getType().toString()) + stat.getValue());
        }
        System.out.println("-------------COMBAT-LOG------------");
        for(Map.Entry<String, Float> entry : val.entrySet()){
            System.out.println("TOTAL-" + entry.getKey() + ": " + entry.getValue());
        }
        float totalDamage = (float)(val.get("DAMAGE_ALL") + 0.5 * (val.get("DAMAGE_FLAME") + val.get("DAMAGE_FROST") + val.get("DAMAGE_BLEED")));
        totalDamage = totalDamage / DAMAGE_REDUCTION_BACKED;
        float totalDefense = (float) (val.get("DEFENSE_ALL") + 0.5 * (val.get("DEFENSE_FIRE") + val.get("DEFENSE_ICE") + val.get("DEFENSE_BLEED")));
        float damageReduction = totalDefense / (totalDefense + DAMAGE_REDUCTION);
        float heartsDealt = totalDamage - totalDamage * damageReduction - val.get("DEFENSE_ULTIMATE") + val.get("DAMAGE_ULTIMATE");
        float gildedRecovery = val.get("DEFENSE_GILDED") / (val.get("DEFENSE_GILDED") + 300);
        float heartsGilded = heartsDealt * gildedRecovery;
        float lifesteal = val.get("DAMAGE_STEAL") / (val.get("DAMAGE_STEAL") + 300);
        float heartsStolen = heartsDealt * lifesteal;
        DamageMeta meta = new DamageMeta();
        meta.heartsDealt = heartsDealt / DAMAGE_PER_HEART;
        meta.heartsGilded = heartsGilded / DAMAGE_PER_HEART;
        meta.heartsStolen = heartsStolen / DAMAGE_PER_HEART;
        if(val.get("DAMAGE_FLAME") > val.get("DEFENSE_FIRE")){
            meta.isBurned = true;
        }
        if(val.get("DAMAGE_FROST") > val.get("DEFENSE_ICE")){
            meta.isFrozen = true;
        }
        if(val.get("DAMAGE_BLEED") > val.get("DEFENSE_BLEED")){
            meta.isBleeding = true;
        }
        System.out.println("-----------------------------------");
        System.out.println("HEARTS-DEALT: " + meta.heartsDealt);
        System.out.println("HEARTS-STOLEN: " + meta.heartsStolen);
        System.out.println("HEARTS-GILDED: " + meta.heartsGilded);
        System.out.println("IS-ON-FIRE: " + meta.isBurned);
        System.out.println("IS-BLEEDING: " + meta.isBleeding);
        System.out.println("IS-FROZEN: " + meta.isFrozen);
        System.out.println("-----------------------------------");
        return meta;
    }

    public void updatePlayer(Player player){
        StatList stats = StatBuilder.fromLore(player.getInventory().getItemInMainHand());
        for(ItemStack item : player.getInventory().getArmorContents()){
            stats.merge(StatBuilder.fromLore(item));
        }
        NetworkPlayerHandler.getPlayer(player.getUniqueId()).stats = stats;
    }

}
