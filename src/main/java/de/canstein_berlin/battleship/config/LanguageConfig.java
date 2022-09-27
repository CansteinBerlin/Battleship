package main.java.de.canstein_berlin.battleship.config;

import org.bukkit.plugin.java.JavaPlugin;

public class LanguageConfig extends CustomConfig{


    public LanguageConfig(JavaPlugin plugin) {
        super(plugin, "messages.yml");
    }
}
