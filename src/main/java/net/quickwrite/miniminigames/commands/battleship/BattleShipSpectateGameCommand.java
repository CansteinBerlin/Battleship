package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
}
