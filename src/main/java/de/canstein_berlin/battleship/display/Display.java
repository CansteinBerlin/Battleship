package de.canstein_berlin.battleship.display;

import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import de.canstein_berlin.battleship.packetwrapper.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Display {

    //Debug Only
    public static Display currentTestDisplay;

    protected final ArrayList<UUID> displayPlayers;
    protected final HashMap<Location, Material> changedBlocks;
    protected Location pos1, pos2;
    protected Direction direction;

    protected int minX, minY, minZ, maxX, maxY, maxZ;

    private ArrayList<Integer> markers;

    public Display() {
        displayPlayers = new ArrayList<>();
        changedBlocks = new HashMap<>();

        markers = new ArrayList<>();
    }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract String getNamedPosition(Location loc);
    public abstract void setBlock(int x, int y, Material material);
    public abstract void setBlock(Location loc, Material material);

    protected void sendBlockChange(Location loc, Material material){
        loc = unifyLocation(loc);
        ArrayList<UUID> left = new ArrayList<>();
        changedBlocks.put(loc, material);
        for(UUID uuid : displayPlayers){
            Player p = Bukkit.getPlayer(uuid);
            if(p == null){
                //Player left
                left.add(uuid);
                continue;
            }

            sendPacketToPlayers(getBlockChangePacket(loc, material));
        }
    }

    public void markLocation(Location loc){
        int ID = (int) (Math.random() * Integer.MAX_VALUE);
        sendMarkerSpawnToAllPlayers(loc, ID);
        markers.add(ID);
    }

    public void removeSpawnedMarkers(){
        if(markers.size() > 0) sendPacketToPlayers(getMarkerDestroyPacket());
        markers.clear();
    }

    public boolean isInBounds(Location loc){
        return minX <= loc.getBlockX() && maxX >= loc.getBlockX()
                && minY <= loc.getBlockY() && maxY >= loc.getBlockY()
                && minZ <= loc.getBlockZ() && maxZ >= loc.getBlockZ();

    }

    private WrapperPlayServerEntityDestroy getMarkerDestroyPacket(){
        WrapperPlayServerEntityDestroy destroyPacket = new WrapperPlayServerEntityDestroy();
        destroyPacket.setEntityIds(markers);
        return destroyPacket;
    }

    private WrapperPlayServerSpawnEntityLiving getMarkerSpawnPacket(Location loc, int entityId){
        WrapperPlayServerSpawnEntityLiving spawnEntityPacket = new WrapperPlayServerSpawnEntityLiving();
        spawnEntityPacket.setUniqueId(UUID.randomUUID());
        spawnEntityPacket.setX(loc.getBlockX() + 0.5);
        spawnEntityPacket.setY(loc.getBlockY());
        spawnEntityPacket.setZ(loc.getBlockZ() + 0.5);
        spawnEntityPacket.setType(WrapperPlayServerSpawnEntityLiving.ENTITY_TYPE.MAGMA_CUBE);
        spawnEntityPacket.setEntityID(entityId);
        return spawnEntityPacket;
    }

    private WrapperPlayServerEntityMetadata getModifyMarkerPacket(int entityId, boolean glowing){
        WrapperPlayServerEntityMetadata entityMetadataPacket = new WrapperPlayServerEntityMetadata();
        entityMetadataPacket.setEntityId(entityId);
        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);
        WrappedDataWatcher.Serializer intSerializer = WrappedDataWatcher.Registry.get(Integer.class);
        watcher.setObject(0, byteSerializer, (byte)((glowing ? 0x40 : 0) | 0x20));
        watcher.setObject(16, intSerializer, 2);
        entityMetadataPacket.setEntityMetadata(watcher.getWatchableObjects());
        return entityMetadataPacket;
    }

    private void sendMarkerSpawnToAllPlayers(Location loc, int entityId){
        sendPacketToPlayers(getMarkerSpawnPacket(loc, entityId));
        sendPacketToPlayers(getModifyMarkerPacket(entityId, true));
    }

    private void sendMarkerSpawnToPlayer(Player p, Location loc, int entityId){
        getMarkerSpawnPacket(loc, entityId).sendPacket(p);
        getModifyMarkerPacket(entityId, true).sendPacket(p);
    }

    public static Location unifyLocation(Location loc){
        return new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 0, 0);
    }

    protected WrapperPlayServerBlockChange getBlockChangePacket(Location loc, Material m){
        WrapperPlayServerBlockChange packet = new WrapperPlayServerBlockChange();
        packet.setLocation(new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        packet.setBlockData(WrappedBlockData.createData(m));
        return packet;
    }

    protected void sendPacketToPlayers(AbstractPacket packet){
        for(UUID uuid : displayPlayers){
            Player p = Bukkit.getPlayer(uuid);
            if(p == null) continue;
            packet.sendPacket(p);
        }
    }

    public void clearForPlayer(Player p){
        for(Location loc : changedBlocks.keySet()){
            getBlockChangePacket(loc, Material.AIR).sendPacket(p);
        }
        getMarkerDestroyPacket().sendPacket(p);
    }

    public void clearDisplay(){
        for(UUID uuid : displayPlayers){
            Player p = Bukkit.getPlayer(uuid);
            if(p == null) continue;
            clearForPlayer(p);
        }
        changedBlocks.clear();
    }

    public ArrayList<UUID> getDisplayPlayers() {
        return displayPlayers;
    }

    public HashMap<Location, Material> getChangedBlocks() {
        return changedBlocks;
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    public Direction getDirection() {
        return direction;
    }

    public void addPlayer(Player p){
        if(displayPlayers.contains(p.getUniqueId())) return;
        displayPlayers.add(p.getUniqueId());
        for(Map.Entry<Location, Material> entry : changedBlocks.entrySet()){
            getBlockChangePacket(entry.getKey(), entry.getValue()).sendPacket(p);
        }
    }

    public void removePlayer(Player p){
        if(!displayPlayers.contains(p.getUniqueId())) return;
        displayPlayers.remove(p.getUniqueId());
        clearForPlayer(p);
    }

    public void display() {
        for(int x = minX; x <= maxX; x++){
            for(int y = minY; y <= maxY; y++){
                for(int z = minZ; z <= maxZ; z++){
                    pos1.getWorld().spawnParticle(Particle.BARRIER, new Location(pos1.getWorld(), x + 0.5, y + 0.5, z + 0.5), 1);
                }
            }
        }
    }

    public void checkAndResendBlock(Location loc){
        if(changedBlocks.containsKey(loc)){
            sendBlockChange(loc,  changedBlocks.get(loc));
        }
    }

    public boolean isAttacked(Location loc){
        return changedBlocks.containsKey(unifyLocation(loc));
    }

    public abstract Location convertWorldToLocalCoordinate(Location loc);

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMaxZ() {
        return maxZ;
    }


}

