package net.quickwrite.miniminigames.commands;

import net.quickwrite.miniminigames.commands.test.DebugAddShipCommand;
import net.quickwrite.miniminigames.commands.test.DebugSaveCommand;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;

public class DebugCommand extends BaseCommand {

    public DebugCommand() {
        super("mdebug", null);

        addSubCommand(new DebugSaveCommand(this));
        addSubCommand(new DebugAddShipCommand(this));
    }
}
