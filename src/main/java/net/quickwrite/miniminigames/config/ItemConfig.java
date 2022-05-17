package net.quickwrite.miniminigames.config;

import net.quickwrite.miniminigames.MiniMinigames;

public class ItemConfig extends CustomConfig{

    public ItemConfig() {
        super(MiniMinigames.getInstance(), "items.yml");
    }
}
