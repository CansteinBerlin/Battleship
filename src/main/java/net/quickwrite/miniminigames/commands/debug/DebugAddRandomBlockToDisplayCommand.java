package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.display.Display;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.util.Random;

public class DebugAddRandomBlockToDisplayCommand extends SubCommand {

    public DebugAddRandomBlockToDisplayCommand(BaseCommand parent) {
        super(parent, "addRandomBlock", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if(Display.currentTestDisplay == null){
            sender.sendMessage(MiniMinigames.PREFIX + "§cNo Display Available");
            return true;
        }
        Display display = Display.currentTestDisplay;
        Random random = new Random();
        display.setBlock(random.nextInt(display.getWidth()), random.nextInt(display.getHeight()), Material.BLACK_WOOL);
        return true;
    }
}
