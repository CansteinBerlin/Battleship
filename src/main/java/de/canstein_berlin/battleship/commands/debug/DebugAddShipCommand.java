package main.java.de.canstein_berlin.battleship.commands.debug;

import main.java.de.canstein_berlin.battleship.Battleship;
import main.java.de.canstein_berlin.battleship.commandsystem.BaseCommand;
import main.java.de.canstein_berlin.battleship.commandsystem.SubCommand;
import main.java.de.canstein_berlin.battleship.ships.Ship;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class DebugAddShipCommand extends SubCommand {

    public DebugAddShipCommand(BaseCommand parent) {
        super(parent, "addShip", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (args.length == 1){
            try {
                int size = Integer.parseInt(args[0]);
                new Ship(size, Material.GREEN_DYE, "default.ship.place", "default.ship.hit", "CustomShip:" + size, "size_" + args[0]);
                sender.sendMessage(Battleship.PREFIX + "§aAdded Ship with size " + size);
                return true;
            }catch (NumberFormatException e){
                sender.sendMessage(e.toString());
            }
        }
        sender.sendMessage(Battleship.PREFIX + "§cCould not add Ship");
        return true;
    }
}
