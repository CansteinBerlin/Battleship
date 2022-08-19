package net.quickwrite.battleship.display;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.Map;

public class VerticalDisplay extends Display implements ConfigurationSerializable {

    public VerticalDisplay(Location loc1, Location loc2){
        super();

        if(loc1.getBlockY() > loc2.getBlockY()){
            setPos1(loc1);
            setPos2(loc2);
        }else{
            setPos1(loc2);
            setPos2(loc1);
        }

        int xDir = loc1.getBlockX() - loc2.getBlockX();
        int zDir = loc1.getBlockZ() - loc2.getBlockZ();
        if(xDir < 0 && zDir == 0) direction = Direction.NEG_Z_DIRECTION;
        else if(xDir > 0 && zDir == 0) direction = Direction.POS_Z_DIRECTION;
        else if(zDir < 0 && xDir == 0) direction = Direction.POS_X_DIRECTION;
        else direction = Direction.NEG_X_DIRECTION;

        minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
    }

    public VerticalDisplay(Map<String, Object> data){
        super();
        pos1 = (Location) data.get("pos1");
        pos2 = (Location) data.get("pos2");

        direction = Direction.valueOf((String) data.get("direction"));

        minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
    }

    //CONVERT DISPLAY BLOCKS TO REAL WORLD BLOCKS


    @Override
    protected void sendBlockChange(Location loc, Material material) {
        loc = unifyLocation(loc);
        changedBlocks.put(loc, material);
        loc.getBlock().setType(material);
    }

    @Override
    public void clearDisplay() {
        for(Location loc : changedBlocks.keySet()){
            loc.getBlock().setType(Material.AIR);
        }
    }

    @Override
    public void clearForPlayer(Player p){
    }

    @Override
    public int getWidth() {
        return Math.abs((pos1.getBlockX() - pos2.getBlockX()) * direction.xMod) + Math.abs((pos1.getBlockZ() - pos2.getBlockZ()) * direction.zMod) + 1;
    }

    @Override
    public int getHeight() {
        return Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1;
    }

    @Override
    public String getNamedPosition(Location loc) {
        return "asdas";
    }

    @Override
    public void setBlock(int x, int y, Material material) {
        if(x < 0 || x >= getWidth()) return;
        if(y < 0 || y >= getHeight()) return;

        sendBlockChange(pos1.clone().subtract(x * direction.xMod, y, x * direction.zMod), material);
    }

    @Override
    public void setBlock(Location loc, Material material) {
        if(!(loc.getBlockX() >= minX && loc.getBlockY() <= maxX)) return;
        if(!(loc.getBlockY() >= minY && loc.getBlockY() <= maxX)) return;
        if(!(loc.getBlockZ() >= minZ && loc.getBlockZ() <= maxZ)) return;

        sendBlockChange(loc, material);
    }

    @Override
    public Location convertWorldToLocalCoordinate(Location loc) {
        Location res = pos1.clone().subtract(Display.unifyLocation(loc));
        res.setX(Math.abs(res.getBlockX()));
        res.setY(Math.abs(res.getBlockY()));
        res.setZ(Math.abs(res.getBlockZ()));

        return new Location(null, Math.abs(res.getBlockX() * direction.getxMod() + res.getBlockZ() * direction.getzMod()), 0, res.getBlockY(), 0, 0);
    }

    @Override
    public Map<String, Object> serialize() {
        return new ImmutableMap.Builder<String, Object>()
                .put("pos1", pos1)
                .put("pos2", pos2)
                .put("direction", direction.name())
                .build();
    }
}
