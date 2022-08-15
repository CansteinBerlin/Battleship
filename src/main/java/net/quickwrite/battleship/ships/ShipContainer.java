package net.quickwrite.battleship.ships;

import net.quickwrite.battleship.display.Display;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;

public class ShipContainer {

    private final ArrayList<Location> placedLocations, hitLocations;

    private final Material placeBlock, hitBlock;

    public ShipContainer(Ship ship, ArrayList<Location> locations){
        placedLocations = (ArrayList<Location>) locations.clone();
        hitLocations = new ArrayList<>();

        placeBlock = ship.getPlaceBlock();
        hitBlock = ship.getHitBlock();
    }

    public void registerShipLocation(Location loc){
        placedLocations.add(loc);
    }

    public void hitLocation(Location loc){
        Location l = loc.clone();
        hitLocations.add(l);
        placedLocations.remove(l);
    }

    public boolean containsLocation(Location loc){
        Location l = loc.clone();
        return placedLocations.contains(l);
    }

    public boolean isSunk(){
        return placedLocations.size() == 0;
    }

    public ArrayList<Location> getHitLocations() {
        return hitLocations;
    }

    public ArrayList<Location> getPlacedLocations() {
        return placedLocations;
    }

    public Material getPlaceBlock() {
        return placeBlock;
    }

    public Material getHitBlock() {
        return hitBlock;
    }

    public void convertLocations(Display display){
        ArrayList<Location> newLocs = new ArrayList<>();
        for(Location loc : placedLocations){
            Location l = display.convertWorldToLocalCoordinate(loc);
            l.setWorld(null);
            newLocs.add(l);

        }
        placedLocations.clear();
        placedLocations.addAll(newLocs);


    }

    public void markSunk(Display display) {
        for(Location loc : hitLocations){
            display.setBlock(loc.getBlockX(), loc.getBlockZ(), Material.GREEN_CONCRETE);
        }
    }
}
