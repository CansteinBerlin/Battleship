package net.quickwrite.miniminigames.commands.debug;

import com.google.common.collect.ImmutableMap;
import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.builder.items.ItemBuilder;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.display.HorizontalDisplay;
import net.quickwrite.miniminigames.display.VerticalDisplay;
import net.quickwrite.miniminigames.map.Map;
import net.quickwrite.miniminigames.ships.Ship;
import net.quickwrite.miniminigames.ships.ShipManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class DebugCreateMapCommand extends SubCommand {

    private static VerticalDisplay attackerVerticalDisplay, defenderVerticalDisplay;
    private static HorizontalDisplay attackerHorizontalDisplay, defenderHorizontalDisplay;
    private static Location attackerSpawnLocation, defenderSpawnLocation;

    public DebugCreateMapCommand(BaseCommand parent) {
        super(parent, "createMap", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(args.length != 1) {
            sender.sendMessage(MiniMinigames.PREFIX + "§cPlease use §6/" + getCommandHistory() + " <name>");
            return true;
        }
        String name = args[0];
        if(attackerHorizontalDisplay == null || defenderHorizontalDisplay == null || attackerVerticalDisplay == null || defenderVerticalDisplay == null){
            sender.sendMessage(MiniMinigames.PREFIX + "§cPlease define all 4 Displays");
            return true;
        }

        if(attackerSpawnLocation == null || defenderSpawnLocation == null){
            sender.sendMessage(MiniMinigames.PREFIX + "§cPlease define both spawnLocations");
            return true;
        }

        if(!Map.isValid(attackerVerticalDisplay, defenderVerticalDisplay, attackerHorizontalDisplay, defenderHorizontalDisplay)){
            sender.sendMessage(MiniMinigames.PREFIX + "§cInvalid display Sizes");
            return true;
        }
        Map map = new Map(attackerVerticalDisplay, defenderVerticalDisplay, attackerHorizontalDisplay, defenderHorizontalDisplay,
                Material.STRUCTURE_BLOCK, name, attackerSpawnLocation, defenderSpawnLocation,
                new ImmutableMap.Builder<Ship, Integer>()
                        .put(ShipManager.getShipWithSize(2), 4)
                        .put(ShipManager.getShipWithSize(3), 3)
                        .put(ShipManager.getShipWithSize(4), 2)
                        .put(ShipManager.getShipWithSize(5), 1)
                        .build()
        );

        if(!MiniMinigames.getInstance().getMapManager().addNewMap(name, map)){
            sender.sendMessage(MiniMinigames.PREFIX + "§cThere is already a map with the specific name");
            return true;
        }

        attackerVerticalDisplay = null;
        defenderVerticalDisplay = null;
        attackerHorizontalDisplay = null;
        defenderHorizontalDisplay = null;
        attackerSpawnLocation = null;
        defenderSpawnLocation = null;

        sender.sendMessage(MiniMinigames.PREFIX + "§aCreated new Map");
        return true;
    }

    public static void addVerticalDisplay(VerticalDisplay display){
        if(attackerVerticalDisplay == null) attackerVerticalDisplay = display;
        else defenderVerticalDisplay = display;
    }

    public static void addHorizontalDisplay(HorizontalDisplay display){
        if(attackerHorizontalDisplay == null) attackerHorizontalDisplay = display;
        else defenderHorizontalDisplay = display;
    }

    private static Location unifyLocation(Location loc){
        return new Location(loc.getWorld(), loc.getBlockX() + 0.5, loc.getBlockY() + 0.5, loc.getBlockZ() + 0.5, loc.getYaw(), loc.getPitch());
    }

    public static void addSpawnLocation(Location loc){
        if(attackerSpawnLocation == null) attackerSpawnLocation = unifyLocation(loc);
        else defenderSpawnLocation = unifyLocation(loc);
    }
}
