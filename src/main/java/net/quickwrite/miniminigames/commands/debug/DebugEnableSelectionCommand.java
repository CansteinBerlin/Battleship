package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.map.Selection;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.ArrayList;
import java.util.HashMap;

public class DebugEnableSelectionCommand extends SubCommand {

    public static final HashMap<Player, Selection> selections = new HashMap<>();
    public static final ArrayList<Player> selectingPlayers = new ArrayList<>();

    public DebugEnableSelectionCommand(BaseCommand parent) {
        super(parent, "enableSelection", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            selections.put(p, new Selection());
            selectingPlayers.add(p);
            sender.sendMessage(MiniMinigames.PREFIX + "§aYou created a new Selection");
            sender.sendMessage(MiniMinigames.PREFIX + "§aRightClick for first Location");
            sender.sendMessage(MiniMinigames.PREFIX + "§aLeftClick for second Location");
        }else{
            sender.sendMessage(MiniMinigames.PREFIX + "§cYou have to be a player to use this command");
        }

        return true;
    }

    public static void modifySelection(Player p, Location loc, Action action){
        if(action.equals(Action.RIGHT_CLICK_BLOCK)){
            selections.get(p).setPos1(loc);
        }else{
            selections.get(p).setPos2(loc);
        }

        if(selections.get(p).isFinished()){
            selectingPlayers.remove(p);
            p.sendMessage("Finished Selection");
        }
    }
}
