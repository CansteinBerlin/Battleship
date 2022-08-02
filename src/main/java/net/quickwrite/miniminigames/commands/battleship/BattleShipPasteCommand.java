package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.display.Display;
import net.quickwrite.miniminigames.display.HorizontalDisplay;
import net.quickwrite.miniminigames.display.VerticalDisplay;
import net.quickwrite.miniminigames.map.Map;
import net.quickwrite.miniminigames.map.MapDefinition;
import net.quickwrite.miniminigames.util.DebugMessage;
import org.bukkit.Bukkit;
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
            sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.noPlayer"));
            return true;
        }

        Player p = ((Player) sender);

        if(!BattleShipCopyMapDefinitionCommand.MAP_DEFINITION_HASH_MAP.containsKey(p)){
            p.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.paste.noMap"));
            return true;
        }

        if(args.length != 1){
            p.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.paste.invalidCommand", "command", getCommandHistory()));
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

        if(!MiniMinigames.getInstance().getMapManager().addNewMap(args[0], map)){
            sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.paste.alreadyMap", "map", args[0]));
            return true;
        }

        sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.paste.createdMap", "map", args[0]));
        return true;
    }
}
