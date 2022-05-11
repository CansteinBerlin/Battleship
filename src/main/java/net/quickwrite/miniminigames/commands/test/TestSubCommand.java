package net.quickwrite.miniminigames.commands.test;

import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class TestSubCommand extends SubCommand {

    public TestSubCommand(BaseCommand parent) {
        super(parent, "subTest1", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return super.tabComplete(sender, args);
    }
}
