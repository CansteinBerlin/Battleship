package net.quickwrite.miniminigames.game.gamestate;

import net.quickwrite.miniminigames.game.Game;

public class GameStateManager {

    public enum GameState{
        PLAYER_ACCEPTING,
        GAME_INIT,
        PLACING_SHIPS,
        ATTACKER_ATTACKING,
        DEFENDER_ATTACKING,
        GAME_FINISH
    }

    private GameState currentGameState;
    private final Game game;

    public GameStateManager(Game game){
        currentGameState = GameState.PLAYER_ACCEPTING;
        this.game = game;
    }

    public void changeBoardSide(){
        currentGameState = (currentGameState == GameState.ATTACKER_ATTACKING) ? GameState.DEFENDER_ATTACKING : GameState.ATTACKER_ATTACKING;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}
