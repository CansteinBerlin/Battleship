package de.canstein_berlin.battleship.commands.debug;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import de.canstein_berlin.battleship.packetwrapper.WrapperPlayServerEntityMetadata;
import de.canstein_berlin.battleship.packetwrapper.WrapperPlayServerSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DebugSpawnMarkerCommand extends SubCommand {

    public DebugSpawnMarkerCommand(BaseCommand parent) {
        super(parent, "spawnMarker", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Battleship.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }

        Player p = (Player)sender;

        /*int i = 0;
        for(EntityType e : EntityType.values()){
            try{
                //Marker Entity Spawn
                int ID = (int)(Math.random() * Integer.MAX_VALUE);
                WrapperPlayServerSpawnEntityLiving spawnEntityPacket = new WrapperPlayServerSpawnEntityLiving();
                spawnEntityPacket.setUniqueId(UUID.randomUUID());
                spawnEntityPacket.setX(p.getLocation().getBlockX() + 0.5 + i * 2);
                spawnEntityPacket.setY(p.getLocation().getBlockY());
                spawnEntityPacket.setZ(p.getLocation().getBlockZ() + 0.5);
                spawnEntityPacket.setType(e);
                System.out.println(spawnEntityPacket.getType());
                spawnEntityPacket.setEntityID(ID);
                spawnEntityPacket.sendPacket(p);

                WrapperPlayServerEntityMetadata entityMetadata = new WrapperPlayServerEntityMetadata();
                entityMetadata.setEntityId(ID);
                WrappedDataWatcher watcher = new WrappedDataWatcher();
                WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);
                WrappedDataWatcher.Serializer intSerializer = WrappedDataWatcher.Registry.get(Integer.class);
                watcher.setObject(0, byteSerializer, (byte)(0x40 | 0x20)); //
                watcher.setObject(16, intSerializer, 2);
                entityMetadata.setEntityMetadata(watcher.getWatchableObjects());
                entityMetadata.sendPacket(p);

                //ArmorStand
                ArmorStand stand = p.getLocation().getWorld().spawn(p.getLocation().add(i * 2, 0, 0), ArmorStand.class);
                stand.setCustomName(i + "");
                stand.setCustomNameVisible(true);
                stand.setInvisible(true);
                stand.setGravity(false);
                i++;

            }catch (Exception _){

            }
        }

         */

        int ID = (int)(Math.random() * Integer.MAX_VALUE);
        WrapperPlayServerSpawnEntityLiving spawnEntityPacket = new WrapperPlayServerSpawnEntityLiving();
        spawnEntityPacket.setUniqueId(UUID.randomUUID());
        spawnEntityPacket.setX(p.getLocation().getBlockX() + 0.5);
        spawnEntityPacket.setY(p.getLocation().getBlockY());
        spawnEntityPacket.setZ(p.getLocation().getBlockZ() + 0.5);
        spawnEntityPacket.setType(EntityType.MAGMA_CUBE);
        spawnEntityPacket.setEntityID(ID);
        spawnEntityPacket.sendPacket(p);

        WrapperPlayServerEntityMetadata entityMetadata = new WrapperPlayServerEntityMetadata();
        entityMetadata.setEntityId(ID);
        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);
        WrappedDataWatcher.Serializer intSerializer = WrappedDataWatcher.Registry.get(Integer.class);
        watcher.setObject(0, byteSerializer, (byte)(0x40 | 0x20)); //
        watcher.setObject(16, intSerializer, 2);
        entityMetadata.setEntityMetadata(watcher.getWatchableObjects());
        entityMetadata.sendPacket(p);

        return true;
    }
}
