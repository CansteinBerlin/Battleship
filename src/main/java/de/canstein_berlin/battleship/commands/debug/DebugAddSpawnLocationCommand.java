package de.canstein_berlin.battleship.commands.debug;

import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugAddSpawnLocationCommand extends SubCommand {

    public DebugAddSpawnLocationCommand(BaseCommand parent) {
        super(parent, "addSpawnLocation", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Battleship.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }

        Player p = (Player) sender;
        DebugCreateMapCommand.addSpawnLocation(p.getLocation());
        p.sendMessage(Battleship.PREFIX + "§aAdded new SpawnLocation");
        return true;
    }
}
