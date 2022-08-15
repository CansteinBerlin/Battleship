package net.quickwrite.battleship.commands.debug;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import net.quickwrite.battleship.display.Display;
import net.quickwrite.battleship.map.Selection;
import net.quickwrite.battleship.display.VerticalDisplay;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugAddVerticalDisplayCommand extends SubCommand {

    public DebugAddVerticalDisplayCommand(BaseCommand parent) {
        super(parent, "addVerticalDisplay", null);
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
            VerticalDisplay verticalDisplay = new VerticalDisplay(selection.pos1, selection.pos2);
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
