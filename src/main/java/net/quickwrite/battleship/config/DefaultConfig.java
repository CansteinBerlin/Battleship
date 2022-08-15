package net.quickwrite.battleship.config;


import net.quickwrite.battleship.Battleship;

public class DefaultConfig extends CustomConfig {
    public DefaultConfig() {
        super(Battleship.getInstance(), "config.yml");
    }
}
