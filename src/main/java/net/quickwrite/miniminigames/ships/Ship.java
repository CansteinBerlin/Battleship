package net.quickwrite.miniminigames.ships;

import com.google.common.collect.ImmutableMap;
import net.quickwrite.miniminigames.items.BattleshipItems;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;
import java.util.Objects;

public class Ship implements ConfigurationSerializable {

    private final int size;
    private final String display, placeBlock, hitBlock;

    public Ship(int size, String display, String placeBlock, String hitBlock){
        this.size = size;
        this.display = display;
        this.placeBlock = placeBlock;
        this.hitBlock = hitBlock;

        ShipManager.ships.add(this);
    }

    public Ship(Map<String, Object> data){
        size = (int) data.get("size");
        display = (String) data.get("display");
        placeBlock = (String) data.get("placeBlock");
        hitBlock = (String) data.get("hitBlock");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return size == ship.size && display.equals(ship.display);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, display, hitBlock, placeBlock);
    }

    @Override
    @NonNull
    public Map<String, Object> serialize() {
        return new ImmutableMap.Builder<String, Object>()
                .put("size", size)
                .put("display", display)
                .put("hitBlock", hitBlock)
                .put("placeBlock", placeBlock)
                .build();
    }

    @Override
    public String toString() {
        return "Ship{" +
                "size=" + size +
                ", display='" + display + '\'' +
                ", placeBlock='" + placeBlock + '\'' +
                ", hitBlock='" + hitBlock + '\'' +
                '}';
    }

    public int getSize() {
        return size;
    }

    public ItemStack getDisplay() {
        return BattleshipItems.getItem(display);
    }

    public ItemStack getPlaceBlock() {
        return BattleshipItems.getItem(placeBlock);
    }

    public ItemStack getHitBlock() {
        return BattleshipItems.getItem(hitBlock);
    }
}
