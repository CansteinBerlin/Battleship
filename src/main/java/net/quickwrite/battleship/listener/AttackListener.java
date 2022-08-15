package net.quickwrite.battleship.listener;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.game.Game;
import net.quickwrite.battleship.game.gamestate.GameStateManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class AttackListener implements Listener {

    private final ArrayList<Player> delays = new ArrayList<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Game game = Battleship.getInstance().getGameManager().getGame(event.getPlayer());
        if(game == null) return;
        if(!game.getCurrentGameState().equals(GameStateManager.GameState.ATTACKING)) return;
        if(!game.isAttacking(event.getPlayer())) return;

        if(delays.contains(event.getPlayer())){
            event.getPlayer().sendMessage(Battleship.PREFIX + Battleship.getLang("display.game.underDelay"));
            return;
        }

        delays.add(event.getPlayer());
        //Bukkit.broadcastMessage("delay");
        new BukkitRunnable(){
            @Override
            public void run() {
                delays.remove(event.getPlayer());
            }
        }.runTaskLater(Battleship.getInstance(), Battleship.ATTACK_DELAY);

        game.attack();
    }

}
