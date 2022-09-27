package main.java.de.canstein_berlin.battleship.commands.debug;

import main.java.de.canstein_berlin.battleship.commandsystem.BaseCommand;
import main.java.de.canstein_berlin.battleship.commandsystem.SubCommand;
import main.java.de.canstein_berlin.battleship.ships.Ship;
import main.java.de.canstein_berlin.battleship.ships.ShipManager;
import org.bukkit.command.CommandSender;

public class DebugListShipsCommand extends SubCommand {

    public DebugListShipsCommand(BaseCommand parent) {
        super(parent, "listShips", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        for(Ship ship : ShipManager.ships){
            sender.sendMessage(ship + "");
        }

        return true;
    }
}
