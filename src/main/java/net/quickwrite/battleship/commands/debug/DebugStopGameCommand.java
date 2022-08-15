package net.quickwrite.battleship.commands.debug;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import net.quickwrite.battleship.game.Game;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugStopGameCommand extends SubCommand {

    public DebugStopGameCommand(BaseCommand parent) {
        super(parent, "stopGameTasks", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Battleship.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }
        Player p = (Player) sender;
        Game game = Battleship.getInstance().getGameManager().getGame(p);
        if(game == null) {
            sender.sendMessage(Battleship.PREFIX + "§cYou don't have a game running");
            return true;
        }
        game.playerLeft(p);
        p.sendMessage(Battleship.PREFIX + "§aThe Tasks have been stopped");
        return true;
    }
}
