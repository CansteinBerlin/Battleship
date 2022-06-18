package net.quickwrite.miniminigames.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {

    private final ArrayList<Game> games;

    public GameManager(){
        games = new ArrayList<>();
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

    public void removeGame(Game game, Player player){
        game.deny(player);
        games.remove(game);
    }

    public void invalidMapSelection(Game game){
        games.remove(game);
    }

    public boolean contains(Player player){
        return getGame(player) != null;
    }

}
