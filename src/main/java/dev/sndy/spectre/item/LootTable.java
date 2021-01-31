package dev.sndy.spectre.item;

import dev.sndy.spectre.Reference;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class LootTable {

    public String name;
    public Map.Entry<IMaterial, Integer>[] drops;

    public LootTable(String name, Map.Entry<IMaterial, Integer>... drops){
        this.name = name;
        this.drops = drops;
    }

    public IMaterial getItem(){
        Random random = new Random();
        for(Map.Entry<IMaterial, Integer> drop : drops){
            if(drop.getValue() > random.nextInt(100)){
                return drop.getKey();
            }
        }
        return Reference.NULL_RESOURCE;
    }

    public ArrayList<IMaterial> getItems(int count){
        ArrayList<IMaterial> items = new ArrayList<>();
        boolean found = false;
        Random random = new Random();
        while(count > 0){
            count--;
            for(Map.Entry<IMaterial, Integer> drop : drops){
                if(drop.getValue() > random.nextInt(100)){
                    items.add(drop.getKey());
                    found = true;
                    break;
                }
            }
            if(!found){
                items.add(Reference.NULL_RESOURCE);
            }
            found = false;
        }
        return items;
    }

}
