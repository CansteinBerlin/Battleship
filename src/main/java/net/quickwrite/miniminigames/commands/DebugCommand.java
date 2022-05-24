package net.quickwrite.miniminigames.commands;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commands.debug.*;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;

public class DebugCommand extends BaseCommand {

    public DebugCommand() {
        super("mdebug", null);

        for(Class<? extends SubCommand> command : ReflectionUtil.getAllClasses("net.quickwrite.miniminigames.commands.debug", SubCommand.class)){
            try {
                addSubCommand(command.getConstructor(BaseCommand.class).newInstance(this));
            }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                MiniMinigames.logger.severe("§cCould not register Subcommand for " + name);
            }
        }
    }
}
