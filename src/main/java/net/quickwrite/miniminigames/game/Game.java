package net.quickwrite.miniminigames.game;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.game.gamestate.GameStateManager;
import net.quickwrite.miniminigames.map.Map;
import net.quickwrite.miniminigames.ships.Ship;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Game {

    private final Player defender, attacker;
    private final GameStateManager manager;
    private Map map;
    private boolean started;
    private PlayerSafe attackerSafe, defenderSafe;
    private ShipPlacementRunner attackerRunner, defenderRunner;

    public Game(Player defender, Player attacker) {
        this.defender = defender;
        this.attacker = attacker;

        manager = new GameStateManager(this);
    }

    public void accept(Player p){
        p.sendMessage(MiniMinigames.PREFIX + "§aThe Game has been started");
        if(p.equals(defender)){
            attacker.sendMessage(MiniMinigames.PREFIX + "§aThe Player §6" + p.getDisplayName() + "§a has accepted the invite");
        }else{
            defender.sendMessage(MiniMinigames.PREFIX + "§aThe Player §6" + p.getDisplayName() + "§a has accepted the invite");
        }
        System.out.println("Start the Game");
        started = true;
        initGame();
    }

    private void initGame(){
        manager.setCurrentGameState(GameStateManager.GameState.GAME_INIT);

        attackerSafe = new PlayerSafe(attacker);
        defenderSafe = new PlayerSafe(defender);

        map.addPlayer(attacker);
        map.addPlayer(defender);
        map.teleportToSpawn();

        attacker.getInventory().clear();
        defender.getInventory().clear();

        for(Ship ship : map.getShips().keySet()){
            ItemStack stack = ship.getDisplayItem().clone();
            stack.setAmount(map.getShips().get(ship));
            attacker.getInventory().addItem(stack);
            defender.getInventory().addItem(stack);
        }

        startGame();
    }

    public void startGame(){
        manager.setCurrentGameState(GameStateManager.GameState.PLACING_SHIPS);

        attackerRunner = new ShipPlacementRunner(attacker, new HashMap<>(map.getShips()), map.getAttacker());
        defenderRunner = new ShipPlacementRunner(defender, new HashMap<>(map.getShips()), map.getDefender());
        attackerRunner.runTaskTimer(MiniMinigames.getInstance(), 0, 1);
        defenderRunner.runTaskTimer(MiniMinigames.getInstance(), 0, 1);
    }

    public void deny(Player p){
        p.sendMessage(MiniMinigames.PREFIX + "§aYou denied the invite");
        if(p.equals(defender)){
            attacker.sendMessage(MiniMinigames.PREFIX + "§cYour opponent §6" + p.getDisplayName() + "§c has denied the invite");
        }else{
            defender.sendMessage(MiniMinigames.PREFIX + "§cYour opponent §6" + p.getDisplayName() + "§c has denied the invite");
        }
    }

    public void stop(){
        defenderRunner.cancel();
        attackerRunner.cancel();
    }

    public GameStateManager.GameState getCurrentGameState(){
        return manager.getCurrentGameState();
    }

    public void setCurrentGameState(GameStateManager.GameState gameState){
        manager.setCurrentGameState(gameState);
    }

    public boolean contains(Player player){
        return defender.equals(player) || attacker.equals(player);
    }

    public void setMap(Map map){
        this.map = map;
    }

    public boolean isStarted() {
        return started;
    }


}
