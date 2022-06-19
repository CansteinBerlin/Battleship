package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.ships.Ship;
import net.quickwrite.miniminigames.ships.ShipManager;
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
