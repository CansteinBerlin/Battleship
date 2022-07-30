package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
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
            sender.sendMessage(MiniMinigames.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }
        Player p = ((Player) sender);
        if(SPECTATE_ALL_GAMES_PLAYERS.contains(p)){
            sender.sendMessage(MiniMinigames.PREFIX + "§aYou are no longer spectating all games");
            SPECTATE_ALL_GAMES_PLAYERS.remove(p);
            MiniMinigames.getInstance().getGameManager().removeSpectateAllGames(p);
            return true;
        }
        sender.sendMessage(MiniMinigames.PREFIX + "§aYou are now spectating all games");
        SPECTATE_ALL_GAMES_PLAYERS.add(p);
        MiniMinigames.getInstance().getGameManager().spectateAllGames(p);
        return true;
    }
}
