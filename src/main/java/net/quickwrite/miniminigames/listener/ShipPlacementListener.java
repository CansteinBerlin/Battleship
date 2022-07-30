package net.quickwrite.miniminigames.listener;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.game.Game;
import net.quickwrite.miniminigames.game.gamestate.GameStateManager;
import net.quickwrite.miniminigames.ships.Ship;
import net.quickwrite.miniminigames.util.DebugMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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

        Game game = MiniMinigames.getInstance().getGameManager().getGame(p);
        if(game == null) return;
        if(!game.getCurrentGameState().equals(GameStateManager.GameState.PLACING_SHIPS)) return;
        game.toggleShipDirection(p);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent event){
        Player p = event.getPlayer();
        Game game = MiniMinigames.getInstance().getGameManager().getGame(p);
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
        }.runTaskLater(MiniMinigames.getInstance(), 2L);
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
        Game game = MiniMinigames.getInstance().getGameManager().getGame(player);
        if(game == null) return;
        if(!game.getCurrentGameState().equals(GameStateManager.GameState.PLACING_SHIPS)) return;
        game.placeShip(player);
    }


}
