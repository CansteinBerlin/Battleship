package net.quickwrite.miniminigames.listener;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.game.Game;
import net.quickwrite.miniminigames.game.gamestate.GameStateManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class AttackListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Game game = MiniMinigames.getInstance().getGameManager().getGame(event.getPlayer());
        if(game == null) return;
        if(!game.getCurrentGameState().equals(GameStateManager.GameState.ATTACKING)) return;
        if(!game.isAttacking(event.getPlayer())) return;
        game.attack();
    }

}
