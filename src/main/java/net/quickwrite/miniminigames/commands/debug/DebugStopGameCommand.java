package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.game.Game;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugStopGameCommand extends SubCommand {

    public DebugStopGameCommand(BaseCommand parent) {
        super(parent, "stopGameTasks", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(MiniMinigames.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }
        Player p = (Player) sender;
        Game game = MiniMinigames.getInstance().getGameManager().getGame(p);
        if(game == null) {
            sender.sendMessage(MiniMinigames.PREFIX + "§cYou don't have a game running");
            return true;
        }
        game.playerLeft(p);
        p.sendMessage(MiniMinigames.PREFIX + "§aThe Tasks have been stopped");
        return true;
    }
}
