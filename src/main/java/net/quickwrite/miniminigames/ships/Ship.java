package net.quickwrite.miniminigames.ships;

import com.google.common.collect.ImmutableMap;
import net.quickwrite.miniminigames.blocks.BattleShipBlocks;
import net.quickwrite.miniminigames.items.BattleshipItems;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Objects;

public class Ship implements ConfigurationSerializable {

    private final int size;
    private final String displayItem, placeBlock, hitBlock;

    public Ship(int size, String display, String placeBlock, String hitBlock){
        this.size = size;
        this.displayItem = display;
        this.placeBlock = placeBlock;
        this.hitBlock = hitBlock;

        ShipManager.ships.add(this);
    }

    public Ship(Map<String, Object> data){
        size = (int) data.get("size");
        displayItem = (String) data.get("display");
        placeBlock = (String) data.get("placeBlock");
        hitBlock = (String) data.get("hitBlock");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return size == ship.size && displayItem.equals(ship.displayItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, displayItem, hitBlock, placeBlock);
    }

    @Override
    public Map<String, Object> serialize() {
        return new ImmutableMap.Builder<String, Object>()
                .put("size", size)
                .put("displayItem", displayItem)
                .put("hitBlock", hitBlock)
                .put("placeBlock", placeBlock)
                .build();
    }

    @Override
    public String toString() {
        return "Ship{" +
                "size=" + size +
                ", displayItem='" + displayItem + '\'' +
                ", placeBlock='" + placeBlock + '\'' +
                ", hitBlock='" + hitBlock + '\'' +
                '}';
    }

    public int getSize() {
        return size;
    }

    public ItemStack getDisplayItem() {
        return BattleshipItems.getItem(displayItem);
    }

    public Material getPlaceBlock() {
        return BattleShipBlocks.getMaterial(placeBlock);
    }

    public Material getHitBlock() {
        return BattleShipBlocks.getMaterial(hitBlock);
    }
}
