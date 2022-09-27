package main.java.de.canstein_berlin.battleship.commands.debug;

import main.java.de.canstein_berlin.battleship.Battleship;
import main.java.de.canstein_berlin.battleship.commandsystem.BaseCommand;
import main.java.de.canstein_berlin.battleship.commandsystem.SubCommand;
import main.java.de.canstein_berlin.battleship.items.BattleshipItems;
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
