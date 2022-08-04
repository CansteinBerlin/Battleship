package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class BattleShipSpectateGameCommand extends SubCommand {

    public BattleShipSpectateGameCommand(BaseCommand parent) {
        super(parent, "spectateGame", "battleship.spectateGame");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.noPlayer"));
            return true;
        }
        Player spectator = ((Player) sender);
        if(args.length != 1){
            sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.spectateGame.invalidCommand", "command", getCommandHistory()));
            return true;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if(p == null){
            sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.unknownPlayer"));
            return true;
        }
        Game game = MiniMinigames.getInstance().getGameManager().getGame(p);
        if(game == null){
            sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.spectateGame.noGameRunning"));
            return true;
        }
        game.addSpectator(spectator);
        sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.spectateGame.start"));

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length > 1) return new ArrayList<>();
        Player p = null;
        if(sender instanceof Player) p = ((Player) sender);
        final Player finalP = p;
        return Bukkit.getOnlinePlayers().stream()
                .filter((pl) -> pl != finalP)
                .map(Player::getDisplayName)
                .map(ChatColor::stripColor)
                .filter(name1 -> name1.toLowerCase(Locale.ROOT).startsWith(args[0].toLowerCase(Locale.ROOT)))
                .sorted()
                .collect(Collectors.toList());
    }
}
