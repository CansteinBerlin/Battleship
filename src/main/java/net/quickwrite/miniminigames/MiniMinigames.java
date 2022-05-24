package net.quickwrite.miniminigames;

import net.quickwrite.miniminigames.blocks.BattleShipBlocks;
import net.quickwrite.miniminigames.commands.DebugCommand;
import net.quickwrite.miniminigames.commandsystem.CommandManager;
import net.quickwrite.miniminigames.config.*;
import net.quickwrite.miniminigames.display.Display;
import net.quickwrite.miniminigames.display.HorizontalDisplay;
import net.quickwrite.miniminigames.display.VerticalDisplay;
import net.quickwrite.miniminigames.items.BattleshipItems;
import net.quickwrite.miniminigames.listener.SelectionListener;
import net.quickwrite.miniminigames.map.Map;
import net.quickwrite.miniminigames.map.MapManager;
import net.quickwrite.miniminigames.map.MapSide;
import net.quickwrite.miniminigames.ships.Ship;
import net.quickwrite.miniminigames.ships.ShipManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class MiniMinigames extends JavaPlugin {

    public static String PREFIX = "§3[§bMiniMinigames§3]§r ";
    private static MiniMinigames instance;
    public static Logger LOGGER;

    private CommandManager commandManager;
    private DefaultConfig config;
    private ShipConfig shipConfig;
    private ItemConfig itemConfig;
    private BlockConfig blockConfig;
    private MapsConfig mapsConfig;

    private MapManager mapManager;


    @Override
    public void onEnable() {
        instance = this;
        LOGGER = getLogger();
        ConfigurationSerialization.registerClass(Ship.class);
        ConfigurationSerialization.registerClass(MapSide.class);
        ConfigurationSerialization.registerClass(VerticalDisplay.class);
        ConfigurationSerialization.registerClass(HorizontalDisplay.class);
        ConfigurationSerialization.registerClass(Map.class);

        loadConfigs();

        commandManager = new CommandManager(this);
        commandManager.addCommand(new DebugCommand());

        Bukkit.getPluginManager().registerEvents(new SelectionListener(), this);
    }

    private void loadConfigs() {
        loadDefaultConfig();
        loadItemConfig();
        loadBlockConfig();
        loadShipConfig();
        loadMapsConfig();
    }

    private void loadMapsConfig() {
        mapsConfig = new MapsConfig();
        mapManager = new MapManager();
        if(mapsConfig.getConfig().contains("maps")) mapManager.loadMaps(mapsConfig.getConfig().getStringList("maps"));
    }

    private void loadBlockConfig() {
        blockConfig = new BlockConfig();
        BattleShipBlocks.load(blockConfig.getConfig());
    }

    private void loadItemConfig() {
        itemConfig = new ItemConfig();
        BattleshipItems.load(itemConfig.getConfig());
    }

    private void loadShipConfig() {
        shipConfig = new ShipConfig();
        ShipManager.loadShips(shipConfig);
        for(Ship s : ShipManager.ships){
            Bukkit.broadcastMessage(s.toString());
        }
    }

    private void loadDefaultConfig(){
        config = new DefaultConfig();
        FileConfiguration conf = config.getConfig();
        PREFIX = conf.getString("prefix", PREFIX);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MiniMinigames getInstance() {
        return instance;
    }

    public DefaultConfig getDefaultConfig() {
        return config;
    }

    public ShipConfig getShipConfig() {
        return shipConfig;
    }

    public ItemConfig getItemConfig() {
        return itemConfig;
    }

    public BlockConfig getBlockConfig() {
        return blockConfig;
    }

    public MapsConfig getMapsConfig() {
        return mapsConfig;
    }

    public MapManager getMapManager() {
        return mapManager;
    }
}
