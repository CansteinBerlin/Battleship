package main.java.de.canstein_berlin.battleship.commands.debug;

import main.java.de.canstein_berlin.battleship.Battleship;
import main.java.de.canstein_berlin.battleship.commandsystem.BaseCommand;
import main.java.de.canstein_berlin.battleship.commandsystem.SubCommand;
import main.java.de.canstein_berlin.battleship.display.Display;
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
            sender.sendMessage(Battleship.PREFIX + "§cNo Display Available");
            return true;
        }
        Display display = Display.currentTestDisplay;
        Random random = new Random();
        display.setBlock(random.nextInt(display.getWidth()), random.nextInt(display.getHeight()), Material.BLACK_WOOL);
        return true;
    }
}
