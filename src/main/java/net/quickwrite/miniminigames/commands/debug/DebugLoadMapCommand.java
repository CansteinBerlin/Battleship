package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.map.Map;
import org.bukkit.command.CommandSender;

public class DebugLoadMapCommand extends SubCommand {

    public DebugLoadMapCommand(BaseCommand parent) {
        super(parent, "loadMap", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(args.length != 1){
            sender.sendMessage(MiniMinigames.PREFIX + "§cPlease use §6/" + getCommandHistory() + " <name>");
            return true;
        }
        Map map = MiniMinigames.getInstance().getMapManager().loadMap(args[0]);
        if(map == null){
            sender.sendMessage(MiniMinigames.PREFIX + "§cUnknown Map with name: " + args[0]);
            return true;
        }
        map.displayAll();

        return true;
    }
}
