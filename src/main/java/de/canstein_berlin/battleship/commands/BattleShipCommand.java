package main.java.de.canstein_berlin.battleship.commands;

import main.java.de.canstein_berlin.battleship.Battleship;
import main.java.de.canstein_berlin.battleship.commandsystem.BaseCommand;
import main.java.de.canstein_berlin.battleship.commandsystem.SubCommand;
import main.java.de.canstein_berlin.battleship.util.ReflectionUtil;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;

public class BattleShipCommand extends BaseCommand {

    public BattleShipCommand() {
        super("battleship", "battleship.command.battleship");

        for(Class<? extends SubCommand> command : ReflectionUtil.getAllClasses("net.quickwrite.battleship.commands.battleship", SubCommand.class)){
            try {
                addSubCommand(command.getConstructor(BaseCommand.class).newInstance(this));
            }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                Battleship.LOGGER.severe("§cCould not register Subcommand for " + name);
            }
        }
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        return super.performCommand(sender, args);
    }
}
