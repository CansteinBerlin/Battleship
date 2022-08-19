package net.quickwrite.battleship.game;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerSafe {

    private ItemStack[] items;
    private Location loc;
    private GameMode gameMode;
    private Player p;

    public PlayerSafe(Player player){
        setFromPlayer(player);
    }

    public void setFromPlayer(Player p){
        this.p = p;
        items = p.getInventory().getContents();
        loc = p.getLocation();
        gameMode = p.getGameMode();
    }

    public void setToPlayer(){
        p.getInventory().setContents(items);
        p.setGameMode(gameMode);
        if(Game.SPAWN_POINT == null) p.teleport(loc);
        else p.teleport(Game.SPAWN_POINT);
    }
}
