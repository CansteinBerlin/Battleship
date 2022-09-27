package main.java.de.canstein_berlin.battleship.commands.debuggame;

import main.java.de.canstein_berlin.battleship.Battleship;
import main.java.de.canstein_berlin.battleship.commandsystem.BaseCommand;
import main.java.de.canstein_berlin.battleship.commandsystem.SubCommand;
import main.java.de.canstein_berlin.battleship.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.BatToggleSleepEvent;

public class DebugGameInfoCommand extends SubCommand {

    public DebugGameInfoCommand(BaseCommand parent) {
        super(parent, "info", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (args.length != 1){
            sender.sendMessage(Battleship.PREFIX + "§cPlease use §6/debugGame info <player>");
            return true;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if(p == null){
            sender.sendMessage(Battleship.PREFIX + "§cUnknown player with name " + args[0]);
            return true;
        }
        Game game = Battleship.getInstance().getGameManager().getGame(p);
        if(game == null) {
            sender.sendMessage(Battleship.PREFIX + "§cThis player has no game");
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("GameIndex: ").append(Battleship.getInstance().getGameManager().getGames().indexOf(game)).append("\n");
        sb.append("Attacker: ").append(game.getAttacker().getDisplayName()).append("\n");
        sb.append("Defender: ").append(game.getAttacker().getDisplayName()).append("\n");
        sb.append("GameState: ").append(game.getCurrentGameState()).append("\n");
        sb.append("AttackerAttacking: ").append(game.isAttackerAttacking()).append("\n");

        sender.sendMessage(sb.toString());


        return true;
    }
}
