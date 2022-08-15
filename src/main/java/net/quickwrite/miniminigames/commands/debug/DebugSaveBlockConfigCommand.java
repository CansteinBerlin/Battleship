package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.Battleship;
import net.quickwrite.miniminigames.blocks.BattleShipBlocks;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
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
