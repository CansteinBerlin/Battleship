package net.quickwrite.miniminigames.listener;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.game.Game;
import net.quickwrite.miniminigames.game.gamestate.GameStateManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class AttackListener implements Listener {

    private final ArrayList<Player> delays = new ArrayList<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Game game = MiniMinigames.getInstance().getGameManager().getGame(event.getPlayer());
        if(game == null) return;
        if(!game.getCurrentGameState().equals(GameStateManager.GameState.ATTACKING)) return;
        if(!game.isAttacking(event.getPlayer())) return;

        if(delays.contains(event.getPlayer())){
            event.getPlayer().sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("display.game.underDelay"));
            return;
        }

        delays.add(event.getPlayer());
        Bukkit.broadcastMessage("delay");
        new BukkitRunnable(){
            @Override
            public void run() {
                delays.remove(event.getPlayer());
            }
        }.runTaskLater(MiniMinigames.getInstance(), MiniMinigames.ATTACK_DELAY);

        game.attack();
    }

}
