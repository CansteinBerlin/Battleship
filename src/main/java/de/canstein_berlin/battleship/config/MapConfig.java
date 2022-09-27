package main.java.de.canstein_berlin.battleship.config;

import main.java.de.canstein_berlin.battleship.Battleship;

public class MapConfig extends CustomConfig{

    public MapConfig(String name) {
        super(Battleship.getInstance(), name);
    }
}