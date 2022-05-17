package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.ships.Ship;
import org.bukkit.command.CommandSender;

public class DebugAddShipCommand extends SubCommand {

    public DebugAddShipCommand(BaseCommand parent) {
        super(parent, "addShip", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (args.length == 1){
            try {
                int size = Integer.parseInt(args[0]);
                new Ship(size, "default.ship.display.small", "default.ship.place", "default.ship.hit");
                sender.sendMessage(MiniMinigames.PREFIX + "§aAdded Ship with size " + size);
                return true;
            }catch (NumberFormatException e){
                sender.sendMessage(e.toString());
            }
        }
        sender.sendMessage(MiniMinigames.PREFIX + "§cCould not add Ship");
        return true;
    }
}
