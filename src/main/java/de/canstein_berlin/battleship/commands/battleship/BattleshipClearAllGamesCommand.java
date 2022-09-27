package de.canstein_berlin.battleship.commands.battleship;

import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import de.canstein_berlin.battleship.game.GameManager;
import org.bukkit.command.CommandSender;

public class BattleshipClearAllGamesCommand extends SubCommand {

    public BattleshipClearAllGamesCommand(BaseCommand parent) {
        super(parent, "clearGames", "battleship.cleargames");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {

        GameManager manager = Battleship.getInstance().getGameManager();
        for(int i = manager.getGames().size() - 1; i >= 0; i--){
            manager.getGames().get(i).forceQuit();
        }

        sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.clearGames.success"));

        return true;
    }
}
