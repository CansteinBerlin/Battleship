package net.quickwrite.miniminigames.config;

import net.quickwrite.miniminigames.Battleship;

public class MapsConfig extends CustomConfig{

    public MapsConfig() {
        super(Battleship.getInstance(), "maps.yml");
    }
}
