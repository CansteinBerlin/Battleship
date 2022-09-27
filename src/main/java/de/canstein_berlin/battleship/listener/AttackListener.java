package main.java.de.canstein_berlin.battleship.listener;

import main.java.de.canstein_berlin.battleship.Battleship;
import main.java.de.canstein_berlin.battleship.game.Game;
import main.java.de.canstein_berlin.battleship.game.gamestate.GameStateManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class AttackListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Game game = Battleship.getInstance().getGameManager().getGame(event.getPlayer());
        if(game == null) return;
        if(!game.getCurrentGameState().equals(GameStateManager.GameState.ATTACKING)) return;
        if(!game.isAttacking(event.getPlayer())) return;

        game.attack();
    }

}
