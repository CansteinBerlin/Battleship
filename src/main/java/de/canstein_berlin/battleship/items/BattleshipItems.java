package de.canstein_berlin.battleship.items;

import de.canstein_berlin.battleship.builder.items.ItemBuilder;
import de.canstein_berlin.battleship.config.ItemConfig;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class BattleshipItems {

    public static final HashMap<String, ItemStack> items = new HashMap<>();

    private BattleshipItems(){

    }

    public static void registerItemStack(String identifier, ItemStack stack){
        if(!items.containsKey(identifier)){
            items.put(identifier, stack);
        }else{
            items.replace(identifier, stack);
        }
    }

    public static ItemStack getItem(String identifier){
        return items.getOrDefault(identifier, new ItemBuilder(Material.BARRIER).setDisplayName("§cUNKNOWN ITEM WITH ID: " + identifier).build());
    }

    public static void saveItems(ItemConfig c){
        FileConfiguration config = c.getConfig();
        for(Map.Entry<String, ItemStack> entry : items.entrySet()){
            config.set(entry.getKey().replace(".", "§"), entry.getValue());
        }
        c.saveConfig();
    }

    public static void load(FileConfiguration config){
        for(String id : config.getKeys(false)){
            registerItemStack(id.replace("§", "."), config.getItemStack(id, new ItemBuilder(Material.BARRIER).setDisplayName("§cUNKNOWN ITEM WITH ID: " + id).build()));
        }

    }

}
