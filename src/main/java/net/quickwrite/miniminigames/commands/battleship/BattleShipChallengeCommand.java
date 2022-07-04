package net.quickwrite.miniminigames.commands.battleship;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BattleShipChallengeCommand extends SubCommand {

    public BattleShipChallengeCommand(BaseCommand parent) {
        super(parent, "challenge", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(args.length != 1){
            sender.sendMessage(MiniMinigames.PREFIX + "§cPlease use §6/" + getCommandHistory() + " <player>");
            return true;
        }
        if(!(sender instanceof Player)){
            sender.sendMessage(MiniMinigames.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }
        Player attacker = (Player) sender;
        Player defender = Bukkit.getPlayer(args[0]);
        if(defender == null){
            sender.sendMessage(MiniMinigames.PREFIX + "§cUnknown Player with name: " + args[0]);
            return true;
        }
        if(defender.equals(attacker)){
            sender.sendMessage(MiniMinigames.PREFIX + "§cYou can't challenge yourself");
            return true;
        }

        Game game = MiniMinigames.getInstance().getGameManager().createGame(attacker, defender);
        if(game == null){
            if(MiniMinigames.getInstance().getGameManager().getGame(defender) != null){
                sender.sendMessage(MiniMinigames.PREFIX + "§cYou opponent ist currently in a game");
            }else{
                sender.sendMessage(MiniMinigames.PREFIX + "§cYou are currently in a game");
            }
            return true;
        }

        MiniMinigames.getInstance().getGuiManager().createMapSelectionGui(attacker, game, (map) -> {
            TextComponent acceptComponent = new TextComponent("[Accept]");
            acceptComponent.setColor(ChatColor.GREEN);
            acceptComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/battleship accept"));

            TextComponent denyComponent = new TextComponent("[Deny]");
            denyComponent.setColor(ChatColor.RED);
            denyComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/battleship deny"));

            TextComponent message = new TextComponent(MiniMinigames.PREFIX + "§aPlayer §6" + attacker.getDisplayName() + "§a invited you to a game of Battleship on the map §6" + map.getName() + "\n§aYou have §660 §aseconds to accept\n");

            defender.spigot().sendMessage(new ComponentBuilder(message).append(" ").append(acceptComponent).append(" ").append(denyComponent).create());

            attacker.sendMessage(MiniMinigames.PREFIX + "§aSent challenge invite to §6" + defender.getDisplayName());

            MiniMinigames.getInstance().getGameManager().startCountdown(game, attacker);
        });

        sender.sendMessage(MiniMinigames.PREFIX + "§aPlease select the map");
        return true;
    }


}
