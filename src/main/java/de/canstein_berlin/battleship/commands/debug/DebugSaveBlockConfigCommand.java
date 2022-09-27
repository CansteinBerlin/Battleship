package de.canstein_berlin.battleship.commands.debug;

import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.blocks.BattleShipBlocks;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import org.bukkit.command.CommandSender;

public class DebugSaveBlockConfigCommand extends SubCommand {

    public DebugSaveBlockConfigCommand(BaseCommand parent) {
        super(parent, "saveBlockConfig", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        sender.sendMessage(Battleship.PREFIX + "§aSaved Config");
        BattleShipBlocks.saveBlocks(Battleship.getInstance().getBlockConfig());
        return true;
    }
}
