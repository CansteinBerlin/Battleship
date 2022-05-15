package net.quickwrite.miniminigames.config;


import net.quickwrite.miniminigames.MiniMinigames;

public class DefaultConfig extends CustomConfig {
    public DefaultConfig() {
        super(MiniMinigames.getInstance(), "config.yml");
    }
}
