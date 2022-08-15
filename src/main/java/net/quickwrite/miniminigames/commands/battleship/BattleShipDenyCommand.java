package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.Battleship;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.game.Game;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BattleShipDenyCommand extends SubCommand {

    public BattleShipDenyCommand(BaseCommand parent) {
        super(parent, "deny", "battleship.accept");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            Game game = Battleship.getInstance().getGameManager().getGame(p);
            if(game == null){
                p.sendMessage(Battleship.PREFIX + Battleship.getLang("command.deny.noGame"));
                return true;
            }
            if(game.isStarted()){
                p.sendMessage(Battleship.PREFIX + Battleship.getLang("command.deny.isStarted"));
                return true;
            }
            Battleship.getInstance().getGameManager().removeGameBecauseOfDeny(game, p);
            p.sendMessage(Battleship.PREFIX + Battleship.getLang("command.deny.denied"));
        }
        return true;
    }
}
