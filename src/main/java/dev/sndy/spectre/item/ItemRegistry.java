package dev.sndy.spectre.item;

import dev.sndy.spectre.item.effect.Effect;
import dev.sndy.spectre.item.effect.EffectList;
import dev.sndy.spectre.item.effect.EffectType;
import dev.sndy.spectre.item.stat.Rarity;
import dev.sndy.spectre.item.stat.Stat;
import dev.sndy.spectre.item.stat.StatList;
import dev.sndy.spectre.item.stat.StatType;
import org.bukkit.Material;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;

public class ItemRegistry {

    public static final HashMap<LootTable> registry = new HashMap<>();

    public static void register(IMaterial material){
        if(material instanceof Equipment){
            registry.put(((Equipment) material).name, material);
        }
        else if(material instanceof Resource){
            registry.put(((Resource)material).name, material);
        }
        else if(material instanceof Food){
            registry.put(((Food)material).name, material);
        }
    }

    public static IMaterial get(String name){
        if(registry.containsKey(name)){
            return registry.get(name);
        }
        return new Resource("Null", 0, Material.BARRIER, "", false);
    }

    public static void log(String log){
        System.out.println("[SPECTRE ITEM PARSER]: " + log);
    }

    public static void parseItems() {
        File directory = new File("rpg/items");
        for(String fileName : Objects.requireNonNull(directory.list())){
            log("Parsing file: " + fileName + "!");
            File file = new File("rpg/items/" + fileName);
            try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                for(String header; (header = br.readLine()) != null; ) {
                    while(true) {
                        String backedUpHeader = "";
                        if (header.contains(":")) {
                            String formattedHeader = header.substring(header.indexOf(" ") + 1, header.indexOf(":"));
                            if (header.startsWith("EQUIPMENT")) {
                                boolean abort = false;
                                Equipment item = new Equipment(formattedHeader, 1, Material.BARRIER, new StatList(), Rarity.COMMON, "", false);
                                for (String line; (line = br.readLine()) != null; ) {
                                    if (line.contains("=")) {
                                        String key = line.substring(0, line.indexOf("="));
                                        String value = line.substring(line.indexOf("=") + 1);
                                        switch (key) {
                                            case "Material":
                                                if (Material.getMaterial(value) != null)
                                                    item.material = Material.getMaterial(value);
                                                else {
                                                    log("Illegal Material! Material parsing failed in file " + fileName + " on item " + header + " with values \"" + key + "=" + value + "\"! Aborting item injection.");
                                                    abort = true;
                                                }
                                                break;
                                            case "Stat":
                                                if (value.contains(",")) {
                                                    String[] args = value.split(",");
                                                    Stat stat = new Stat(StatType.INVALID);
                                                    try {
                                                        stat = new Stat(StatType.valueOf(args[0]));
                                                        stat.chance = 100;
                                                    } catch (IllegalArgumentException e) {
                                                        log("Illegal StatType! Stat parsing failed in file " + fileName + " on item " + header + " with values \"" + key + "=" + value + "\"! Aborting item injection");
                                                        abort = true;
                                                    }
                                                    stat.value = new Double(args[1]);
                                                    if (args.length == 3) {
                                                        stat.chance = new Double(args[2].replace("%", ""));
                                                    }
                                                    item.stats.add(stat);
                                                } else {
                                                    log("Illegal Stat! Stat parsing failed in file " + fileName + " on item " + header + " with values \"" + key + "=" + value + "\"! Aborting item injection");
                                                    abort = true;
                                                }
                                                break;
                                            case "Rarity":
                                                try {
                                                    item.rarity = Rarity.valueOf(value);
                                                } catch (IllegalArgumentException e) {
                                                    log("Illegal Rarity! Stat parsing failed in file " + fileName + " on item " + header + " with values \"" + key + "=" + value + "\"! Aborting item injection");
                                                    abort = true;
                                                }
                                                break;
                                            case "Description":
                                                item.description = value;
                                                break;
                                            case "Enchanted":
                                                item.enchanted = value.equals("TRUE");
                                                break;
                                            default:
                                                log("Illegal argument! Stat parsing failed in file " + fileName + " on item " + header + " with values \"" + key + "=" + value + "\"! Aborting item injection");
                                                abort = true;
                                        }
                                    } else {
                                        backedUpHeader = line;
                                        break;
                                    }
                                    if (abort) {
                                        break;
                                    }
                                }
                                if (!abort) {
                                    log("Injected item " + item.name + " into the registry HashMap!");
                                    register(item);
                                }
                            }
                            else if(header.startsWith("RESOURCE")){
                                boolean abort = false;
                                Resource item = new Resource(formattedHeader, 1, Material.BARRIER, "", false);
                                for (String line; (line = br.readLine()) != null; ) {
                                    if (line.contains("=")) {
                                        String key = line.substring(0, line.indexOf("="));
                                        String value = line.substring(line.indexOf("=") + 1);
                                        switch (key) {
                                            case "Material":
                                                if (Material.getMaterial(value) != null)
                                                    item.material = Material.getMaterial(value);
                                                else {
                                                    log("Illegal Material! Material parsing failed in file " + fileName + " on item " + header + " with values \"" + key + "=" + value + "\"! Aborting item injection.");
                                                    abort = true;
                                                }
                                                break;
                                            case "Description":
                                                item.description = value;
                                                break;
                                            case "Enchanted":
                                                item.enchanted = value.equals("TRUE");
                                                break;
                                            default:
                                                log("Illegal argument! Stat parsing failed in file " + fileName + " on item " + header + " with values \"" + key + "=" + value + "\"! Aborting item injection");
                                                abort = true;
                                        }
                                    } else {
                                        backedUpHeader = line;
                                        break;
                                    }
                                    if (abort) {
                                        break;
                                    }
                                }
                                if (!abort) {
                                    log("Injected item " + item.name + " into the registry HashMap!");
                                    register(item);
                                }
                            }
                            else if(header.startsWith("FOOD")){
                                boolean abort = false;
                                Food item = new Food(formattedHeader, 1, Material.BARRIER, new EffectList(), "", false);
                                for (String line; (line = br.readLine()) != null; ) {
                                    if (line.contains("=")) {
                                        String key = line.substring(0, line.indexOf("="));
                                        String value = line.substring(line.indexOf("=") + 1);
                                        switch (key) {
                                            case "Material":
                                                if (Material.getMaterial(value) != null)
                                                    item.material = Material.getMaterial(value);
                                                else {
                                                    log("Illegal Material! Material parsing failed in file " + fileName + " on item " + header + " with values \"" + key + "=" + value + "\"! Aborting item injection.");
                                                    abort = true;
                                                }
                                                break;
                                            case "Effect":
                                                if (value.contains(",")) {
                                                    String[] args = value.split(",");
                                                    Effect effect = new Effect(EffectType.INVALID);
                                                    try {
                                                        effect = new Effect(EffectType.valueOf(args[0]));
                                                        effect.chance = 100;
                                                    } catch (IllegalArgumentException e) {
                                                        log("Illegal StatType! Stat parsing failed in file " + fileName + " on item " + header + " with values \"" + key + "=" + value + "\"! Aborting item injection");
                                                        abort = true;
                                                    }
                                                    effect.value = new Double(args[1]);
                                                    if (args.length == 3) {
                                                        effect.chance = new Double(args[2].replace("%", ""));
                                                    }
                                                    item.effects.add(effect);
                                                } else {
                                                    log("Illegal Stat! Stat parsing failed in file " + fileName + " on item " + header + " with values \"" + key + "=" + value + "\"! Aborting item injection");
                                                    abort = true;
                                                }
                                                break;
                                            case "Description":
                                                item.description = value;
                                                break;
                                            case "Enchanted":
                                                item.enchanted = value.equals("TRUE");
                                                break;
                                            default:
                                                log("Illegal argument! Stat parsing failed in file " + fileName + " on item " + header + " with values \"" + key + "=" + value + "\"! Aborting item injection");
                                                abort = true;
                                        }
                                    } else {
                                        backedUpHeader = line;
                                        break;
                                    }
                                    if (abort) {
                                        break;
                                    }
                                }
                                if (!abort) {
                                    log("Injected item " + item.name + " into the registry HashMap!");
                                    register(item);
                                }
                            }
                        }
                        if(backedUpHeader.equals("")) {
                            break;
                        }
                        else{
                            header = backedUpHeader;
                        }
                    }
                }
            }
            catch(FileNotFoundException e){
                log("Error while parsing file: " + fileName + "!");
                log("The file could be corrupt or missing.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
