package net.quickwrite.battleship.config;

import net.quickwrite.battleship.Battleship;

public class ItemConfig extends CustomConfig{

    public ItemConfig() {
        super(Battleship.getInstance(), "items.yml");
    }
}
