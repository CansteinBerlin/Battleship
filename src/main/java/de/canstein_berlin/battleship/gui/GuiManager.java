package main.java.de.canstein_berlin.battleship.gui;

import main.java.de.canstein_berlin.battleship.game.Game;
import main.java.de.canstein_berlin.battleship.map.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import java.util.HashMap;
import java.util.function.Consumer;

public class GuiManager {

    private final HashMap<Player, IGUI> guis;

    public GuiManager(){
        guis = new HashMap<>();
    }

    public boolean onClick(Player p, int slot, InventoryAction action){
        if(!guis.containsKey(p)) return false;
        if(slot == -999) return true;
        guis.get(p).onClick(slot, action);
        return true;
    }

    public void onClose(Player p){
        if(guis.containsKey(p)){
            if(guis.get(p).onClose(p)){
                guis.remove(p);
            }
        }
    }

    public boolean isCustomGui(Player p){
        return guis.containsKey(p);
    }


    public MapSelectionGUI createMapSelectionGui(Player player, Game game, Consumer<Map> consumer){
        MapSelectionGUI gui = new MapSelectionGUI(player, game, consumer);
        guis.put(player, gui);
        return gui;
    }
}
