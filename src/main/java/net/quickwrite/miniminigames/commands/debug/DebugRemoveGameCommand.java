package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import org.bukkit.command.CommandSender;

@Deprecated
public class DebugRemoveGameCommand extends SubCommand {

    public DebugRemoveGameCommand(BaseCommand parent) {
        super(parent, "removeGame", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        sender.sendMessage(MiniMinigames.PREFIX + "§cThis command does not work anymore");
        return true;

        /*if(!(sender instanceof Player)) {
            sender.sendMessage(MiniMinigames.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }
        Player p = (Player) sender;
        Game game = MiniMinigames.getInstance().getGameManager().getGame(p);
        if(game == null){
            sender.sendMessage(MiniMinigames.PREFIX + "§cYou don't have a game running");
            return true;
        }
        //MiniMinigames.getInstance().getGameManager().removeGame(game);
        p.sendMessage(MiniMinigames.PREFIX + "§aThe game has been removed");
        return true;

         */
    }
}
