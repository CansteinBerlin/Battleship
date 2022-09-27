package de.canstein_berlin.battleship.config;

import de.canstein_berlin.battleship.Battleship;

public class ItemConfig extends CustomConfig{

    public ItemConfig() {
        super(Battleship.getInstance(), "items.yml");
    }
}
