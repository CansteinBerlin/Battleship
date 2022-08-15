package net.quickwrite.battleship.config;

import net.quickwrite.battleship.Battleship;

public class ShipConfig extends CustomConfig{
    public ShipConfig() {
        super(Battleship.getInstance(), "ships.yml");
    }
}
