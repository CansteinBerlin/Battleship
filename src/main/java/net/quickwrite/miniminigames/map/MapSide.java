package net.quickwrite.miniminigames.map;

import com.google.common.collect.ImmutableMap;
import net.quickwrite.miniminigames.display.HorizontalDisplay;
import net.quickwrite.miniminigames.display.VerticalDisplay;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.Map;

public class MapSide implements ConfigurationSerializable{

    private Player player;
    private VerticalDisplay otherPlayerDisplay;
    private HorizontalDisplay thisPlayerDisplay;
    private boolean surrendered, finishedMove, finishedPlace;

    public MapSide(VerticalDisplay otherPlayerDisplay, HorizontalDisplay thisPlayerDisplay){
        this.otherPlayerDisplay = otherPlayerDisplay;
        this.thisPlayerDisplay = thisPlayerDisplay;

        surrendered = false;
        finishedMove = false;
        finishedPlace = false;
    }

    public MapSide(Map<String, Object> data){
        this((VerticalDisplay) data.get("otherPlayerDisplay"), (HorizontalDisplay) data.get("thisPlayerDisplay"));
    }

    public void addPlayingPlayer(Player player){
        if(this.player == null) this.player = player;
        addPlayerToDisplays(player);
    }

    public void removePlayer(Player player){
        if(this.player.getUniqueId().equals(player.getUniqueId())){
            player = null;
            surrendered = true;
        }
        removefromDisplay(player);

    }

    public void addPlayerToDisplays(Player player){
        otherPlayerDisplay.addPlayer(player);
        thisPlayerDisplay.addPlayer(player);
    }
    private void removefromDisplay(Player player){
        otherPlayerDisplay.removePlayer(player);
        thisPlayerDisplay.removePlayer(player);
    }

    @Override
    public Map<String, Object> serialize() {
        return new ImmutableMap.Builder<String, Object>()
                .put("otherPlayerDisplay", otherPlayerDisplay)
                .put("thisPlayerDisplay", thisPlayerDisplay)
                .build();
    }

    public void display(){
        thisPlayerDisplay.display();
        otherPlayerDisplay.display();
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
