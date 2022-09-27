package de.canstein_berlin.battleship.commands.battleship;

import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import de.canstein_berlin.battleship.game.Game;
import de.canstein_berlin.battleship.game.GameManager;
import de.canstein_berlin.battleship.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
        if(!(sender instanceof Player)){
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.noPlayer"));
            return true;
        }
        Player playerSender = ((Player) sender);
        List<Player> playingPlayers = Bukkit.getOnlinePlayers().stream()
                .filter(p -> !p.getGameMode().equals(GameMode.SPECTATOR))
                .filter(p -> p.getWorld().equals(playerSender.getWorld()))
                .collect(Collectors.toList());

        Collections.shuffle(playingPlayers);
        if(playingPlayers.size() % 2 != 0){
            playingPlayers.remove(0);
        }

        GameManager manager = Battleship.getInstance().getGameManager();

        int mapAmount = playingPlayers.size() / 2;
        ArrayList<String> maps = Battleship.getInstance().getMapManager().getMaps();
        if(maps.size() < mapAmount){
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.queueAllPlayers.noMaps", "amount", String.valueOf((mapAmount - maps.size()))));
            return true;
        }

        for(int i = manager.getGames().size() - 1; i >= 0; i--){
            manager.getGames().get(i).forceQuit();
        }

        for(int i = 0; i < mapAmount; i++){
            Game g = manager.createGame(playingPlayers.get(i * 2), playingPlayers.get(i * 2 + 1));
            Map map = Battleship.getInstance().getMapManager().loadMap(maps.get(i));
            if(map == null){
                sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.queueAllPlayers.invalidMap"));
                g.forceQuit();
                continue;
            }
            g.setMap(Battleship.getInstance().getMapManager().loadMap(maps.get(i)));
            g.initGame();
        }
        sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.queueAllPlayers.started"));

        int number = Battleship.getInstance().getLanguageConfig().getConfig().getInt("messages.instructions.amount", -1);
        List<String> texts = new ArrayList<>();
        for(int i = 0; i < number; i++){
            texts.add(Battleship.getLang("instructions.inst_" + i));
        }
        int[] amount = {0};
        new BukkitRunnable(){

            @Override
            public void run() {

                if(amount[0] >= texts.size()){
                    cancel();
                    return;
                }
                for(Player p : playingPlayers){
                    String[] parts = ChatColor.translateAlternateColorCodes('&', texts.get(amount[0])).split("\\n");
                    if(parts.length == 2) p.sendTitle(parts[0], parts[1], 0, 20*2, 0);
                    else p.sendTitle(texts.get(amount[0]), "", 0, 20*2, 0);
                }
                amount[0] += 1;

            }
        }.runTaskTimer(Battleship.getInstance(), 0, 20*2);

        return true;
    }
}
