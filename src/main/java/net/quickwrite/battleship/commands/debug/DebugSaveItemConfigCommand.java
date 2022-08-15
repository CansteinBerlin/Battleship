package net.quickwrite.battleship.commands.debug;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import net.quickwrite.battleship.items.BattleshipItems;
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
