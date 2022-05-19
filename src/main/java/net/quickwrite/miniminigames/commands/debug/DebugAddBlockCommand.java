package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.blocks.BattleShipBlocks;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.items.BattleshipItems;
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
            sender.sendMessage(MiniMinigames.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }

        Player p = (Player) sender;

        if(args.length == 1){
            String name = args[0];
            ItemStack selected = p.getInventory().getItemInMainHand();
            if(selected.getType().isAir()){
                p.sendMessage(MiniMinigames.PREFIX + "§cYou have to hold an item to use this command");
                return true;
            }
            BattleShipBlocks.registerBlock(name, selected.getType());
            sender.sendMessage(MiniMinigames.PREFIX + "§aAdded block: §6" + selected + "\n§ausing name: §6" + name);
        }else{
            sender.sendMessage(MiniMinigames.PREFIX + "§cCould not add Block with missing name");
        }

        return true;
    }
}
