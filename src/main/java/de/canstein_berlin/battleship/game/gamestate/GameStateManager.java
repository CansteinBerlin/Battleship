package de.canstein_berlin.battleship.game.gamestate;

import de.canstein_berlin.battleship.game.Game;

public class GameStateManager {

    public enum GameState{
        PLAYER_ACCEPTING,
        GAME_INIT,
        PLACING_SHIPS,
        ATTACKING,
        GAME_FINISH
    }

    private GameState currentGameState;
    private final Game game;

    public GameStateManager(Game game){
        currentGameState = GameState.PLAYER_ACCEPTING;
        this.game = game;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}
