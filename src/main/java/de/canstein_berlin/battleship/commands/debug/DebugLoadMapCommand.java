package de.canstein_berlin.battleship.commands.debug;

import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import de.canstein_berlin.battleship.map.Map;
import org.bukkit.command.CommandSender;

public class DebugLoadMapCommand extends SubCommand {

    public DebugLoadMapCommand(BaseCommand parent) {
        super(parent, "loadMap", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(args.length != 1){
            sender.sendMessage(Battleship.PREFIX + "§cPlease use §6/" + getCommandHistory() + " <name>");
            return true;
        }
        Map map = Battleship.getInstance().getMapManager().loadMap(args[0]);
        if(map == null){
            sender.sendMessage(Battleship.PREFIX + "§cUnknown Map with name: " + args[0]);
            return true;
        }
        map.displayAll();

        return true;
    }
}
