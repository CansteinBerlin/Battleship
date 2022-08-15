package net.quickwrite.miniminigames.listener;

import net.quickwrite.miniminigames.Battleship;
import net.quickwrite.miniminigames.commands.battleship.BattleShipSpectateAllGamesCommand;
import net.quickwrite.miniminigames.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerGameListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        BattleShipSpectateAllGamesCommand.SPECTATE_ALL_GAMES_PLAYERS.remove(event.getPlayer());

        Player p = event.getPlayer();
        Game game = Battleship.getInstance().getGameManager().getGame(p);
        if(game == null) return;
        Battleship.getInstance().getGameManager().removeGameBecauseOfPlayerLeft(game, p);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerBreakBlock(BlockBreakEvent event){
        Player p = event.getPlayer();
        Game game = Battleship.getInstance().getGameManager().getGame(p);
        if(game == null) return;

        new BukkitRunnable(){

            @Override
            public void run() {
                game.brokeBlock(event.getBlock().getLocation());
            }
        }.runTaskLater(Battleship.getInstance(), 1);

        event.setCancelled(true);
    }
}
