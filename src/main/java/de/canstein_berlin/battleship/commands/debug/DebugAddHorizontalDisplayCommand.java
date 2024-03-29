package de.canstein_berlin.battleship.commands.debug;

import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import de.canstein_berlin.battleship.display.Display;
import de.canstein_berlin.battleship.display.HorizontalDisplay;
import de.canstein_berlin.battleship.map.Selection;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugAddHorizontalDisplayCommand extends SubCommand {
    public DebugAddHorizontalDisplayCommand(BaseCommand parent) {
        super(parent, "addHorizontalDisplay", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Battleship.PREFIX + "§cYou have to be a player");
            return true;
        }
        Player p = (Player) sender;
        if(DebugEnableSelectionCommand.selections.containsKey(p) && DebugEnableSelectionCommand.selections.get(p).isFinished()){
            Selection selection = DebugEnableSelectionCommand.selections.get(p);
            HorizontalDisplay verticalDisplay = new HorizontalDisplay(selection.pos1, selection.pos2);
            p.sendMessage(Battleship.PREFIX + "§aCreated Vertical Selection with Height " + verticalDisplay.getHeight() + " and Width " + verticalDisplay.getWidth());
            p.sendMessage(Battleship.PREFIX + "§aDirection: " + verticalDisplay.getDirection());
            verticalDisplay.display();
            Display.currentTestDisplay = verticalDisplay;
        }else{
            p.sendMessage(Battleship.PREFIX + "§cNo or invalid Selection");
        }

        return true;
    }
}
