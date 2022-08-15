package net.quickwrite.battleship.commands.debug;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.blocks.BattleShipBlocks;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
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
