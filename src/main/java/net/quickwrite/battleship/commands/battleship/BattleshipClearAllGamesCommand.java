package net.quickwrite.battleship.commands.battleship;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import net.quickwrite.battleship.game.GameManager;
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
