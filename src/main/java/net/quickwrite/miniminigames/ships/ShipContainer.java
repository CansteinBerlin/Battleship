package net.quickwrite.miniminigames.ships;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;

public class ShipContainer {

    private final ArrayList<Location> placedLocations;
    private final ArrayList<Location> hitLocations;

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

    public boolean hitLocation(Location loc){
        for(int i = placedLocations.size() - 1; i >= 0; i--){
            if(placedLocations.get(i).equals(loc)){
                placedLocations.remove(loc);
                hitLocations.add(loc);
                return true;
            }
        }
        return false;
    }

    public boolean containsLocation(Location loc){
        return placedLocations.contains(loc);
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
}
