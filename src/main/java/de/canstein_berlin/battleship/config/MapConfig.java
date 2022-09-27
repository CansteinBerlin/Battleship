package de.canstein_berlin.battleship.config;

import de.canstein_berlin.battleship.Battleship;

public class MapConfig extends CustomConfig{

    public MapConfig(String name) {
        super(Battleship.getInstance(), name);
    }
}