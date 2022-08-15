package net.quickwrite.battleship.config;

import net.quickwrite.battleship.Battleship;

public class MapsConfig extends CustomConfig{

    public MapsConfig() {
        super(Battleship.getInstance(), "maps.yml");
    }
}
