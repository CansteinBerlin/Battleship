package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.items.BattleshipItems;
import org.bukkit.command.CommandSender;

public class DebugSaveItemConfigCommand extends SubCommand {

    public DebugSaveItemConfigCommand(BaseCommand parent) {
        super(parent, "saveItemConfig", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        sender.sendMessage(MiniMinigames.PREFIX + "§aSaved Config");
        BattleshipItems.saveItems(MiniMinigames.getInstance().getItemConfig());
        return true;
    }
}
