package net.quickwrite.miniminigames.ships;

import net.quickwrite.miniminigames.display.Direction;
import net.quickwrite.miniminigames.display.Display;
import net.quickwrite.miniminigames.display.HorizontalDisplay;
import net.quickwrite.miniminigames.util.DebugMessage;
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

    public void convertLocations(Display display){
        ArrayList<Location> newLocs = new ArrayList<>();
        if(display instanceof HorizontalDisplay) {
            for (Location loc : placedLocations) {
                Location res = display.getPos1().clone().subtract(Display.unifyLocation(loc));
                res.setX(Math.abs(res.getBlockX()));
                res.setY(Math.abs(res.getBlockY()));
                res.setZ(Math.abs(res.getBlockZ()));

                if (display.getDirection() == Direction.NEG_X_DIRECTION || display.getDirection() == Direction.POS_X_DIRECTION) {
                    int x = res.getBlockX();
                    res.setX(res.getBlockZ());
                    res.setZ(x);
                }
                newLocs.add(res);
            }
        }else{
            for (Location loc : placedLocations) {
                Location res = display.getPos1().clone().subtract(Display.unifyLocation(loc));
                res.setX(Math.abs(res.getBlockX()));
                res.setY(Math.abs(res.getBlockY()));
                res.setZ(Math.abs(res.getBlockZ()));

                newLocs.add(new Location(null, Math.abs(res.getBlockX() * display.getDirection().getxMod() + res.getBlockZ() * display.getDirection().getzMod()), 0, res.getBlockY(), 0, 0));
            }
        }
        placedLocations.clear();
        placedLocations.addAll(newLocs);
    }
}
