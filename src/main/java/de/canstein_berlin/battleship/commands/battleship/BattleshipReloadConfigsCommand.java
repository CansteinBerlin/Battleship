package main.java.de.canstein_berlin.battleship.commands.battleship;

import main.java.de.canstein_berlin.battleship.Battleship;
import main.java.de.canstein_berlin.battleship.blocks.BattleShipBlocks;
import main.java.de.canstein_berlin.battleship.commandsystem.BaseCommand;
import main.java.de.canstein_berlin.battleship.commandsystem.SubCommand;
import main.java.de.canstein_berlin.battleship.items.BattleshipItems;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BattleshipReloadConfigsCommand extends SubCommand {

    public BattleshipReloadConfigsCommand(BaseCommand parent) {
        super(parent, "reloadconfigs", "battleship.reloadconfigs");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {

        if(args.length == 0){
            sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.reloadConfigs.invalidCommand", "command", getCommandHistory()));
            return true;
        }

        switch (args[0].toLowerCase(Locale.ROOT)){

            case "messages":
                Battleship.getInstance().getLanguageConfig().reloadConfig();
                break;
            case "blocks":
                BattleShipBlocks.load(Battleship.getInstance().getBlockConfig().getConfig());
                break;
            case "items":
                BattleshipItems.load(Battleship.getInstance().getItemConfig().getConfig());
                break;
            case "maps":
                if(Battleship.getInstance().getMapsConfig().getConfig().contains("maps"))
                    Battleship.getInstance().getMapManager().loadMaps(Battleship.getInstance().getMapsConfig().getConfig().getStringList("maps"));
                break;
            case "ships":
                Battleship.getInstance().loadShipConfig();
                break;
            case "config":
                Battleship.getInstance().loadDefaultConfig();
                break;

            default:
                sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.reloadConfigs.invalidCommand", "command", getCommandHistory()));
                return true;

        }
        sender.sendMessage(Battleship.PREFIX + Battleship.getLang("command.reloadConfigs.success", "config", args[0].toLowerCase(Locale.ROOT)));

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length > 1) return new ArrayList<>();

        return Stream.of("messages", "blocks", "items", "maps", "ships", "config")
                .filter(config -> config.startsWith(args[0].toLowerCase(Locale.ROOT)))
                .sorted()
                .collect(Collectors.toList());
    }
}
