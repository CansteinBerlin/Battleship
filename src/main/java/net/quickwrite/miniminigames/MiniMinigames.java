package net.quickwrite.miniminigames;

import net.quickwrite.miniminigames.blocks.BattleShipBlocks;
import net.quickwrite.miniminigames.commands.BattleShipCommand;
import net.quickwrite.miniminigames.commands.DebugCommand;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.CommandManager;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import net.quickwrite.miniminigames.config.*;
import net.quickwrite.miniminigames.display.HorizontalDisplay;
import net.quickwrite.miniminigames.display.VerticalDisplay;
import net.quickwrite.miniminigames.game.GameManager;
import net.quickwrite.miniminigames.game.gamestate.GameStateManager;
import net.quickwrite.miniminigames.gui.GuiManager;
import net.quickwrite.miniminigames.items.BattleshipItems;
import net.quickwrite.miniminigames.listener.GuiListener;
import net.quickwrite.miniminigames.listener.SelectionListener;
import net.quickwrite.miniminigames.map.Map;
import net.quickwrite.miniminigames.map.MapManager;
import net.quickwrite.miniminigames.map.MapSide;
import net.quickwrite.miniminigames.ships.Ship;
import net.quickwrite.miniminigames.ships.ShipManager;
import net.quickwrite.miniminigames.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    private GameManager gameManager;
    private GuiManager guiManager;


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
        commandManager.addCommand(new BattleShipCommand());

        gameManager = new GameManager();

        guiManager = new GuiManager();

        for(Class<? extends Listener > listener : ReflectionUtil.getAllClasses("net.quickwrite.miniminigames.listener", Listener.class)){
            try {
                Bukkit.getPluginManager().registerEvents(listener.getConstructor().newInstance(), this);
            }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                MiniMinigames.LOGGER.severe("§cCould not register Listener with name " + listener);
            }
        }
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

        //Default ships
        ShipManager.defaultShips.add(new Ship(2, Material.GREEN_DYE, "default.ship.place", "default.ship.hit", "Small Ship"));
        ShipManager.defaultShips.add(new Ship(3, Material.RED_DYE, "default.ship.place", "default.ship.hit", "Medium Ship"));
        ShipManager.defaultShips.add(new Ship(4, Material.BLUE_DYE, "default.ship.place", "default.ship.hit", "Large Ship"));
        ShipManager.defaultShips.add(new Ship(5, Material.BLACK_DYE, "default.ship.place", "default.ship.hit", "Larger Ship"));
        ShipManager.defaultShips.add(new Ship(6, Material.BROWN_DYE, "default.ship.place", "default.ship.hit", "Even larger Ship"));

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

    public GameManager getGameManager(){
        return gameManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}
