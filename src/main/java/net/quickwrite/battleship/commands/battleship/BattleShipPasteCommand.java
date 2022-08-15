package net.quickwrite.battleship.commands.battleship;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import net.quickwrite.battleship.display.Display;
import net.quickwrite.battleship.display.HorizontalDisplay;
import net.quickwrite.battleship.display.VerticalDisplay;
import net.quickwrite.battleship.map.Map;
import net.quickwrite.battleship.map.MapDefinition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BattleShipPasteCommand extends SubCommand {


    public BattleShipPasteCommand(BaseCommand parent) {
        super(parent, "pasteMapDefinition", "battleship.pasteMapDefinition");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.noPlayer"));
            return true;
        }

        Player p = ((Player) sender);

        if(!BattleShipCopyMapDefinitionCommand.MAP_DEFINITION_HASH_MAP.containsKey(p)){
            p.sendMessage(Battleship.PREFIX + Battleship.getLang("command.paste.noMap"));
            return true;
        }

        if(args.length != 1){
            p.sendMessage(Battleship.PREFIX + Battleship.getLang("command.paste.invalidCommand", "command", getCommandHistory()));
            return true;
        }

        MapDefinition definition = BattleShipCopyMapDefinitionCommand.MAP_DEFINITION_HASH_MAP.get(p).clone();
        Location loc = Display.unifyLocation(p.getLocation());

        HorizontalDisplay attackerHorizontal = new HorizontalDisplay(definition.getAttackerP1().add(loc), definition.getAttackerP2().add(loc));
        HorizontalDisplay defenderHorizontal = new HorizontalDisplay(definition.getDefenderP1().add(loc), definition.getDefenderP2().add(loc));

        VerticalDisplay attackerVertical = new VerticalDisplay(definition.getAttackerP3().add(loc), definition.getAttackerP4().add(loc));
        VerticalDisplay defenderVertical = new VerticalDisplay(definition.getDefenderP3().add(loc), definition.getDefenderP4().add(loc));

        Map map = new Map(attackerVertical, defenderVertical, attackerHorizontal, defenderHorizontal,
                Material.STRUCTURE_BLOCK, args[0], definition.getAttackerSpawn().add(loc), definition.getDefenderSpawn().add(loc), definition.getShips());

        map.displayAll();

        if(!Battleship.getInstance().getMapManager().addNewMap(args[0], map)){
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.paste.alreadyMap", "map", args[0]));
            return true;
        }

        sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.paste.createdMap", "map", args[0]));
        return true;
    }
}
