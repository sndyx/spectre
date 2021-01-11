package com.sndy.spectre.event;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

public class Set {

    Sword sword;
    Helmet helmet;
    Chestplate chestplate;
    Leggings leggings;
    Boots boots;

    public Set(Object... set){
        for(Object piece : set){
            if(piece instanceof Sword){
                this.sword = (Sword)piece;
            }
            else if(piece instanceof Helmet){
                this.helmet = (Helmet)piece;
            }
            else if(piece instanceof Chestplate){
                this.chestplate = (Chestplate)piece;
            }
            else if(piece instanceof Leggings){
                this.leggings = (Leggings)piece;
            }
            else if(piece instanceof Boots){
                this.boots = (Boots)piece;
            }
        }
    }

    public Set(PlayerInventory inventory){
        int index = 0;
        for(ItemStack item : inventory.getArmorContents()){
            if(item != null) {
                if (index == 3 && !item.getType().equals(Material.AIR)) {
                    helmet = new Helmet(Objects.requireNonNull(item.getItemMeta()).getDisplayName().replaceAll("§.", ""));
                }
                if (index == 2 && !item.getType().equals(Material.AIR)) {
                    chestplate = new Chestplate(Objects.requireNonNull(item.getItemMeta()).getDisplayName().replaceAll("§.", ""));
                }
                if (index == 1 && !item.getType().equals(Material.AIR)) {
                    leggings = new Leggings(Objects.requireNonNull(item.getItemMeta()).getDisplayName().replaceAll("§.", ""));
                }
                if (index == 0 && !item.getType().equals(Material.AIR)) {
                    boots = new Boots(Objects.requireNonNull(item.getItemMeta()).getDisplayName().replaceAll("§.", ""));
                }
            }
            index++;
        }
        if(!inventory.getItemInMainHand().getType().equals(Material.AIR)) {
            sword = new Sword(Objects.requireNonNull(inventory.getItemInMainHand().getItemMeta()).getDisplayName().replaceAll("§.", ""));
        }
    }

    public boolean hasPiece(Class<? extends Set> c){
        if(c == Sword.class){
            return sword != null;
        }
        if(c == Helmet.class){
            return helmet != null;
        }
        if(c == Chestplate.class){
            return chestplate != null;
        }
        if(c == Leggings.class){
            return leggings != null;
        }
        if(c == Boots.class){
            return boots != null;
        }
        return false;
    }

    public boolean isMatch(Set set){
        if(hasPiece(Sword.class)){
            if(!(set.hasPiece(Sword.class) && set.sword.name.equals(sword.name))){
                return false;
            }
        }
        if(hasPiece(Helmet.class)){
            if(!(set.hasPiece(Helmet.class) && set.helmet.name.equals(helmet.name))){
                return false;
            }
        }
        if(hasPiece(Chestplate.class)){
            if(!(set.hasPiece(Chestplate.class) && set.chestplate.name.equals(chestplate.name))){
                return false;
            }
        }
        if(hasPiece(Leggings.class)){
            if(!(set.hasPiece(Leggings.class) && set.leggings.name.equals(leggings.name))){
                return false;
            }
        }
        if(hasPiece(Boots.class)){
            return set.hasPiece(Boots.class) && set.boots.name.equals(boots.name);
        }
        return true;
    }

    /**
     * Inner Classes & Methods:
     */

    String name;

    public Set(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static class Sword extends Set {

        @Override
        public String getName() {
            return super.getName();
        }

        public Sword(String name) {
            super(name);
        }

    }

    public static class Helmet extends Set {

        @Override
        public String getName() {
            return super.getName();
        }

        public Helmet(String name) {
            super(name);
        }

    }

    public static class Chestplate extends Set {

        @Override
        public String getName() {
            return super.getName();
        }

        public Chestplate(String name) {
            super(name);
        }

    }

    public static class Leggings extends Set {

        @Override
        public String getName() {
            return super.getName();
        }

        public Leggings(String name) {
            super(name);
        }

    }

    public static class Boots extends Set {

        @Override
        public String getName() {
            return super.getName();
        }

        public Boots(String name) {
            super(name);
        }

    }

}
