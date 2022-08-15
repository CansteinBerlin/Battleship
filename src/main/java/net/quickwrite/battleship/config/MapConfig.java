package net.quickwrite.battleship.config;

import net.quickwrite.battleship.Battleship;

public class MapConfig extends CustomConfig{

    public MapConfig(String name) {
        super(Battleship.getInstance(), name);
    }
}