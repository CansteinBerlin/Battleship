package main.java.de.canstein_berlin.battleship.blocks;

import main.java.de.canstein_berlin.battleship.Battleship;
import main.java.de.canstein_berlin.battleship.config.BlockConfig;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BattleShipBlocks {

    public static final HashMap<String, Material> blocks = new HashMap<>();

    private BattleShipBlocks(){}

    public static void registerBlock(String identifier, Material material){
        if(!blocks.containsKey(identifier)){
            blocks.put(identifier, material);
        }else{
            blocks.replace(identifier, material);
        }
    }

    //UNSAFE DON'T USE ONLY FOR CONFIG
    private static void registerBlock(String identifier, String material){
        try {
            if (!blocks.containsKey(identifier)) {
                blocks.put(identifier, Material.valueOf(material));
            } else {
                blocks.replace(identifier, Material.valueOf(material));
            }
        }catch (IllegalArgumentException e){
            Battleship.LOGGER.severe("Could not load Material with name: " + material);
            blocks.remove(identifier);
        }
    }

    public static Material getMaterial(String identifier){
        return blocks.getOrDefault(identifier, Material.BARRIER);
    }

    public static void saveBlocks(BlockConfig c){
        FileConfiguration config = c.getConfig();
        for(Map.Entry<String, Material> entry : blocks.entrySet()){
            config.set(entry.getKey().replace(".", "§"), entry.getValue().name());
        }
        c.saveConfig();
    }

    public static void load(FileConfiguration config){
        for(String id : config.getKeys(false)){
            registerBlock(id.replace("§", "."), config.getString(id, Material.BARRIER.name()).toUpperCase(Locale.ROOT));
        }

    }

}
