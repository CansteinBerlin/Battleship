package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugAddSpawnLocationCommand extends SubCommand {

    public DebugAddSpawnLocationCommand(BaseCommand parent) {
        super(parent, "addSpawnLocation", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(MiniMinigames.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }

        Player p = (Player) sender;
        DebugCreateMapCommand.addSpawnLocation(p.getLocation());
        p.sendMessage(MiniMinigames.PREFIX + "§aAdded new SpawnLocation");
        return true;
    }
}
