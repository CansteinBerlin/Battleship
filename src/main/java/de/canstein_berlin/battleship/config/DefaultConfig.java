package de.canstein_berlin.battleship.config;


import de.canstein_berlin.battleship.Battleship;

public class DefaultConfig extends CustomConfig {
    public DefaultConfig() {
        super(Battleship.getInstance(), "config.yml");
    }
}
