package net.quickwrite.battleship.commands.battleship;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import net.quickwrite.battleship.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class BattleShipChallengeCommand extends SubCommand {

    public BattleShipChallengeCommand(BaseCommand parent) {
        super(parent, "challenge", "battleship.challenge");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(args.length != 1){
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.challenge.invalidCommand", "command", getCommandHistory()));
            return true;
        }
        if(!(sender instanceof Player)){
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.noPlayer"));
            return true;
        }
        Player attacker = (Player) sender;
        Player defender = Bukkit.getPlayer(args[0]);
        if(defender == null){
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.unknownPlayer", "player", args[0]));
            return true;
        }
        if(defender.equals(attacker)){
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.challenge.noChallengeSelf"));
            return true;
        }

        Game game = Battleship.getInstance().getGameManager().createGame(attacker, defender);
        if(game == null){
            if(Battleship.getInstance().getGameManager().getGame(defender) != null){
                sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.challenge.opponentInGame"));
            }else{
                sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.inGame"));
            }
            return true;
        }

        Battleship.getInstance().getGuiManager().createMapSelectionGui(attacker, game, (map) -> {
            TextComponent acceptComponent = new TextComponent(Battleship.getLang("display.accept"));
            acceptComponent.setColor(Battleship.getChatColor("display.acceptColor"));
            acceptComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/battleship accept"));

            TextComponent denyComponent = new TextComponent(Battleship.getLang("display.deny"));
            denyComponent.setColor(Battleship.getChatColor("display.denyColor"));
            denyComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/battleship deny"));

            TextComponent message = new TextComponent(Battleship.PREFIX + Battleship.getLang("display.challengeMessage", "player", attacker.getDisplayName(), "map", map.getName()));

            defender.spigot().sendMessage(new ComponentBuilder(message).append(" ").append(acceptComponent).append(" ").append(denyComponent).create());

            attacker.sendMessage(Battleship.PREFIX + Battleship.getLang("command.challenge.sentChallengeInvite", "player", defender.getDisplayName()));

            Battleship.getInstance().getGameManager().startCountdown(game, attacker);
            Battleship.getInstance().getMapManager().markCurrentlyPlaying(map, true);
        });

        sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.challenge.selectMap"));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length > 1) return new ArrayList<>();
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
