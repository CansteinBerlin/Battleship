package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.display.Display;
import net.quickwrite.miniminigames.map.Map;
import net.quickwrite.miniminigames.map.MapDefinition;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BattleShipCopyMapDefinitionCommand extends SubCommand {

    public static final HashMap<Player, MapDefinition> MAP_DEFINITION_HASH_MAP = new HashMap<>();


    public BattleShipCopyMapDefinitionCommand(BaseCommand parent) {
        super(parent, "copyMapDefinition", "battleship.copyMapDefinition");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.noPlayer"));
            return true;
        }
        Player p = ((Player) sender);

        if(args.length != 1){
            p.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.copyMapDefinition.invalidCommand", "command", getCommandHistory()));
            return true;
        }

        Map copy = MiniMinigames.getInstance().getMapManager().loadMap(args[0]);

        if(copy == null){
            p.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.unknownMap", "map", args[0]));
            return true;
        }

        MapDefinition mapDefinition = copy.createMapDefinition(Display.unifyLocation(p.getLocation()));
        MAP_DEFINITION_HASH_MAP.put(p, mapDefinition);
        p.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.copyMapDefinition.copied"));

        return true;
    }
}
