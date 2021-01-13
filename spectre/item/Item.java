package com.sndy.spectre.item;

public class Item {

    public StatList stats;
    private String name;
    private String description;
    private final Rarity rarity;
    private final Material material;
  
    public Item(String name, Material material, StatList stats, Rarity rarity, String description){
        this.name = name;
        this.material = material;
        this.stats = stats;
        this.rarity = rarity;
        this.description = description;
    }
    
    public Item(String name, Material material, StatList stats, Rarity rarity){
        this.name = name;
        this.material = material;
        this.stats = stats;
        this.rarity = rarity;
        description = "";
    }
    
    public Item(ItemStack item){
        name = item.getItemMeta().getDisplayName().replaceAll("§.", "");
        material = item.getType();
        stats = StatBuilder.fromLore(item.getItemMeta().getLore());
        if(item.getItemMeta().getDisplayName().charAt(0) == '§'){
            Rarity = Stat.getRarityFormatting(item.getItemMeta().getDisplayName().charAt(1));
        }
        else{
            Rarity = Rarity.COMMON;
        }
        description = "";
    }
    
    public ItemStack toItemStack(){
        return StatBuilder.toItem(name, material, stats, rarity, description);
    }
    
    public Item bless(BlessingLevel level){
        Random rand = new Random();
        int seed = rand.nextInt(20) + 1;
        for(Stat stat : stats.getDamage()){
            stat.bless(seed);
        }
        for(Stat stat : stats.getDefense()){
            stat.bless(seed);
        }
    }

}
