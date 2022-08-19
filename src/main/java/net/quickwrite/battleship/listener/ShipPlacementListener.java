package net.quickwrite.battleship.listener;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.game.Game;
import net.quickwrite.battleship.game.gamestate.GameStateManager;
import net.quickwrite.battleship.ships.Ship;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class ShipPlacementListener implements Listener {

    private final ArrayList<Player> droppedOnTick = new ArrayList<>();

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        if(!event.isSneaking() || event.getPlayer().isFlying()) return;
        Player p = event.getPlayer();

        Game game = Battleship.getInstance().getGameManager().getGame(p);
        if(game == null) return;
        if(!game.getCurrentGameState().equals(GameStateManager.GameState.PLACING_SHIPS)) return;
        game.toggleShipDirection(p);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent event){
        Player p = event.getPlayer();
        Game game = Battleship.getInstance().getGameManager().getGame(p);
        if(game == null) return;
        if(!game.getCurrentGameState().equals(GameStateManager.GameState.PLACING_SHIPS)) return;
        game.toggleShipDirection(p);
        event.setCancelled(true);

        droppedOnTick.add(p);
        new BukkitRunnable(){

            @Override
            public void run() {
                droppedOnTick.remove(p);
            }
        }.runTaskLater(Battleship.getInstance(), 2L);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(droppedOnTick.contains(event.getPlayer())) return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if(event.getItem() == null) return;
        if(event.getItem().getType().equals(Material.AIR)) return;
        if(event.getItem().getItemMeta() == null) return;
        if(!event.getItem().getItemMeta().getPersistentDataContainer().has(Ship.KEY, PersistentDataType.INTEGER)) return;

        placeShip(event.getPlayer());
    }

    public void placeShip(Player player){
        Game game = Battleship.getInstance().getGameManager().getGame(player);
        if(game == null) return;
        if(!game.getCurrentGameState().equals(GameStateManager.GameState.PLACING_SHIPS)) return;
        game.placeShip(player);
    }

    @EventHandler
    public void onCreativeCheat(InventoryCreativeEvent event){
        //Cancel item cheating
        Game game = Battleship.getInstance().getGameManager().getGame((Player) event.getWhoClicked());
        if(game == null) return;
        if(!game.getCurrentGameState().equals(GameStateManager.GameState.PLACING_SHIPS)) return;
        event.setCancelled(true);
        new BukkitRunnable(){

            @Override
            public void run() {
                ((Player) event.getWhoClicked()).updateInventory();
            }
        }.runTaskLater(Battleship.getInstance(), 1);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        //Disable taking the itemstack
        Game game = Battleship.getInstance().getGameManager().getGame((Player) event.getWhoClicked());
        if(game == null) return;
        if(!game.getCurrentGameState().equals(GameStateManager.GameState.PLACING_SHIPS)) return;
        event.setCancelled(true);
        new BukkitRunnable(){

            @Override
            public void run() {
                ((Player) event.getWhoClicked()).updateInventory();
            }
        }.runTaskLater(Battleship.getInstance(), 1);
    }

    @EventHandler
    public void onInventoryDragItem(InventoryDragEvent event){
        //disable dragging
        Game game = Battleship.getInstance().getGameManager().getGame((Player) event.getWhoClicked());
        if(game == null) return;
        if(!game.getCurrentGameState().equals(GameStateManager.GameState.PLACING_SHIPS)) return;
        event.setCancelled(true);
        new BukkitRunnable(){

            @Override
            public void run() {
                ((Player) event.getWhoClicked()).updateInventory();
            }
        }.runTaskLater(Battleship.getInstance(), 1);
    }

}
