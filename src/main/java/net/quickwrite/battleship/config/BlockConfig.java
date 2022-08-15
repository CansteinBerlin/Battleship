package net.quickwrite.battleship.config;

import net.quickwrite.battleship.Battleship;

public class BlockConfig extends CustomConfig{

    public BlockConfig() {
        super(Battleship.getInstance(), "blocks.yml");
    }
}
