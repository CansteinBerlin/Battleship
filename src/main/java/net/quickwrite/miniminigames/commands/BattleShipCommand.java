package net.quickwrite.miniminigames.commands;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.util.ReflectionUtil;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;

public class BattleShipCommand extends BaseCommand {

    public BattleShipCommand() {
        super("battleship", null);

        for(Class<? extends SubCommand> command : ReflectionUtil.getAllClasses("net.quickwrite.miniminigames.commands.battleship", SubCommand.class)){
            try {
                addSubCommand(command.getConstructor(BaseCommand.class).newInstance(this));
            }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                MiniMinigames.LOGGER.severe("§cCould not register Subcommand for " + name);
            }
        }
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        return super.performCommand(sender, args);
    }
}
