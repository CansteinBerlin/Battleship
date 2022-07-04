package net.quickwrite.miniminigames.listener;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerGameListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player p = event.getPlayer();
        Game game = MiniMinigames.getInstance().getGameManager().getGame(p);
        if(game == null) return;
        MiniMinigames.getInstance().getGameManager().removeGameBecauseOfPlayerLeft(game, p);
    }

}
