package net.quickwrite.miniminigames.ships;

import com.google.common.collect.ImmutableMap;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;
import java.util.Objects;

public class Ship implements ConfigurationSerializable {

    private final int size;

    public Ship(int size){
        this.size = size;

        ShipManager.ships.add(this);
    }

    public Ship(Map<String, Object> data){
        size = (int) data.get("size");
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

    @Override
    @NonNull
    public Map<String, Object> serialize() {
        return new ImmutableMap.Builder<String, Object>()
                .put("size", size)
                .build();
    }

    @Override
    public String toString() {
        return "Ship{" +
                "size=" + size +
                '}';
    }
}
