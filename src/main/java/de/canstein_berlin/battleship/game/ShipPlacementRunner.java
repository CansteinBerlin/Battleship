package main.java.de.canstein_berlin.battleship.game;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import main.java.de.canstein_berlin.battleship.Battleship;
import main.java.de.canstein_berlin.battleship.display.Direction;
import main.java.de.canstein_berlin.battleship.display.Display;
import main.java.de.canstein_berlin.battleship.map.MapSide;
import main.java.de.canstein_berlin.battleship.ships.Ship;
import main.java.de.canstein_berlin.battleship.ships.ShipContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;

import java.util.ArrayList;
import java.util.Map;

public class ShipPlacementRunner extends BukkitRunnable {

    public static String rotateShipMessage;

    private final Player p;
    private final Map<Ship, Integer> shipsToPlace;
    private final ArrayList<ShipContainer> placedShips;
    private final MapSide displaySide;

    private final int minX, maxX, minZ, maxZ;
    private final int y;
    private boolean horizontal;
    private Direction dirMult;
    private Location lastLocation;
    private boolean lastDirection;
    private int lastShipSize;
    private final ArrayList<Location> locations;
    private boolean recentlyPlaced;

    public ShipPlacementRunner(Player p, Map<Ship, Integer> shipsToPlace, MapSide displaySide) {
        this.p = p;
        this.shipsToPlace = shipsToPlace;
        this.displaySide = displaySide;

        minX = displaySide.getThisPlayerDisplay().getMinX();
        maxX = displaySide.getThisPlayerDisplay().getMaxX();
        minZ = displaySide.getThisPlayerDisplay().getMinZ();
        maxZ = displaySide.getThisPlayerDisplay().getMaxZ();
        y = displaySide.getThisPlayerDisplay().getMinY();

        horizontal = false;
        this.dirMult = displaySide.getOtherPlayerDisplay().getDirection();

        placedShips = new ArrayList<>();
        locations = new ArrayList<>();

        rotateShipMessage = Battleship.getLang("display.shipPlacementRunner.rotateShip");
        recentlyPlaced = false;
    }

    @Override
    public void run() {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(rotateShipMessage));

        if(p.getInventory().getItemInMainHand().getItemMeta() == null) {
            if(lastLocation != null) displaySide.getThisPlayerDisplay().removeSpawnedMarkers();
            lastLocation = null;
            return;
        }
        if(!p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Ship.KEY, PersistentDataType.INTEGER)) {
            if(lastLocation != null) displaySide.getThisPlayerDisplay().removeSpawnedMarkers();
            lastLocation = null;
            return;
        }
        RayTraceResult result = p.getWorld().rayTraceBlocks(p.getEyeLocation(), p.getLocation().getDirection(), 50);
        if(result == null) return;
        Location loc = Display.unifyLocation(result.getHitPosition().toLocation(p.getWorld()));
        int shipSize = p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(Ship.KEY, PersistentDataType.INTEGER);

        //Save Resources
        if(lastDirection == horizontal && loc.equals(lastLocation) && lastShipSize == shipSize && !recentlyPlaced) return;

        //Remove markers after placing
        recentlyPlaced = false;

        //Check Bounds
        if(loc.getBlockX() < minX || loc.getBlockX() > maxX || loc.getBlockZ() < minZ || loc.getBlockZ() > maxZ) {
            if(lastLocation != null) displaySide.getThisPlayerDisplay().removeSpawnedMarkers();
            lastLocation = null;
            return;
        }

        loc.setY(y);

        //Execute only one time
        lastDirection = horizontal;
        lastLocation = loc.clone();
        lastShipSize = shipSize;

        locations.clear();
        locations.add(loc.clone());

        //Calculate Locations
        for(int i = 1; i < shipSize; i++){
            locations.add(loc.add(
                    horizontal ? dirMult.getHorizontalX() : dirMult.getVerticalX(),
                    0,
                    horizontal ? dirMult.getHorizontalZ() : dirMult.getVerticalZ()
            ).clone());
        }

        displaySide.getThisPlayerDisplay().removeSpawnedMarkers();

        if(!allLocationsValid(locations)) {
            locations.clear();
            return;
        }

        for(Location l : locations){
            displaySide.getThisPlayerDisplay().markLocation(l);
        }
    }

    public void toggleDirection(){
        horizontal = !horizontal;
    }

    public boolean placeShip(){
        int shipSize = p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().getOrDefault(Ship.KEY, PersistentDataType.INTEGER, -1);
        if(locations.size() != shipSize) return false;
        Ship ship = null;
        for(Ship s : shipsToPlace.keySet()){
            if(shipSize == s.getSize()){
                ship = s;
                break;
            }
        }
        if(ship == null) return false;
        ShipContainer container = new ShipContainer(ship, locations);
        for(Location loc : locations){
            displaySide.getThisPlayerDisplay().setBlock(loc, container.getPlaceBlock());
        }

        placedShips.add(container);
        shipsToPlace.replace(ship, shipsToPlace.get(ship) - 1);
        if(shipsToPlace.get(ship) == 0) shipsToPlace.remove(ship);
        p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);

        //To prevent multiple ships being placed in the same tick at the same location
        locations.clear();
        recentlyPlaced = true;
        displaySide.getThisPlayerDisplay().removeSpawnedMarkers();

        if(shipsToPlace.isEmpty()){
            displaySide.getThisPlayerDisplay().removeSpawnedMarkers();
            cancel();
            return true;
        }
        return false;
    }

    public boolean allLocationsValid(ArrayList<Location> locs){
        for(Location loc : locs){
            if(!displaySide.isInBounds(loc)) return false;
            for(ShipContainer ship : placedShips){
                if(ship.containsLocation(loc)) return false;
            }
        }
        return true;
    }

    public ArrayList<ShipContainer> getPlacedShips() {
        return placedShips;
    }
}
