package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.display.HorizontalDisplay;
import net.quickwrite.miniminigames.display.VerticalDisplay;
import net.quickwrite.miniminigames.map.Selection;
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
            sender.sendMessage(MiniMinigames.PREFIX + "§cYou have to be a player");
            return true;
        }
        Player p = (Player) sender;
        HashMap<Player, Selection> selections = DebugEnableSelectionCommand.selections;
        if(!selections.containsKey(p)){
            p.sendMessage(MiniMinigames.PREFIX + "§cYou don't have a selection");
            return true;
        }
        Selection sel = selections.get(p);
        if(!sel.isFinished()){
            p.sendMessage(MiniMinigames.PREFIX + "§cUnfinished Selection");
            return true;
        }
        if(sel.pos1.getBlockY() == sel.pos2.getBlockY()){
            HorizontalDisplay display = new HorizontalDisplay(sel.pos1, sel.pos2);
            display.display();
            DebugCreateMapCommand.addHorizontalDisplay(display);
            p.sendMessage(MiniMinigames.PREFIX + "§aAdded Horizontal Display");
        }else{
            VerticalDisplay verticalDisplay = new VerticalDisplay(sel.pos1, sel.pos2);
            verticalDisplay.display();
            DebugCreateMapCommand.addVerticalDisplay(verticalDisplay);
            p.sendMessage(MiniMinigames.PREFIX + "§aAdded Vertical Display");
        }

        return true;
    }
}
