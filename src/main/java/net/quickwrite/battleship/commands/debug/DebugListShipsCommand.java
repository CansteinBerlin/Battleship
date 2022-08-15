package net.quickwrite.battleship.commands.debug;

import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import net.quickwrite.battleship.ships.Ship;
import net.quickwrite.battleship.ships.ShipManager;
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
