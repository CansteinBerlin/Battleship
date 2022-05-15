package net.quickwrite.miniminigames.ships;

import net.quickwrite.miniminigames.config.ShipConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;

public class ShipManager {

    public static final HashSet<Ship> ships = new HashSet<>();

    public static void saveShips(ShipConfig shipConfig) {
        FileConfiguration config = shipConfig.getConfig();
        config.set("ships", ships);
        shipConfig.saveConfig();
    }

    @SuppressWarnings("unchecked")
    public static void loadShips(ShipConfig shipConfig){
        FileConfiguration config = shipConfig.getConfig();
        ships.clear();
        ships.addAll((HashSet<Ship>) config.get("ships", new HashSet<>()));

    }

}
