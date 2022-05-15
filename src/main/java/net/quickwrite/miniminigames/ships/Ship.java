package net.quickwrite.miniminigames.ships;

import java.util.HashSet;
import java.util.Objects;

public class Ship {

    public static final HashSet<Ship> ships = new HashSet<>();

    private final int size;

    public Ship(int size){
        this.size = size;

        ships.add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return size == ship.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size);
    }
}
