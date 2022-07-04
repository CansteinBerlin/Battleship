package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.game.Game;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BattleShipDenyCommand extends SubCommand {

    public BattleShipDenyCommand(BaseCommand parent) {
        super(parent, "deny", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            Game game = MiniMinigames.getInstance().getGameManager().getGame(p);
            if(game == null){
                p.sendMessage(MiniMinigames.PREFIX + "§cThere is no game to deny");
                return true;
            }
            if(game.isStarted()){
                p.sendMessage(MiniMinigames.PREFIX + "§cThe game has already been started");
                return true;
            }
            MiniMinigames.getInstance().getGameManager().removeGameBecauseOfDeny(game, p);
            p.sendMessage(MiniMinigames.PREFIX + "§aYou denied the invite");
        }
        return true;
    }
}
