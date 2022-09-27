package main.java.de.canstein_berlin.battleship.commands;

import main.java.de.canstein_berlin.battleship.Battleship;
import main.java.de.canstein_berlin.battleship.commandsystem.BaseCommand;
import main.java.de.canstein_berlin.battleship.commandsystem.SubCommand;
import main.java.de.canstein_berlin.battleship.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;

public class DebugCommand extends BaseCommand {

    public DebugCommand() {
        super("mdebug", "battleship.debug");

        for(Class<? extends SubCommand> command : ReflectionUtil.getAllClasses("net.quickwrite.battleship.commands.debug", SubCommand.class)){
            try {
                addSubCommand(command.getConstructor(BaseCommand.class).newInstance(this));
            }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                Battleship.LOGGER.severe("§cCould not register Subcommand for " + name);
            }
        }
    }
}
