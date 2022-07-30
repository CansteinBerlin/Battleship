package net.quickwrite.miniminigames.game;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.util.DebugMessage;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    private final ArrayList<Game> games;
    private final HashMap<Game, BukkitTask> tasks;

    public GameManager(){
        games = new ArrayList<>();
        tasks = new HashMap<>();
    }

    public Game createGame(Player attacker, Player defender){
        if(getGame(attacker) != null || getGame(defender) != null){
            return null;
        }
        Game game = new Game(attacker, defender);
        games.add(game);
        return game;

    }

    public Game getGame(Player player){
        for(Game game : games){
            if(game.contains(player)) return game;
        }
        return null;
    }

    public void removeGameBecauseOfDeny(Game game, Player player){
        game.deny(player);
        MiniMinigames.getInstance().getMapManager().markCurrentlyPlaying(game.getMap(), false);
        games.remove(game);
        stopCountdown(game);
    }

    public void removeGameBecauseOfPlayerLeft(Game game, Player player){
        if(!game.isStarted()) {
            removeGameBecauseOfDeny(game, player);
            return;
        }
        game.playerLeft(player);
        games.remove(game);
        MiniMinigames.getInstance().getMapManager().markCurrentlyPlaying(game.getMap(), false);
    }

    public void spectateAllGames(Player p){
        for(Game game : games){
            game.addSpectator(p);
        }
    }

    public void removeSpectateAllGames(Player p){
        for(Game game : games){
            game.removeSpectator(p);
        }
    }

    public void invalidMapSelection(Game game){
        games.remove(game);
    }

    public boolean contains(Player player){
        return getGame(player) != null;
    }

    public void finishGame(Game game) {
        games.remove(game);
        MiniMinigames.getInstance().getMapManager().markCurrentlyPlaying(game.getMap(), false);
    }

    public void startCountdown(Game game, Player p) {
        BukkitTask task = new BukkitRunnable(){
            @Override
            public void run() {
                game.getAttacker().sendMessage(MiniMinigames.PREFIX + "§cThe invite from §6" + p.getDisplayName() + "§c has been removed");
                game.getDefender().sendMessage(MiniMinigames.PREFIX + "§cYour opponent §6" + game.getAttacker().getDisplayName() + "§c has not responded");
                games.remove(game);
                tasks.remove(game);
            }
        }.runTaskLater(MiniMinigames.getInstance(), 20*60);
        tasks.put(game, task);
    }

    public void stopCountdown(Game game){
        BukkitTask task = tasks.get(game);
        if(task != null) task.cancel();
        tasks.remove(game);
    }

    public ArrayList<Game> getGames() {
        return games;
    }
}
