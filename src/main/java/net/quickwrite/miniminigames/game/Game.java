package net.quickwrite.miniminigames.game;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.game.gamestate.GameStateManager;
import net.quickwrite.miniminigames.map.Map;
import net.quickwrite.miniminigames.map.MapSide;
import net.quickwrite.miniminigames.ships.Ship;
import net.quickwrite.miniminigames.ships.ShipContainer;
import net.quickwrite.miniminigames.util.DebugMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private final Player defender, attacker;
    private final GameStateManager manager;
    private Map map;
    private boolean started;
    private PlayerSafe attackerSafe, defenderSafe;
    private ShipPlacementRunner attackerShipPlacementRunner, defenderShipPlacementRunner;
    private AttackShipRunner attackerShipRunner, defenderShipRunner;
    private ArrayList<ShipContainer> attackerShips, defenderShips;
    private boolean attackerAttacking;

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

        startShipPlacement();
    }

    public void startShipPlacement(){
        manager.setCurrentGameState(GameStateManager.GameState.PLACING_SHIPS);

        attackerShipPlacementRunner = new ShipPlacementRunner(attacker, new HashMap<>(map.getShips()), map.getAttacker());
        defenderShipPlacementRunner = new ShipPlacementRunner(defender, new HashMap<>(map.getShips()), map.getDefender());
        attackerShipPlacementRunner.runTaskTimer(MiniMinigames.getInstance(), 0, 1);
        defenderShipPlacementRunner.runTaskTimer(MiniMinigames.getInstance(), 0, 1);
    }

    public void startAttacking(){
        setCurrentGameState(GameStateManager.GameState.ATTACKING);

        attackerShipPlacementRunner = null;
        defenderShipPlacementRunner = null;
        attackerShipRunner = new AttackShipRunner(attacker, map.getAttacker());
        defenderShipRunner = new AttackShipRunner(defender, map.getDefender());
        attackerShipRunner.runTaskTimer(MiniMinigames.getInstance(), 0, 1);
        defenderShipRunner.runTaskTimer(MiniMinigames.getInstance(), 0, 1);
        defenderShipRunner.setRunning(false);
        attackerAttacking = true;
        attacker.sendMessage(MiniMinigames.PREFIX + "§aYou can now attack your opponent");
    }

    public void attack(){
        if(attackerAttacking){
            if(attackerShipRunner.getLastLocation() == null) return;
            Location displayLoc = map.getAttacker().getOtherPlayerDisplay().convertWorldToLocalCoordinate(attackerShipRunner.getLastLocation());
            ShipContainer sc = shipAtPosition(displayLoc, defenderShips);
            if(sc != null){
                map.getAttacker().getOtherPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), sc.getHitBlock());
                map.getDefender().getThisPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), sc.getHitBlock());
                sc.hitLocation(displayLoc);
                if(sc.isSunk()){
                    attackerShips.remove(sc);
                    attacker.sendTitle("§aSunk", "", 0, 20 * 3, 0);
                }else {
                    attacker.sendTitle("§aHit", "", 0, 20 * 3, 0);
                }
            }else{
                map.getAttacker().getOtherPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), Material.BLUE_CONCRETE);
                map.getDefender().getThisPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), Material.BLUE_CONCRETE);
                attacker.sendTitle("§cMiss", "", 0, 20*3, 0);
            }
            map.getAttacker().getOtherPlayerDisplay().removeSpawnedMarkers();
            attackerShipRunner.setRunning(false);
            defenderShipRunner.setRunning(true);
        }else{
            if(defenderShipRunner.getLastLocation() == null) return;
            Location displayLoc = map.getDefender().getOtherPlayerDisplay().convertWorldToLocalCoordinate(defenderShipRunner.getLastLocation());
            ShipContainer sc = shipAtPosition(displayLoc, attackerShips);
            if(sc != null){
                map.getDefender().getOtherPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), sc.getHitBlock());
                map.getAttacker().getThisPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), sc.getHitBlock());
                defender.sendTitle("§aHit", "", 0, 20*3, 0);
            }else{
                map.getDefender().getOtherPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), Material.BLUE_CONCRETE);
                map.getAttacker().getThisPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), Material.BLUE_CONCRETE);
                defender.sendTitle("§cMiss", "", 0, 20*3, 0);
            }
            map.getDefender().getOtherPlayerDisplay().removeSpawnedMarkers();
            defenderShipRunner.setRunning(false);
            attackerShipRunner.setRunning(true);
        }
        attackerAttacking = !attackerAttacking;
    }

    private ShipContainer shipAtPosition(Location displayLoc, ArrayList<ShipContainer> container) {
        for(ShipContainer sc : container){
            if(sc.containsLocation(displayLoc)) return sc;
        }
        return null;
    }

    public boolean isAttacking(Player player){
        return (player == attacker && attackerAttacking) || (player == defender && !attackerAttacking) ;
    }

    public void toggleShipDirection(Player p){
        if(defender.equals(p)) defenderShipPlacementRunner.toggleDirection();
        else attackerShipPlacementRunner.toggleDirection();
    }

    public void placeShip(Player p){
        boolean finished;
        if(defender.equals(p)) finished = defenderShipPlacementRunner.placeShip();
        else finished = attackerShipPlacementRunner.placeShip();

        if(finished){
            if(defender.equals(p)) {
                processContainers(defenderShipPlacementRunner.getPlacedShips(), map.getDefender());
                defenderShips = defenderShipPlacementRunner.getPlacedShips();
                defenderShipPlacementRunner = null;
            }
            else {
                processContainers(attackerShipPlacementRunner.getPlacedShips(), map.getAttacker());
                attackerShips = attackerShipPlacementRunner.getPlacedShips();
                attackerShipPlacementRunner = null;
            }

            p.sendMessage(MiniMinigames.PREFIX + "§aYou finished placing the ships.");
            if(attackerShips == null || defenderShips == null){
                p.sendMessage(MiniMinigames.PREFIX + "§aNow waiting for your opponent");
            }else{
                startAttacking();
            }
        }
    }

    public void processContainers(ArrayList<ShipContainer> containers, MapSide side){
        for(ShipContainer container : containers){
            container.convertLocations(side.getThisPlayerDisplay());
        }
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
        defenderShipPlacementRunner.cancel();
        attackerShipPlacementRunner.cancel();
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
