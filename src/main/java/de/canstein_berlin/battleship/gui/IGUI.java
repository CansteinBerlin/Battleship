package main.java.de.canstein_berlin.battleship.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public interface IGUI {

    void open();
    void close();
    void onClick(int slot, InventoryAction inventoryAction);
    void update();
    boolean onClose(Player p);
}
