package net.quickwrite.battleship.commands.debuggame;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import net.quickwrite.battleship.game.Game;
import org.bukkit.command.CommandSender;

public class DebugGameListGamesCommand extends SubCommand {

    public DebugGameListGamesCommand(BaseCommand parent) {
        super(parent, "listGames", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for(Game game : Battleship.getInstance().getGameManager().getGames()){
            sb.append(i).append(": ").append(game.getAttacker().getDisplayName()).append(" vs. ").append(game.getDefender().getDisplayName()).append("\n");
            i++;
        }

        sender.sendMessage(sb.toString());

        return true;
    }
}
