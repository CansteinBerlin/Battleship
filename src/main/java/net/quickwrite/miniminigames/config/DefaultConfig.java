package net.quickwrite.miniminigames.config;


import net.quickwrite.miniminigames.Battleship;

public class DefaultConfig extends CustomConfig {
    public DefaultConfig() {
        super(Battleship.getInstance(), "config.yml");
    }
}
