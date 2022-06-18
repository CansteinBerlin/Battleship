package net.quickwrite.miniminigames.map;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.config.MapConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapManager {

    private final ArrayList<String> maps;
    private final HashMap<String, Map> currentlyPlayingMaps;

    public MapManager() {
        maps = new ArrayList<>();
        currentlyPlayingMaps = new HashMap<>();
    }

    public Map loadMap(String name){
        name = name.toLowerCase(Locale.ROOT);
        if(!maps.contains(name)){
            return null;
        }
        MapConfig cfg = new MapConfig(getFileName(name));
        System.out.println(cfg.getConfig());
        if(cfg.getConfig().contains("map"))
            return cfg.getConfig().getObject("map", Map.class);
        return null;
    }

    public void loadMaps(List<String> names){
        maps.addAll(names);
        Bukkit.broadcastMessage(maps + "");
    }

    public boolean addNewMap(String name, Map map){
        name = name.toLowerCase(Locale.ROOT);
        if(maps.contains(name.toLowerCase(Locale.ROOT))){
            return false;
        }

        MapConfig cfg = new MapConfig(getFileName(name));
        FileConfiguration config = cfg.getConfig();
        config.set("map", map);
        cfg.saveConfig();

        maps.add(name);
        MiniMinigames.getInstance().getMapsConfig().getConfig().set("maps", maps);
        MiniMinigames.getInstance().getMapsConfig().saveConfig();

        return true;
    }

    private String getFileName(String name){
        return "maps/" + name.toLowerCase(Locale.ROOT) + ".yml";
    }

    private ItemStack getDisplayItemStack(String name){
        return loadMap(name).getDisplayItem();
    }

    public ItemStack[] getDisplayItemStacks(){
        ItemStack[] stacks = new ItemStack[maps.size()];
        int i = 0;
        for(String name : maps){
            stacks[i] = getDisplayItemStack(name);
            i++;
        }
        return stacks;
    }

    public void markCurrentlyPlaying(Map map, boolean currentlyPlaying){
        if(currentlyPlaying){
            currentlyPlayingMaps.put(map.getName().toLowerCase(Locale.ROOT), map);
        }else{
            currentlyPlayingMaps.remove(map.getName().toLowerCase(Locale.ROOT));
        }
    }

    public boolean isCurrentlyPlaying(String name){
        return currentlyPlayingMaps.containsKey(name);
    }

    public ArrayList<String> getMaps() {
        return maps;
    }
}
