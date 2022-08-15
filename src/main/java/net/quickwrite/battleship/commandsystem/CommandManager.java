package net.quickwrite.battleship.commandsystem;

import net.quickwrite.battleship.Battleship;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final Set<BaseCommand> commands = new HashSet<>();
    private final JavaPlugin main;

    public CommandManager(JavaPlugin main) {
        this.main = main;
    }

    public void addCommand(BaseCommand command){
        commands.add(command);

        PluginCommand cmd = main.getCommand(command.getName());
        cmd.setExecutor(this);
        cmd.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (BaseCommand cmd : commands) {
            if (cmd.getName().equalsIgnoreCase(label)) {
                if (cmd.getPermission() != null) {
                    if (sender.hasPermission(cmd.getPermission())) {
                        return cmd.performCommand(sender, args);
                    } else {
                        sender.sendMessage(cmd.getNoPermissionMessage());
                        return true;
                    }
                } else {
                    return cmd.performCommand(sender, args);
                }
            }
        }
        sender.sendMessage(Battleship.PREFIX + "§cUnknown Command");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        for (BaseCommand cmd : commands){
            if(cmd.getName().equalsIgnoreCase(command.getLabel())){
                List<String> list = cmd.tabComplete(sender, args);
                if(list == null) return null;
                Collections.sort(list);
                return list;
            }
        }
        return null;
    }
}
