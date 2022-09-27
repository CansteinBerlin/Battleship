package de.canstein_berlin.battleship.commands.debug;

import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import de.canstein_berlin.battleship.items.BattleshipItems;
import org.bukkit.command.CommandSender;

public class DebugSaveItemConfigCommand extends SubCommand {

    public DebugSaveItemConfigCommand(BaseCommand parent) {
        super(parent, "saveItemConfig", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        sender.sendMessage(Battleship.PREFIX + "§aSaved Config");
        BattleshipItems.saveItems(Battleship.getInstance().getItemConfig());
        return true;
    }
}
