package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.game.Game;
import net.quickwrite.miniminigames.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BattleShipQueueAllPlayersCommand extends SubCommand {

    public BattleShipQueueAllPlayersCommand(BaseCommand parent) {
        super(parent, "queueAll", "battleship.queueAllPlayers");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        List<Player> playingPlayers = Bukkit.getOnlinePlayers().stream().filter((p) -> !p.getGameMode().equals(GameMode.SPECTATOR)).collect(Collectors.toList());
        List<Player> spectators = Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode().equals(GameMode.SPECTATOR)).collect(Collectors.toList());
        Collections.shuffle(playingPlayers);
        Collections.shuffle(spectators);
        if(playingPlayers.size() % 2 != 0){
            playingPlayers.add(spectators.get(0));
        }

        GameManager manager = MiniMinigames.getInstance().getGameManager();

        int mapAmount = playingPlayers.size() / 2;
        ArrayList<String> maps = MiniMinigames.getInstance().getMapManager().getMaps();
        if(maps.size() <= mapAmount){
            sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.queueAllPlayers.noMaps", "amount", String.valueOf((mapAmount - maps.size()))));
            return true;
        }

        System.out.println(manager.getGames());
        for(int i = manager.getGames().size() - 1; i >= 0; i--){
            manager.getGames().get(i).forceQuit();
        }

        for(int i = 0; i < mapAmount; i++){
            Game g = manager.createGame(playingPlayers.get(i * 2), playingPlayers.get(i * 2 + 1));
            g.setMap(MiniMinigames.getInstance().getMapManager().loadMap(maps.get(i)));
            g.initGame();
        }
        sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.queueAllPlayers.started"));
        return true;
    }
}
