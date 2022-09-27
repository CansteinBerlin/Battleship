package main.java.de.canstein_berlin.battleship.config;

import main.java.de.canstein_berlin.battleship.Battleship;

public class BlockConfig extends CustomConfig{

    public BlockConfig() {
        super(Battleship.getInstance(), "blocks.yml");
    }
}
