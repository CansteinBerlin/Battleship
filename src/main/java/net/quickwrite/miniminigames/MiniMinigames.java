package net.quickwrite.miniminigames;

import net.quickwrite.miniminigames.commands.TestCommand;
import net.quickwrite.miniminigames.commandsystem.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiniMinigames extends JavaPlugin {

    public static final String PREFIX = "§3[§bMiniMinigames§3]§r ";
    private static MiniMinigames instance;

    private CommandManager commandManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        commandManager = new CommandManager(this);
        commandManager.addCommand(new TestCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MiniMinigames getInstance() {
        return instance;
    }
}
