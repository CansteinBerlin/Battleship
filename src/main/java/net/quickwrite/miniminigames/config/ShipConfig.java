package net.quickwrite.miniminigames.config;

import net.quickwrite.miniminigames.MiniMinigames;

public class ShipConfig extends CustomConfig{
    public ShipConfig() {
        super(MiniMinigames.getInstance(), "ships.yml");
    }
}
