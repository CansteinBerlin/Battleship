package net.quickwrite.miniminigames;

import net.quickwrite.miniminigames.blocks.BattleShipBlocks;
import net.quickwrite.miniminigames.commands.DebugCommand;
import net.quickwrite.miniminigames.commandsystem.CommandManager;
import net.quickwrite.miniminigames.config.BlockConfig;
import net.quickwrite.miniminigames.config.DefaultConfig;
import net.quickwrite.miniminigames.config.ItemConfig;
import net.quickwrite.miniminigames.config.ShipConfig;
import net.quickwrite.miniminigames.items.BattleshipItems;
import net.quickwrite.miniminigames.ships.Ship;
import net.quickwrite.miniminigames.ships.ShipManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.logging.Logger;

public final class MiniMinigames extends JavaPlugin {

    public static String PREFIX = "§3[§bMiniMinigames§3]§r ";
    private static MiniMinigames instance;

    private CommandManager commandManager;
    private DefaultConfig config;
    private ShipConfig shipConfig;
    private ItemConfig itemConfig;
    private BlockConfig blockConfig;

    public static Logger logger;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        ConfigurationSerialization.registerClass(Ship.class);

        loadConfigs();

        commandManager = new CommandManager(this);
        commandManager.addCommand(new DebugCommand());
    }

    private void loadConfigs() {
        loadDefaultConfig();
        loadItemConfig();
        loadBlockConfig();
        loadShipConfig();

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
}
