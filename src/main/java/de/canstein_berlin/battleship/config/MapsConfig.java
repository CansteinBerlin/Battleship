package main.java.de.canstein_berlin.battleship.config;

import main.java.de.canstein_berlin.battleship.Battleship;

public class MapsConfig extends CustomConfig{

    public MapsConfig() {
        super(Battleship.getInstance(), "maps.yml");
    }
}
