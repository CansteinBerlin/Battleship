package net.quickwrite.battleship.ships;

import com.google.common.collect.ImmutableMap;
import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.blocks.BattleShipBlocks;
import net.quickwrite.battleship.builder.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Ship implements ConfigurationSerializable, Comparable<Ship> {

    public static NamespacedKey KEY = new NamespacedKey(Battleship.getInstance(), "shipLevel");

    private final int size;
    private final String placeBlock, hitBlock;
    private final ItemStack displayItem;
    private final String name, id;

    //Placement


    public Ship(int size, Material displayItem, String placeBlock, String hitBlock, String name, String id){
        this.size = size;
        this.placeBlock = placeBlock;
        this.hitBlock = hitBlock;
        this.displayItem = new ItemBuilder(displayItem).setDisplayName("§6" + name).setLore(Collections.singletonList("§r§7Size: §6" + size)).build();
        this.name = name;
        this.id = id;

        ShipManager.ships.add(this);
    }

    public Ship(Map<String, Object> data){
        size = (int) data.get("size");
        displayItem = (ItemStack) data.get("displayItem");
        placeBlock = (String) data.get("placeBlock");
        hitBlock = (String) data.get("hitBlock");
        id = (String) data.get("id");
        name = displayItem.getItemMeta().getDisplayName().substring(2);
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
                .put("id", id)
                .build();
    }

    @Override
    public String toString() {
        return "Ship{" +
                "size=" + size +
                ", placeBlock='" + placeBlock + '\'' +
                ", hitBlock='" + hitBlock + '\'' +
                ", displayItem=" + displayItem +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public int getSize() {
        return size;
    }

    public ItemStack getDisplayItem() {
        ItemMeta meta = displayItem.getItemMeta();
        meta.getPersistentDataContainer().set(Ship.KEY, PersistentDataType.INTEGER, size);
        ItemStack stack = displayItem.clone();
        stack.setItemMeta(meta);
        return stack;
    }

    public Material getPlaceBlock() {
        return BattleShipBlocks.getMaterial(placeBlock);
    }

    public Material getHitBlock() {
        return BattleShipBlocks.getMaterial(hitBlock);
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Ship o) {
        return size - o.size;
    }

    public String getId() {
        return id;
    }
}
