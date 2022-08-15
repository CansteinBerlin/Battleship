package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.Battleship;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
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
