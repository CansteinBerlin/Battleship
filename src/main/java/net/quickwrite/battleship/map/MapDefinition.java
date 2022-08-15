package net.quickwrite.battleship.map;

import net.quickwrite.battleship.ships.Ship;
import org.bukkit.Location;

import java.util.Map;

public class MapDefinition {

    private Location attackerP1, attackerP2, attackerP3, attackerP4, defenderP1, defenderP2, defenderP3, defenderP4, attackerSpawn, defenderSpawn;

    private Map<Ship, Integer> ships;

    public MapDefinition(){

    }

    public MapDefinition(Location attackerP1, Location attackerP2, Location attackerP3, Location attackerP4, Location defenderP1, Location defenderP2, Location defenderP3, Location defenderP4, Location attackerSpawn, Location defenderSpawn, Map<Ship, Integer> ships) {
        this.attackerP1 = attackerP1;
        this.attackerP2 = attackerP2;
        this.attackerP3 = attackerP3;
        this.attackerP4 = attackerP4;
        this.defenderP1 = defenderP1;
        this.defenderP2 = defenderP2;
        this.defenderP3 = defenderP3;
        this.defenderP4 = defenderP4;
        this.attackerSpawn = attackerSpawn;
        this.defenderSpawn = defenderSpawn;
        this.ships = ships;
    }

    public Location getAttackerP1() {
        return attackerP1;
    }

    public void setAttackerP1(Location attackerP1) {
        this.attackerP1 = attackerP1;
    }

    public Location getAttackerP2() {
        return attackerP2;
    }

    public void setAttackerP2(Location attackerP2) {
        this.attackerP2 = attackerP2;
    }

    public Location getAttackerP3() {
        return attackerP3;
    }

    public void setAttackerP3(Location attackerP3) {
        this.attackerP3 = attackerP3;
    }

    public Location getAttackerP4() {
        return attackerP4;
    }

    public void setAttackerP4(Location attackerP4) {
        this.attackerP4 = attackerP4;
    }

    public Location getDefenderP1() {
        return defenderP1;
    }

    public void setDefenderP1(Location defenderP1) {
        this.defenderP1 = defenderP1;
    }

    public Location getDefenderP2() {
        return defenderP2;
    }

    public void setDefenderP2(Location defenderP2) {
        this.defenderP2 = defenderP2;
    }

    public Location getDefenderP3() {
        return defenderP3;
    }

    public void setDefenderP3(Location defenderP3) {
        this.defenderP3 = defenderP3;
    }

    public Location getDefenderP4() {
        return defenderP4;
    }

    public void setDefenderP4(Location defenderP4) {
        this.defenderP4 = defenderP4;
    }

    public Location getAttackerSpawn() {
        return attackerSpawn;
    }

    public void setAttackerSpawn(Location attackerSpawn) {
        this.attackerSpawn = attackerSpawn;
    }

    public Location getDefenderSpawn() {
        return defenderSpawn;
    }

    public void setDefenderSpawn(Location defenderSpawn) {
        this.defenderSpawn = defenderSpawn;
    }

    public Map<Ship, Integer> getShips() {
        return ships;
    }

    public void setShips(Map<Ship, Integer> ships) {
        this.ships = ships;
    }

    public MapDefinition clone(){
        return new MapDefinition(attackerP1.clone(), attackerP2.clone(), attackerP3.clone(), attackerP4.clone(), defenderP1.clone(), defenderP2.clone(), defenderP3.clone(), defenderP4.clone(), attackerSpawn.clone(), defenderSpawn.clone(), ships);
    }

    @Override
    public String toString() {
        return "MapDefinition{" +
                "attackerP1=" + attackerP1 +
                ", attackerP2=" + attackerP2 +
                ", attackerP3=" + attackerP3 +
                ", attackerP4=" + attackerP4 +
                ", defenderP1=" + defenderP1 +
                ", defenderP2=" + defenderP2 +
                ", defenderP3=" + defenderP3 +
                ", defenderP4=" + defenderP4 +
                ", attackerSpawn=" + attackerSpawn +
                ", defenderSpawn=" + defenderSpawn +
                '}';
    }


}
