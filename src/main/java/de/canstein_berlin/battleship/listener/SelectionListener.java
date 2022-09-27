package main.java.de.canstein_berlin.battleship.listener;

import main.java.de.canstein_berlin.battleship.commands.debug.DebugEnableSelectionCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectionListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if(!event.getAction().equals(Action.LEFT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(!DebugEnableSelectionCommand.selectingPlayers.contains(event.getPlayer())) return;

        DebugEnableSelectionCommand.modifySelection(event.getPlayer(), event.getClickedBlock().getLocation(), event.getAction());
        event.setCancelled(true);
    }
}
