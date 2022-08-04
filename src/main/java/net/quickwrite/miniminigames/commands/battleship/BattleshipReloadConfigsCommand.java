package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.blocks.BattleShipBlocks;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.items.BattleshipItems;
import net.quickwrite.miniminigames.map.MapManager;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public class BattleshipReloadConfigsCommand extends SubCommand {

    public BattleshipReloadConfigsCommand(BaseCommand parent) {
        super(parent, "reloadconfigs", "battleship.reloadconfigs");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {

        if(args.length == 0){
            sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.reloadconfigs.invalidCommand", "command", getCommandHistory()));
            return true;
        }

        switch (args[0].toLowerCase(Locale.ROOT)){

            case "messages":
                MiniMinigames.getInstance().getLanguageConfig().reloadConfig();
                break;
            case "blocks":
                BattleShipBlocks.load(MiniMinigames.getInstance().getBlockConfig().getConfig());
                break;
            case "items":
                BattleshipItems.load(MiniMinigames.getInstance().getItemConfig().getConfig());
                break;
            case "maps":
                if(MiniMinigames.getInstance().getMapsConfig().getConfig().contains("maps"))
                    MiniMinigames.getInstance().getMapManager().loadMaps(MiniMinigames.getInstance().getMapsConfig().getConfig().getStringList("maps"));
                break;
            case "ships":
                MiniMinigames.getInstance().loadShipConfig();
                break;

            default:
                sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.reloadconfigs.invalidCommand", "command", getCommandHistory()));
                return true;

        }
        sender.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.reloadconfigs.success", "config", args[0].toLowerCase(Locale.ROOT)));

        return true;
    }
}
