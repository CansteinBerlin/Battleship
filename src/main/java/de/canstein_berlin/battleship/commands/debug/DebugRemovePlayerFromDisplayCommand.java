package de.canstein_berlin.battleship.commands.debug;

import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import de.canstein_berlin.battleship.display.Display;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugRemovePlayerFromDisplayCommand extends SubCommand {
    public DebugRemovePlayerFromDisplayCommand(BaseCommand parent) {
        super(parent, "removePlayerFromDisplay", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Battleship.PREFIX + "§cYou have to be a player");
            return true;
        }
        if(Display.currentTestDisplay == null){
            sender.sendMessage(Battleship.PREFIX + "§cNo Display Available");
            return true;
        }
        Player p = (Player) sender;
        Display.currentTestDisplay.removePlayer(p);
        p.sendMessage(Battleship.PREFIX + "§aRemoved from Display");
        return true;
    }
}
