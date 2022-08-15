package net.quickwrite.miniminigames.config;

import net.quickwrite.miniminigames.Battleship;

public class ShipConfig extends CustomConfig{
    public ShipConfig() {
        super(Battleship.getInstance(), "ships.yml");
    }
}
