package net.quickwrite.battleship;

import net.quickwrite.battleship.blocks.BattleShipBlocks;
import net.quickwrite.battleship.commands.BattleShipCommand;
import net.quickwrite.battleship.commands.DebugCommand;
import net.quickwrite.battleship.commandsystem.CommandManager;
import net.quickwrite.battleship.config.*;
import net.quickwrite.battleship.display.HorizontalDisplay;
import net.quickwrite.battleship.display.VerticalDisplay;
import net.quickwrite.battleship.game.GameManager;
import net.quickwrite.battleship.gui.GuiManager;
import net.quickwrite.battleship.items.BattleshipItems;
import net.quickwrite.battleship.map.Map;
import net.quickwrite.battleship.map.MapManager;
import net.quickwrite.battleship.map.MapSide;
import net.quickwrite.battleship.ships.Ship;
import net.quickwrite.battleship.ships.ShipManager;
import net.quickwrite.battleship.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public final class Battleship extends JavaPlugin {

    public static String PREFIX = "§3[§bMiniMinigames§3]§r ";
    public static int ATTACK_DELAY = 10;

    private static Battleship instance;
    public static Logger LOGGER;

    private CommandManager commandManager;
    private DefaultConfig config;
    private ShipConfig shipConfig;
    private ItemConfig itemConfig;
    private BlockConfig blockConfig;
    private MapsConfig mapsConfig;
    private LanguageConfig languageConfig;

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

        for(Class<? extends Listener > listener : ReflectionUtil.getAllClasses("net.quickwrite.battleship.listener", Listener.class)){
            try {
                Bukkit.getPluginManager().registerEvents(listener.getConstructor().newInstance(), this);
            }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                Battleship.LOGGER.severe("§cCould not register Listener with name " + listener);
            }
        }
    }

    private void loadConfigs() {
        loadDefaultConfig();
        loadLanguageConfig();
        loadItemConfig();
        loadBlockConfig();
        loadShipConfig();
        loadMapsConfig();
    }

    public static String getLang(String key, String... args) {
        String lang = Battleship.getInstance().languageConfig.getConfig().getString("messages." + key, "&cUnknown or empty language key please check the config &6" + key);
        for (int i = 0; i + 1 < args.length; i += 2) {
            lang = lang.replace("%" + args[i] + "%", args[i + 1]);
        }

        if(!Battleship.getInstance().languageConfig.getConfig().contains("messages." + key)){
            Battleship.getInstance().languageConfig.getConfig().set("messages." + key, "MISSING LANGUAGE KEY");
            Battleship.getInstance().languageConfig.saveConfig();
        }

        return ChatColor.translateAlternateColorCodes('&', lang).replace("\\n", "\n");
    }

    public static net.md_5.bungee.api.ChatColor getChatColor(String key){
        String color = Battleship.getInstance().languageConfig.getConfig().getString("messages." + key, "MAGIC");
        return net.md_5.bungee.api.ChatColor.of(color);
    }

    private void loadLanguageConfig(){
        languageConfig = new LanguageConfig(this);
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

    public void loadShipConfig() {
        shipConfig = new ShipConfig();

        ShipManager.loadShips(shipConfig);

        //Default ships
        ShipManager.defaultShips.add(new Ship(2, Material.GREEN_DYE, "default.ship.place", "default.ship.hit", getLang("ship.small"), "small"));
        ShipManager.defaultShips.add(new Ship(3, Material.RED_DYE, "default.ship.place", "default.ship.hit", getLang("ship.medium"), "medium"));
        ShipManager.defaultShips.add(new Ship(4, Material.BLUE_DYE, "default.ship.place", "default.ship.hit", getLang("ship.large"), "large"));
        ShipManager.defaultShips.add(new Ship(5, Material.BLACK_DYE, "default.ship.place", "default.ship.hit", getLang("ship.larger"), "larger"));
        ShipManager.defaultShips.add(new Ship(6, Material.BROWN_DYE, "default.ship.place", "default.ship.hit", getLang("ship.even_larger"), "even_larger"));

    }

    public void loadDefaultConfig(){
        config = new DefaultConfig();
        FileConfiguration conf = config.getConfig();
        PREFIX = conf.getString("prefix", PREFIX);
        ATTACK_DELAY = conf.getInt("delay", 10);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Battleship getInstance() {
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

    public LanguageConfig getLanguageConfig() {
        return languageConfig;
    }
}
