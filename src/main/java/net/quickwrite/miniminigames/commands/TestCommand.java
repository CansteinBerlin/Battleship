package net.quickwrite.miniminigames.commands;

import net.quickwrite.miniminigames.commands.test.TestSubCommand;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;

public class TestCommand extends BaseCommand {

    public TestCommand() {
        super("test", null);

        addSubCommand(new TestSubCommand(this));
    }
}
