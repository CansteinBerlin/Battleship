package net.quickwrite.miniminigames.game;

import net.quickwrite.miniminigames.Battleship;
import net.quickwrite.miniminigames.commands.battleship.BattleShipSpectateAllGamesCommand;
import net.quickwrite.miniminigames.display.Display;
import net.quickwrite.miniminigames.game.gamestate.GameStateManager;
import net.quickwrite.miniminigames.map.Map;
import net.quickwrite.miniminigames.map.MapSide;
import net.quickwrite.miniminigames.ships.Ship;
import net.quickwrite.miniminigames.ships.ShipContainer;
import org.bukkit.*;
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
import java.util.TreeMap;

public class Game {

    public static final Sound HIT_SOUND = Sound.BLOCK_ANVIL_PLACE,
                              MISS_SOUND = Sound.ENTITY_BOAT_PADDLE_WATER;

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
        p.sendMessage(Battleship.PREFIX + Battleship.getLang("display.game.gameStarted"));
        if(p.equals(defender)){
            attacker.sendMessage(Battleship.PREFIX + Battleship.getLang("display.game.gameAccepted", "player", p.getDisplayName()));
        }else{
            defender.sendMessage(Battleship.PREFIX + Battleship.getLang("display.game.gameAccepted", "player", p.getDisplayName()));
        }
        initGame();
    }

    public void initGame(){
        started = true;
        manager.setCurrentGameState(GameStateManager.GameState.GAME_INIT);

        attackerSafe = new PlayerSafe(attacker);
        defenderSafe = new PlayerSafe(defender);
        attacker.setGameMode(GameMode.CREATIVE);
        defender.setGameMode(GameMode.CREATIVE);

        map.addPlayer(attacker);
        map.addPlayer(defender);
        map.teleportToSpawn();

        attacker.getInventory().clear();
        defender.getInventory().clear();

        TreeMap<Ship, Integer> sortedShips = new TreeMap<>(map.getShips());

        for(java.util.Map.Entry<Ship, Integer> entry : sortedShips.entrySet()){
            ItemStack stack = entry.getKey().getDisplayItem().clone();
            stack.setAmount(entry.getValue());
            attacker.getInventory().addItem(stack);
            defender.getInventory().addItem(stack);
        }

        for(Player p : BattleShipSpectateAllGamesCommand.SPECTATE_ALL_GAMES_PLAYERS){
            addSpectator(p);
        }

        startShipPlacement();
    }

    public void addSpectator(Player p){
        map.getAttacker().addPlayerToDisplays(p);
        map.getDefender().addPlayerToDisplays(p);
    }

    public void removeSpectator(Player p){
        map.getAttacker().removePlayer(p);
        map.getDefender().removePlayer(p);
    }

    public void startShipPlacement(){
        manager.setCurrentGameState(GameStateManager.GameState.PLACING_SHIPS);

        attackerShipPlacementRunner = new ShipPlacementRunner(attacker, new HashMap<>(map.getShips()), map.getAttacker());
        defenderShipPlacementRunner = new ShipPlacementRunner(defender, new HashMap<>(map.getShips()), map.getDefender());
        attackerShipPlacementRunner.runTaskTimer(Battleship.getInstance(), 0, 1);
        defenderShipPlacementRunner.runTaskTimer(Battleship.getInstance(), 0, 1);
    }

    public void startAttacking(){
        setCurrentGameState(GameStateManager.GameState.ATTACKING);

        attackerShipPlacementRunner = null;
        defenderShipPlacementRunner = null;
        attackerShipRunner = new AttackShipRunner(attacker, map.getAttacker());
        defenderShipRunner = new AttackShipRunner(defender, map.getDefender());
        attackerShipRunner.runTaskTimer(Battleship.getInstance(), 0, 1);
        defenderShipRunner.runTaskTimer(Battleship.getInstance(), 0, 1);
        defenderShipRunner.setRunning(false);
        attackerAttacking = true;
        attacker.sendMessage(Battleship.PREFIX + Battleship.getLang("display.game.canAttack"));
    }

    public void forceQuit(){
        attacker.sendMessage(Battleship.PREFIX + Battleship.getLang("display.game.forcefullyStopped"));
        defender.sendMessage(Battleship.PREFIX + Battleship.getLang("display.game.forcefullyStopped"));

        map.getAttacker().getThisPlayerDisplay().removeSpawnedMarkers();
        map.getAttacker().getOtherPlayerDisplay().removeSpawnedMarkers();
        map.getDefender().getThisPlayerDisplay().removeSpawnedMarkers();
        map.getDefender().getOtherPlayerDisplay().removeSpawnedMarkers();

        map.getAttacker().removeAll();
        map.getDefender().removeAll();

        if(attackerShipPlacementRunner != null) attackerShipPlacementRunner.cancel();
        if(defenderShipPlacementRunner != null) defenderShipPlacementRunner.cancel();
        if(attackerShipRunner != null) attackerShipRunner.cancel();
        if(defenderShipRunner != null) defenderShipRunner.cancel();

        attackerSafe.setToPlayer();
        defenderSafe.setToPlayer();
        Battleship.getInstance().getGameManager().finishGame(this);
    }

    public void finishGame(){
        attackerShipRunner.cancel();
        defenderShipRunner.cancel();

        Player lost = attackerShips.isEmpty() ? attacker : defender;
        Player finished = attackerShips.isEmpty() ? defender : attacker;

        lost.sendTitle(Battleship.getLang("display.game.lost"), "", 0, 70, 0);
        finished.sendTitle(Battleship.getLang("display.game.won"), "", 0, 70, 0);

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
                    meta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BURST).withColor(Color.GREEN, Color.GREEN, Color.RED, Color.ORANGE).build());
                    firework.setFireworkMeta(meta);
                }
            }
        }.runTaskTimer(Battleship.getInstance(), 0, 20*2);

        Game game = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                map.getAttacker().removeAll();
                map.getDefender().removeAll();
                attackerSafe.setToPlayer();
                defenderSafe.setToPlayer();
                Battleship.getInstance().getGameManager().finishGame(game);
                rocketTask.cancel();
            }
        }.runTaskLater(Battleship.getInstance(), 20*10);
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
                //Hit Ship
                map.getAttacker().getOtherPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), sc.getHitBlock());
                map.getDefender().getThisPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), sc.getHitBlock());
                sc.hitLocation(displayLoc);
                if(sc.isSunk()){
                    defenderShips.remove(sc);
                    attacker.sendTitle(Battleship.getLang("display.game.sunk"), "", 0, 20 * 3, 0);
                    sc.markSunk(map.getDefender().getThisPlayerDisplay());
                    sc.markSunk(map.getAttacker().getOtherPlayerDisplay());
                }else {
                    attacker.sendTitle(Battleship.getLang("display.game.hit"), "", 0, 20 * 3, 0);
                }
                attacker.playSound(attacker.getLocation(), HIT_SOUND, SoundCategory.MASTER, 1, 0);
            }else{
                //Not hit ship
                attacker.playSound(attacker.getLocation(), MISS_SOUND, SoundCategory.MASTER, 1, 0);
                map.getAttacker().getOtherPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), Material.BLUE_CONCRETE);
                map.getDefender().getThisPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), Material.BLUE_CONCRETE);
                attacker.sendTitle(Battleship.getLang("display.game.miss"), "", 0, 20*3, 0);
            }
            map.getAttacker().getOtherPlayerDisplay().removeSpawnedMarkers();
            attackerShipRunner.setRunning(false);
            defenderShipRunner.setRunning(true);
        }else{
            if(defenderShipRunner.getLastLocation() == null) return;
            Location displayLoc = map.getDefender().getOtherPlayerDisplay().convertWorldToLocalCoordinate(defenderShipRunner.getLastLocation());
            ShipContainer sc = shipAtPosition(displayLoc, attackerShips);
            if(sc != null){
                //Hit Ship
                map.getDefender().getOtherPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), sc.getHitBlock());
                map.getAttacker().getThisPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), sc.getHitBlock());
                sc.hitLocation(displayLoc);
                if(sc.isSunk()){
                    attackerShips.remove(sc);
                    defender.sendTitle(Battleship.getLang("display.game.sunk"), "", 0, 20 * 3, 0);
                    sc.markSunk(map.getAttacker().getThisPlayerDisplay());
                    sc.markSunk(map.getDefender().getOtherPlayerDisplay());
                }else {
                    defender.sendTitle(Battleship.getLang("display.game.hit"), "", 0, 20 * 3, 0);
                }
                defender.playSound(defender.getLocation(), HIT_SOUND, SoundCategory.MASTER, 1, 0);
            }else{
                //Not Hit Ship
                defender.playSound(defender.getLocation(), MISS_SOUND, SoundCategory.MASTER, 1, 0);
                map.getDefender().getOtherPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), Material.BLUE_CONCRETE);
                map.getAttacker().getThisPlayerDisplay().setBlock(displayLoc.getBlockX(), displayLoc.getBlockZ(), Material.BLUE_CONCRETE);
                defender.sendTitle(Battleship.getLang("display.game.miss"), "", 0, 20*3, 0);
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
        if(defender.equals(p) && defenderShipPlacementRunner != null) defenderShipPlacementRunner.toggleDirection();
        else if(attackerShipPlacementRunner != null) attackerShipPlacementRunner.toggleDirection();
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

            p.sendMessage(Battleship.PREFIX + Battleship.getLang("display.game.finishedPlacing"));
            if(attackerShips == null || defenderShips == null){
                p.sendMessage(Battleship.PREFIX + Battleship.getLang("display.game.waitingForOpponent"));
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
            attacker.sendMessage(Battleship.PREFIX + Battleship.getLang("display.opponentDenied", "player", defender.getDisplayName()));
        }else{
            defender.sendMessage(Battleship.PREFIX + Battleship.getLang("display.opponentDenied", "player", attacker.getDisplayName()));
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
        Battleship.getInstance().getMapManager().markCurrentlyPlaying(map, true);
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
            defender.sendTitle(Battleship.getLang("display.game.won"), Battleship.getLang("display.game.leftGame"));
        }else{
            attacker.sendTitle(Battleship.getLang("display.game.won"), Battleship.getLang("display.game.leftGame"));
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

    public boolean isMissingPlayer(Player p) {
        return p == attacker;
    }

    public Map getMap() {
        return map;
    }

    public void brokeBlock(Location location) {
        location = Display.unifyLocation(location);
        location.subtract(0, 1, 0);
        map.getAttacker().checkAndResendBlock(location);
        map.getDefender().checkAndResendBlock(location);

    }
}
