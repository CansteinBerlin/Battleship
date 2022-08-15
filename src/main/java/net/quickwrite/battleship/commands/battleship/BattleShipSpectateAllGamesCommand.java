package net.quickwrite.battleship.commands.battleship;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BattleShipSpectateAllGamesCommand extends SubCommand {

    public static final ArrayList<Player> SPECTATE_ALL_GAMES_PLAYERS = new ArrayList<>();

    public BattleShipSpectateAllGamesCommand(BaseCommand parent) {
        super(parent, "spectateAllGames", "battleship.spectateAllGames");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.noPlayer"));
            return true;
        }
        Player p = ((Player) sender);
        if(SPECTATE_ALL_GAMES_PLAYERS.contains(p)){
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.spectateAllGames.stop"));
            SPECTATE_ALL_GAMES_PLAYERS.remove(p);
            Battleship.getInstance().getGameManager().removeSpectateAllGames(p);
            return true;
        }
        sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.spectateAllGames.start"));
        SPECTATE_ALL_GAMES_PLAYERS.add(p);
        Battleship.getInstance().getGameManager().spectateAllGames(p);
        return true;
    }
}
