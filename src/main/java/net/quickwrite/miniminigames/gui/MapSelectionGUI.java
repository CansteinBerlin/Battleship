package net.quickwrite.miniminigames.gui;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.game.Game;
import net.quickwrite.miniminigames.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

public class MapSelectionGUI implements IGUI{

    public static final String TITLE = "§6Map Selection";

    private final Player player;
    private Inventory inv;
    private final Game game;
    private final Consumer<Map> runnable;
    private boolean clicked;

    public MapSelectionGUI(Player p, Game game, Consumer<Map> runnable){
        this.player = p;
        this.game = game;
        this.runnable = runnable;
        update();
        p.closeInventory();
        open();
        clicked = false;
    }

    public void update(){
        if(MiniMinigames.getInstance().getMapManager().getMaps().size() == 0){
            inv = Bukkit.createInventory(null, 9, TITLE);
        }else {
            inv = Bukkit.createInventory(null, ((int) Math.ceil(MiniMinigames.getInstance().getMapManager().getMaps().size() / 9.0)) * 9, TITLE);
        }
        int i = 0;
        for(ItemStack display : MiniMinigames.getInstance().getMapManager().getDisplayItemStacks()){
            ItemMeta meta = display.getItemMeta();
            assert meta != null;
            List<String> lore = meta.getLore();
            if(MiniMinigames.getInstance().getMapManager().isCurrentlyPlaying(MiniMinigames.getInstance().getMapManager().getMaps().get(i))){
                meta.setDisplayName("§c" + meta.getDisplayName());
                lore.add("§r" + MiniMinigames.getLang("gui.mapInUse"));
            }else{
                meta.setDisplayName("§a" + meta.getDisplayName());
                lore.add("§r" + MiniMinigames.getLang("gui.mapCanBePlayed"));
            }
            meta.setLore(lore);
            display.setItemMeta(meta);
            inv.addItem(display);
            i++;
        }
        open();
    }

    @Override
    public boolean onClose(Player p) {
        if(!clicked) {
            MiniMinigames.getInstance().getGameManager().invalidMapSelection(game);
            p.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("command.challenge.noMapSelected"));
        }
        return true;
    }

    public void open(){
        player.openInventory(inv);
    }

    public void onClick(int slot, InventoryAction inventoryAction){
        if(inv.getItem(slot) == null) return;
        if(inv.getItem(slot).getType().equals(Material.AIR) && slot >= 0) return;


        Map map = MiniMinigames.getInstance().getMapManager().loadMap(MiniMinigames.getInstance().getMapManager().getMaps().get(slot));
        if(map == null) return;
        if(MiniMinigames.getInstance().getMapManager().isCurrentlyPlaying(map)){
            player.sendMessage(MiniMinigames.PREFIX + MiniMinigames.getLang("gui.noPlayOnMap"));
            return;
        }

        clicked = true;
        close();
        game.setMap(map);

        runnable.accept(map);
    }

    public void close(){
        player.closeInventory();
    }
}
