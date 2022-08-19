package net.quickwrite.battleship.commands.battleship;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import net.quickwrite.battleship.display.Display;
import net.quickwrite.battleship.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BattleshipSetGlobalRespawnPoint extends SubCommand {

    public BattleshipSetGlobalRespawnPoint(BaseCommand parent) {
        super(parent, "setGlobalRespawnPoint", "battleship.setGlobalRespawnPoint");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.noPlayer"));
            return true;
        }

        Player p = ((Player) sender);

        if(args.length == 0){
            //Set Spawnpoint
            Location loc = Display.unifyLocation(p.getLocation());
            Game.SPAWN_POINT = loc;
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.setGlobalRespawnPoint.setSpawnPoint"));
        }else{
            if(args.length != 1){
                sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.setGlobalRespawnPoint.invalidCommand", "command", getCommandHistory()));
                return true;
            }
            //Reset SpawnPoint
            if(!args[0].equalsIgnoreCase("reset")){
                sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.setGlobalRespawnPoint.invalidCommand", "command", getCommandHistory()));
                return true;
            }
            Game.SPAWN_POINT = null;
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.setGlobalRespawnPoint.resetSpawnPoint"));
        }

        Battleship.getInstance().getDefaultConfig().getConfig().set("spawnpoint", Game.SPAWN_POINT);
        Battleship.getInstance().getDefaultConfig().saveConfig();

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length > 1) return new ArrayList<>();
        return Stream.of("reset")
                .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(args[0].toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
    }
}
