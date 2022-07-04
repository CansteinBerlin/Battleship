package net.quickwrite.miniminigames.listener;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.game.Game;
import net.quickwrite.miniminigames.game.gamestate.GameStateManager;
import net.quickwrite.miniminigames.ships.Ship;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.persistence.PersistentDataType;

public class ShipPlacementListener implements Listener {

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        if(!event.isSneaking() || event.getPlayer().isFlying()) return;
        Player p = event.getPlayer();

        Game game = MiniMinigames.getInstance().getGameManager().getGame(p);
        if(game == null) return;
        if(!game.getCurrentGameState().equals(GameStateManager.GameState.PLACING_SHIPS)) return;
        game.toggleShipDirection(p);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
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
