package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.display.Display;
import net.quickwrite.miniminigames.map.Selection;
import net.quickwrite.miniminigames.display.VerticalDisplay;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugAddVerticalDisplayCommand extends SubCommand {

    public DebugAddVerticalDisplayCommand(BaseCommand parent) {
        super(parent, "addVerticalDisplay", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(MiniMinigames.PREFIX + "§cYou have to be a player");
            return true;
        }
        Player p = (Player) sender;
        if(DebugEnableSelectionCommand.selections.containsKey(p) && DebugEnableSelectionCommand.selections.get(p).isFinished()){
            Selection selection = DebugEnableSelectionCommand.selections.get(p);
            VerticalDisplay verticalDisplay = new VerticalDisplay(selection.pos1, selection.pos2);
            p.sendMessage(MiniMinigames.PREFIX + "§aCreated Vertical Selection with Height " + verticalDisplay.getHeight() + " and Width " + verticalDisplay.getWidth());
            p.sendMessage(MiniMinigames.PREFIX + "§aDirection: " + verticalDisplay.getDirection());
            verticalDisplay.display();
            Display.currentTestDisplay = verticalDisplay;
        }else{
            p.sendMessage(MiniMinigames.PREFIX + "§cNo or invalid Selection");
        }

        return true;
    }
}
