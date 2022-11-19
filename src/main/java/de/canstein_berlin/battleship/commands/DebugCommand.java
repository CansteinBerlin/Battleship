package de.canstein_berlin.battleship.commands;

import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import de.canstein_berlin.battleship.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;

public class DebugCommand extends BaseCommand {

    public DebugCommand() {
        super("mdebug", "battleship.debug");

        for(Class<? extends SubCommand> command : ReflectionUtil.getAllClasses("de.canstein_berlin.battleship.commands.debug", SubCommand.class)){
            try {
                addSubCommand(command.getConstructor(BaseCommand.class).newInstance(this));
            }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                Battleship.LOGGER.severe("§cCould not register Subcommand for " + name);
            }
        }
    }
}
