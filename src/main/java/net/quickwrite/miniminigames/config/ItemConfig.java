package net.quickwrite.miniminigames.config;

import net.quickwrite.miniminigames.Battleship;

public class ItemConfig extends CustomConfig{

    public ItemConfig() {
        super(Battleship.getInstance(), "items.yml");
    }
}
