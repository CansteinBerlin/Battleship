package net.quickwrite.miniminigames.map;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.config.MapConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapManager {

    private ArrayList<String> maps;

    public MapManager() {
        maps = new ArrayList<>();
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

}
