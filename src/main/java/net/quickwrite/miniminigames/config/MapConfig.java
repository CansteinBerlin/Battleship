package net.quickwrite.miniminigames.config;

import net.quickwrite.miniminigames.Battleship;

public class MapConfig extends CustomConfig{

    public MapConfig(String name) {
        super(Battleship.getInstance(), name);
    }
}