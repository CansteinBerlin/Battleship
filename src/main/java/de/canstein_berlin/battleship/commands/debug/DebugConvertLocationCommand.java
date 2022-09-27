package main.java.de.canstein_berlin.battleship.commands.debug;

import main.java.de.canstein_berlin.battleship.Battleship;
import main.java.de.canstein_berlin.battleship.commandsystem.BaseCommand;
import main.java.de.canstein_berlin.battleship.commandsystem.SubCommand;
import main.java.de.canstein_berlin.battleship.display.Display;
import main.java.de.canstein_berlin.battleship.ships.ShipContainer;
import main.java.de.canstein_berlin.battleship.ships.ShipManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class DebugConvertLocationCommand extends SubCommand {

    public DebugConvertLocationCommand(BaseCommand parent) {
        super(parent, "convertLocation", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Battleship.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }

        if(Display.currentTestDisplay == null){
            sender.sendMessage(Battleship.PREFIX + "§cNo Display defined");
            return true;
        }
        Player p = (Player) sender;
        Display d = Display.currentTestDisplay;
        if(!d.isInBounds(p.getLocation())){
            p.sendMessage(Battleship.PREFIX + "§cOut of display Bounds");
            return true;
        }
        ShipContainer container = new ShipContainer(ShipManager.getShipWithSize(2), new ArrayList<>());
        container.registerShipLocation(p.getLocation());
        container.convertLocations(d);
        p.sendMessage(Battleship.PREFIX + "Converted Locations: " + container.getPlacedLocations());
        return true;
    }
}
