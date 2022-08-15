package net.quickwrite.miniminigames.commandsystem;

import net.quickwrite.miniminigames.Battleship;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class SubCommand extends BaseCommand {

    protected BaseCommand parent;

    public SubCommand(BaseCommand parent, String name, String permission) {
        super(name, permission);
        this.parent = parent;
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (args.length > 0) {
            for (SubCommand cmd : subCommands) {
                if (cmd.getName().equalsIgnoreCase(args[0])) {
                    if (cmd.getPermission() != null) {
                        if (sender.hasPermission(cmd.getPermission())) {
                            return cmd.performCommand(sender, Arrays.copyOfRange(args, 1, args.length));
                        } else {
                            sender.sendMessage(cmd.getNoPermissionMessage());
                            return true;
                        }
                    } else {
                        return cmd.performCommand(sender, Arrays.copyOfRange(args, 1, args.length));
                    }
                }
            }
        }
        sender.sendMessage(Battleship.PREFIX + "§cPlease use /" + getCommandHistory() + " <" + getValidCommands() + "> ");
        return true;
    }

    public String getCommandHistory(){
        StringBuilder sb = new StringBuilder(name);
        BaseCommand p = parent;
        while (p instanceof SubCommand){
            sb.insert(0, p.getName() + " ");
            p = ((SubCommand) p).parent;
        }
        sb.insert(0, p.getName() + " ");

        return sb.toString();
    }
}
