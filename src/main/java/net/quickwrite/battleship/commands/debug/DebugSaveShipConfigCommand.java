package net.quickwrite.battleship.commands.debug;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import net.quickwrite.battleship.ships.ShipManager;
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
