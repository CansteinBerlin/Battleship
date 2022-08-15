package net.quickwrite.battleship.commands.debug;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugTestGuiCommand extends SubCommand {

    public DebugTestGuiCommand(BaseCommand parent) {
        super(parent, "testGui", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        //MiniMinigames.getInstance().getGuiManager().createMapSelectionGui(p, null);
        p.sendMessage(Battleship.PREFIX + "§cOpened Gui");
        return true;
    }
}
