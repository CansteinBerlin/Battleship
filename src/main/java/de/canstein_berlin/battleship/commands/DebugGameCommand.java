package de.canstein_berlin.battleship.commands;

import de.canstein_berlin.battleship.Battleship;
import de.canstein_berlin.battleship.commandsystem.BaseCommand;
import de.canstein_berlin.battleship.commandsystem.SubCommand;
import de.canstein_berlin.battleship.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;

public class DebugGameCommand extends BaseCommand {

    public DebugGameCommand() {
        super("debugGames", "battleship.debugGame");

        for(Class<? extends SubCommand> command : ReflectionUtil.getAllClasses("net.quickwrite.battleship.commands.debuggame", SubCommand.class)){
            try {
                addSubCommand(command.getConstructor(BaseCommand.class).newInstance(this));
            }catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                    InvocationTargetException e){
                Battleship.LOGGER.severe("§cCould not register Subcommand for " + name);
            }
        }
    }
}
