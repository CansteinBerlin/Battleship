package net.quickwrite.battleship.commands;

import net.quickwrite.battleship.Battleship;
import net.quickwrite.battleship.commandsystem.BaseCommand;
import net.quickwrite.battleship.commandsystem.SubCommand;
import net.quickwrite.battleship.util.ReflectionUtil;

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
