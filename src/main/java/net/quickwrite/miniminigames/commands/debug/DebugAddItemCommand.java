package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.Battleship;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.items.BattleshipItems;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DebugAddItemCommand extends SubCommand {

    public DebugAddItemCommand(BaseCommand parent) {
        super(parent, "addItem", null);
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
            BattleshipItems.registerItemStack(name, selected);
            sender.sendMessage(Battleship.PREFIX + "§aAdded item: §6" + selected + "\n§ausing name: §6" + name);
        }else{
            sender.sendMessage(Battleship.PREFIX + "§cCould not add Item with missing name");
        }

        return true;
    }
}
