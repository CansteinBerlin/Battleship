package de.canstein_berlin.battleship.commands.debug;

import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import de.canstein_berlin.battleship.display.HorizontalDisplay;
import de.canstein_berlin.battleship.display.VerticalDisplay;
import de.canstein_berlin.battleship.map.Selection;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class DebugAcceptDisplayCommand extends SubCommand {

    public DebugAcceptDisplayCommand(BaseCommand parent) {
        super(parent, "acceptDisplay", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Battleship.PREFIX + "§cYou have to be a player");
            return true;
        }
        Player p = (Player) sender;
        HashMap<Player, Selection> selections = DebugEnableSelectionCommand.selections;
        if(!selections.containsKey(p)){
            p.sendMessage(Battleship.PREFIX + "§cYou don't have a selection");
            return true;
        }
        Selection sel = selections.get(p);
        if(!sel.isFinished()){
            p.sendMessage(Battleship.PREFIX + "§cUnfinished Selection");
            return true;
        }
        if(sel.pos1.getBlockY() == sel.pos2.getBlockY()){
            HorizontalDisplay display = new HorizontalDisplay(sel.pos1, sel.pos2);
            display.display();
            DebugCreateMapCommand.addHorizontalDisplay(display);
            p.sendMessage(Battleship.PREFIX + "§aAdded Horizontal Display");
        }else{
            VerticalDisplay verticalDisplay = new VerticalDisplay(sel.pos1, sel.pos2);
            verticalDisplay.display();
            DebugCreateMapCommand.addVerticalDisplay(verticalDisplay);
            p.sendMessage(Battleship.PREFIX + "§aAdded Vertical Display");
        }

        return true;
    }
}
