package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.game.GameManager;
import org.bukkit.command.CommandSender;

public class BattleshipClearAllGamesCommand extends SubCommand {

    public BattleshipClearAllGamesCommand(BaseCommand parent) {
        super(parent, "clearGames", "battleship.cleargames");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {

        GameManager manager = MiniMinigames.getInstance().getGameManager();
        for(int i = manager.getGames().size() - 1; i >= 0; i--){
            manager.getGames().get(i).forceQuit();
        }

        sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("commands.clearGames.success"));

        return true;
    }
}
