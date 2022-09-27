package main.java.de.canstein_berlin.battleship.commands.debug;

import main.java.de.canstein_berlin.battleship.Battleship;
import main.java.de.canstein_berlin.battleship.blocks.BattleShipBlocks;
import main.java.de.canstein_berlin.battleship.commandsystem.BaseCommand;
import main.java.de.canstein_berlin.battleship.commandsystem.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DebugAddBlockCommand extends SubCommand {

    public DebugAddBlockCommand(BaseCommand parent) {
        super(parent, "addBlock", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        //Add the Item the player is holding using the specified name
        if(!(sender instanceof Player)){
            sender.sendMessage(Battleship.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }

        Player p = (Player) sender;

        if(args.length == 1){
            String name = args[0];
            ItemStack selected = p.getInventory().getItemInMainHand();
            if(selected.getType().isAir()){
                p.sendMessage(Battleship.PREFIX + "§cYou have to hold an item to use this command");
                return true;
            }
            BattleShipBlocks.registerBlock(name, selected.getType());
            sender.sendMessage(Battleship.PREFIX + "§aAdded block: §6" + selected + "\n§ausing name: §6" + name);
        }else{
            sender.sendMessage(Battleship.PREFIX + "§cCould not add Block with missing name");
        }

        return true;
    }
}
