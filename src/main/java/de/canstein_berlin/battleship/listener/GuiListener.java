package main.java.de.canstein_berlin.battleship.listener;

import main.java.de.canstein_berlin.battleship.Battleship;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GuiListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(Battleship.getInstance().getGuiManager().isCustomGui((Player) event.getWhoClicked())){
            if(Battleship.getInstance().getGuiManager().onClick((Player) event.getWhoClicked(), event.getSlot(), event.getAction())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        if(Battleship.getInstance().getGuiManager().isCustomGui((Player) event.getPlayer())){
            Battleship.getInstance().getGuiManager().onClose((Player) event.getPlayer());
        }
    }

}
