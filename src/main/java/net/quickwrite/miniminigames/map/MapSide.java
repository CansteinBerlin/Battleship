package net.quickwrite.miniminigames.map;

import com.google.common.collect.ImmutableMap;
import net.quickwrite.miniminigames.display.HorizontalDisplay;
import net.quickwrite.miniminigames.display.VerticalDisplay;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.Map;

public class MapSide implements ConfigurationSerializable{

    private Player player;
    private final VerticalDisplay otherPlayerDisplay;
    private final HorizontalDisplay thisPlayerDisplay;
    private boolean surrendered, finishedMove, finishedPlace;
    private final Location spawnLocation;

    public MapSide(VerticalDisplay otherPlayerDisplay, HorizontalDisplay thisPlayerDisplay, Location spawnLocation){
        this.otherPlayerDisplay = otherPlayerDisplay;
        this.thisPlayerDisplay = thisPlayerDisplay;
        this.spawnLocation = spawnLocation;

        surrendered = false;
        finishedMove = false;
        finishedPlace = false;
    }

    public MapSide(Map<String, Object> data){
        this((VerticalDisplay) data.get("otherPlayerDisplay"), (HorizontalDisplay) data.get("thisPlayerDisplay"), (Location) data.get("spawnLocation"));
    }

    public void addPlayingPlayer(Player player){
        if(this.player == null) this.player = player;
        addPlayerToDisplays(player);
    }

    public void teleportToSpawn(){
        player.teleport(spawnLocation);
    }

    public void removePlayer(Player player){
        if(this.player.getUniqueId().equals(player.getUniqueId())){
            player = null;
            surrendered = true;
        }
        removeFromDisplay(player);

    }

    public void addPlayerToDisplays(Player player){
        otherPlayerDisplay.addPlayer(player);
        thisPlayerDisplay.addPlayer(player);
    }
    private void removeFromDisplay(Player player){
        otherPlayerDisplay.removePlayer(player);
        thisPlayerDisplay.removePlayer(player);
    }

    @Override
    public Map<String, Object> serialize() {
        return new ImmutableMap.Builder<String, Object>()
                .put("otherPlayerDisplay", otherPlayerDisplay)
                .put("thisPlayerDisplay", thisPlayerDisplay)
                .put("spawnLocation", spawnLocation)
                .build();
    }

    public void display(){
        thisPlayerDisplay.display();
        otherPlayerDisplay.display();

        spawnLocation.getWorld().spawnParticle(Particle.END_ROD, spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ(), 100, 0.5, 0.5, 0.5, 0D);
    }

    public boolean isSurrendered() {
        return surrendered;
    }

    public boolean isFinishedMove() {
        return finishedMove;
    }

    public boolean isFinishedPlace() {
        return finishedPlace;
    }

    public Player getPlayer() {
        return player;
    }

    public VerticalDisplay getOtherPlayerDisplay() {
        return otherPlayerDisplay;
    }

    public HorizontalDisplay getThisPlayerDisplay() {
        return thisPlayerDisplay;
    }
}
