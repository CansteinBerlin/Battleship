package net.quickwrite.miniminigames.commands.test;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.ships.ShipManager;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DebugSaveCommand extends SubCommand {

    public DebugSaveCommand(BaseCommand parent) {
        super(parent, "saveShipConfig", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        sender.sendMessage(MiniMinigames.PREFIX + "§aSaved Config");
        ShipManager.saveShips(MiniMinigames.getInstance().getShipConfig());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return super.tabComplete(sender, args);
    }
}
