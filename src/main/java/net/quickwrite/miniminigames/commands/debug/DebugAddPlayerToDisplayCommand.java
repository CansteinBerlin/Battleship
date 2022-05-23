package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.display.Display;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugAddPlayerToDisplayCommand extends SubCommand {

    public DebugAddPlayerToDisplayCommand(BaseCommand parent) {
        super(parent, "addPlayerToDisplay", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(MiniMinigames.PREFIX + "§cYou have to be a player");
            return true;
        }
        if(Display.currentTestDisplay == null){
            sender.sendMessage(MiniMinigames.PREFIX + "§cNo Display Available");
            return true;
        }
        Player p = (Player) sender;
        Display.currentTestDisplay.addPlayer(p);
        p.sendMessage(MiniMinigames.PREFIX + "§aAdded to Display");
        return true;
    }
}
