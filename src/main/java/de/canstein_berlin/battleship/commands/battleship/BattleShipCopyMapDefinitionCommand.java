package de.canstein_berlin.battleship.commands.battleship;

import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import de.canstein_berlin.battleship.display.Display;
import de.canstein_berlin.battleship.map.Map;
import de.canstein_berlin.battleship.map.MapDefinition;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class BattleShipCopyMapDefinitionCommand extends SubCommand {

    public static final HashMap<Player, MapDefinition> MAP_DEFINITION_HASH_MAP = new HashMap<>();


    public BattleShipCopyMapDefinitionCommand(BaseCommand parent) {
        super(parent, "copyMapDefinition", "battleship.copyMapDefinition");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.noPlayer"));
            return true;
        }
        Player p = ((Player) sender);

        if(args.length != 1){
            p.sendMessage(Battleship.PREFIX + Battleship.getLang("command.copyMapDefinition.invalidCommand", "command", getCommandHistory()));
            return true;
        }

        Map copy = Battleship.getInstance().getMapManager().loadMap(args[0]);

        if(copy == null){
            p.sendMessage(Battleship.PREFIX + Battleship.getLang("command.unknownMap", "map", args[0]));
            return true;
        }

        MapDefinition mapDefinition = copy.createMapDefinition(Display.unifyLocation(p.getLocation()));
        MAP_DEFINITION_HASH_MAP.put(p, mapDefinition);
        p.sendMessage(Battleship.PREFIX + Battleship.getLang("command.copyMapDefinition.copied"));

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length > 1) return new ArrayList<>();

        return Battleship.getInstance().getMapManager().getMaps().stream()
                .filter(map -> map.startsWith(args[0].toLowerCase(Locale.ROOT)))
                .sorted()
                .collect(Collectors.toList());
    }
}
