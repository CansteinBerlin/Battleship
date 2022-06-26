package net.quickwrite.miniminigames.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerSafe {

    private ItemStack[] items;
    private Location loc;
    private Player p;

    public PlayerSafe(Player player){
        setFromPlayer(player);
    }

    public void setFromPlayer(Player p){
        this.p = p;
        items = p.getInventory().getContents();
        loc = p.getLocation();
    }

    public void setToPlayer(){
        p.getInventory().setContents(items);
        p.teleport(loc);
    }
}
