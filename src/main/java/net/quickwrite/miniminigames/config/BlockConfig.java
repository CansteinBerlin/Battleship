package net.quickwrite.miniminigames.config;

import net.quickwrite.miniminigames.Battleship;

public class BlockConfig extends CustomConfig{

    public BlockConfig() {
        super(Battleship.getInstance(), "blocks.yml");
    }
}
