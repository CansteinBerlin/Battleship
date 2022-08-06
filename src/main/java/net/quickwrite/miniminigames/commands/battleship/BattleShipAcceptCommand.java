package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.game.Game;
import net.quickwrite.miniminigames.util.DebugMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BattleShipAcceptCommand extends SubCommand {

    public BattleShipAcceptCommand(BaseCommand parent) {
        super(parent, "accept", "battleship.accept");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            Game game = MiniMinigames.getInstance().getGameManager().getGame(p);
            if(game == null){
                p.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.accept.noGame"));
                return true;
            }
            if(!game.isMissingPlayer(p)){
                p.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.accept.noAcceptOwnGame"));
                return true;
            }
            MiniMinigames.getInstance().getGameManager().stopCountdown(game);
            game.accept(p);
        }else{
            sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.noPlayer"));
        }
        return true;
    }
}
