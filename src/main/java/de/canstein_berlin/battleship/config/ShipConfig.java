package main.java.de.canstein_berlin.battleship.config;

import main.java.de.canstein_berlin.battleship.Battleship;

public class ShipConfig extends CustomConfig{
    public ShipConfig() {
        super(Battleship.getInstance(), "ships.yml");
    }
}
