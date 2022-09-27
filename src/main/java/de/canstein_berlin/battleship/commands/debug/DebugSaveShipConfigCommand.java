package de.canstein_berlin.battleship.commands.debug;

import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import de.canstein_berlin.battleship.ships.ShipManager;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DebugSaveShipConfigCommand extends SubCommand {

    public DebugSaveShipConfigCommand(BaseCommand parent) {
        super(parent, "saveShipConfig", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        sender.sendMessage(Battleship.PREFIX + "§aSaved Config");
        ShipManager.saveShips(Battleship.getInstance().getShipConfig());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return super.tabComplete(sender, args);
    }
}
