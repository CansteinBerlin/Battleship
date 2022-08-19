package net.quickwrite.battleship.commands.battleship;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import net.quickwrite.battleship.game.Game;
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
            Game game = Battleship.getInstance().getGameManager().getGame(p);
            if(game == null){
                p.sendMessage(Battleship.PREFIX + Battleship.getLang("command.accept.noGame"));
                return true;
            }
            if(!game.isMissingPlayer(p)){
                p.sendMessage(Battleship.PREFIX + Battleship.getLang("command.accept.noAcceptOwnGame"));
                return true;
            }
            if(game.isStarted()){
                p.sendMessage(Battleship.PREFIX + Battleship.getLang("command.accept.isStarted"));
                return true;
            }
            Battleship.getInstance().getGameManager().stopCountdown(game);
            game.accept(p);
        }else{
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.noPlayer"));
        }
        return true;
    }
}
