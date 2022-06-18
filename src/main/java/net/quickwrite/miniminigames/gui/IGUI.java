package net.quickwrite.miniminigames.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public interface IGUI {

    void open();
    void close();
    void onClick(int slot, InventoryAction inventoryAction);
    void update();
    boolean onClose(Player p);
}
