package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.Battleship;
import net.quickwrite.miniminigames.blocks.BattleShipBlocks;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.items.BattleshipItems;
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
