package net.quickwrite.miniminigames.game;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.game.gamestate.GameStateManager;
import net.quickwrite.miniminigames.map.Map;
import net.quickwrite.miniminigames.map.MapSide;
import net.quickwrite.miniminigames.ships.Ship;
import net.quickwrite.miniminigames.ships.ShipContainer;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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

    public void finishGame(){
        attackerShipRunner.cancel();
        defenderShipRunner.cancel();

        Player lost = attackerShips.isEmpty() ? attacker : defender;
        Player finished = attackerShips.isEmpty() ? defender : attacker;

        lost.sendTitle("§cYou Lost", "", 0, 70, 0);
        finished.sendTitle("§aYou Won", "", 0, 70, 0);

        //Rockets
        Random random = new Random();
        Location spawnLoc = finished.getLocation();

        //Display Your Board to opponent
        if (lost == attacker) displayToOpponent(defenderShips, map.getAttacker());
        else displayToOpponent(attackerShips, map.getDefender());

        BukkitTask rocketTask = new BukkitRunnable(){

            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    Firework firework = (Firework) spawnLoc.getWorld().spawnEntity(spawnLoc.clone().add(random.nextInt(10) - 5, 0, random.nextInt(10) - 5), EntityType.FIREWORK);
                    FireworkMeta meta = firework.getFireworkMeta();
                    meta.clearEffects();
                    meta.setPower(2);
                    meta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BURST).withColor(Color.GREEN, Color.GREEN, Color.GREEN, Color.RED, Color.ORANGE).build());
                    firework.setFireworkMeta(meta);
                }
            }
        }.runTaskTimer(MiniMinigames.getInstance(), 0, 20*2);

        Game game = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                map.getAttacker().removeAll();
                map.getDefender().removeAll();
                attackerSafe.setToPlayer();
                defenderSafe.setToPlayer();
                MiniMinigames.getInstance().getGameManager().finishGame(game);
                rocketTask.cancel();
            }
        }.runTaskLater(MiniMinigames.getInstance(), 20*10);
    }

    private void displayToOpponent(ArrayList<ShipContainer> ships, MapSide displaySide) {
        for(ShipContainer container : ships){
            for(Location loc : container.getPlacedLocations()){
                displaySide.getOtherPlayerDisplay().setBlock(loc.getBlockX(), loc.getBlockZ(), Material.REDSTONE_BLOCK);
            }
        }
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
                    defenderShips.remove(sc);
                    attacker.sendTitle("§aSunk", "", 0, 20 * 3, 0);
                    sc.markSunk(map.getDefender().getThisPlayerDisplay());
                    sc.markSunk(map.getAttacker().getOtherPlayerDisplay());
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
                sc.hitLocation(displayLoc);
                if(sc.isSunk()){
                    attackerShips.remove(sc);
                    defender.sendTitle("§aSunk", "", 0, 20 * 3, 0);
                    sc.markSunk(map.getAttacker().getThisPlayerDisplay());
                    sc.markSunk(map.getDefender().getOtherPlayerDisplay());
                }else {
                    defender.sendTitle("§aHit", "", 0, 20 * 3, 0);
                }
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

        if(defenderShips.isEmpty() || attackerShips.isEmpty()){
            finishGame();
        }
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
        if(p.equals(defender)){
            attacker.sendMessage(MiniMinigames.PREFIX + "§cYour opponent §6" + p.getDisplayName() + "§c has denied the invite");
        }else{
            defender.sendMessage(MiniMinigames.PREFIX + "§cYour opponent §6" + p.getDisplayName() + "§c has denied the invite");
        }
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


    public void playerLeft(Player p) {
        attackerSafe.setToPlayer();
        defenderSafe.setToPlayer();
        map.getDefender().removeAll();
        map.getAttacker().removeAll();
        if(attacker == p){
            defender.sendTitle("§aYou won!", "§6Your opponent left the game");
        }else{
            attacker.sendTitle("§aYou won!", "§6Your opponent left the game");
        }
        if(attackerShipPlacementRunner != null){
            attackerShipPlacementRunner.cancel();
            defenderShipPlacementRunner.cancel();
        }
        if(attackerShipRunner != null){
            attackerShipRunner.cancel();
            defenderShipRunner.cancel();
        }
    }

    public Player getDefender() {
        return defender;
    }

    public Player getAttacker() {
        return attacker;
    }

    public Player getOpponent(Player p) {
        if(p == attacker) return defender;
        return attacker;
    }
}
