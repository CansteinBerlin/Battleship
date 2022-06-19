package net.quickwrite.miniminigames.map;

import com.google.common.collect.ImmutableMap;
import net.quickwrite.miniminigames.display.HorizontalDisplay;
import net.quickwrite.miniminigames.display.VerticalDisplay;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Map implements ConfigurationSerializable {

    private ItemStack displayItem;
    private MapSide attacker, defender;
    private Player player1, player2;
    private String name;


    public Map(VerticalDisplay attackerVerticalDisplay, VerticalDisplay defenderVerticalDisplay, HorizontalDisplay attackerHorizontalDisplay,
               HorizontalDisplay defenderHorizontalDisplay, ItemStack displayItem, String name, Location attackerSpawnLocation, Location defenderSpawnLocation){
        this.attacker = new MapSide(attackerVerticalDisplay, attackerHorizontalDisplay, attackerSpawnLocation);
        this.defender = new MapSide(defenderVerticalDisplay, defenderHorizontalDisplay, defenderSpawnLocation);
        this.displayItem = displayItem;
        applyLore(displayItem);
        this.name = name;
    }

    public Map(java.util.Map<String, Object> data){
        attacker = (MapSide) data.get("attacker");
        defender = (MapSide) data.get("defender");
        name = (String) data.get("name");
        displayItem = (ItemStack) data.get("displayItem");
        applyLore(displayItem);
    }

    public void addPlayer(Player player){
        if(player1 == null) {
            player1 = player;
            attacker.addPlayingPlayer(player);
        }else if(player2 == null){
            player2 = player;
            defender.addPlayingPlayer(player);
        }
    }

    public void teleportToSpawn(){
        attacker.teleportToSpawn();
        defender.teleportToSpawn();
    }

    private void applyLore(ItemStack stack){
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.stripColor(meta.getDisplayName()));
        List<String> lore = new ArrayList<>();
        lore.add("§r§7Map: §6" + name);
        lore.add("§r§7Size: §6" + attacker.getThisPlayerDisplay().getWidth() + "x" + attacker.getThisPlayerDisplay().getHeight());
        meta.setLore(lore);
        stack.setItemMeta(meta);
    }

    public static boolean isValid(VerticalDisplay attackerVerticalDisplay, VerticalDisplay defenderVerticalDisplay, HorizontalDisplay attackerHorizontalDisplay,
                                  HorizontalDisplay defenderHorizontalDisplay){
        return (attackerHorizontalDisplay.getWidth() == defenderHorizontalDisplay.getWidth())
                && (attackerVerticalDisplay.getWidth() == defenderVerticalDisplay.getWidth())
                && (defenderHorizontalDisplay.getWidth() == defenderVerticalDisplay.getWidth())
                &&
                (attackerHorizontalDisplay.getHeight() == defenderHorizontalDisplay.getHeight())
                && (attackerVerticalDisplay.getHeight() == defenderVerticalDisplay.getHeight())
                && (defenderHorizontalDisplay.getHeight() == defenderVerticalDisplay.getHeight());
    }

    @Override
    public java.util.Map<String, Object> serialize() {

        return new ImmutableMap.Builder<String, Object>()
                .put("attacker", attacker)
                .put("defender", defender)
                .put("name", name)
                .put("displayItem", displayItem)
                .build();
    }

    public Player getPlayer1() {
        return player1;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public Player getPlayer2() {
        return player2;
    }

    public String getName() {
        return name;
    }

    public void displayAll() {
        attacker.display();
        defender.display();
    }
}
