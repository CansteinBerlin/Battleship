package net.quickwrite.miniminigames.game;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.quickwrite.miniminigames.display.Direction;
import net.quickwrite.miniminigames.display.Display;
import net.quickwrite.miniminigames.map.MapSide;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;

public class AttackShipRunner extends BukkitRunnable {

    private final Player p;
    private final MapSide displaySide;
    private Location lastLocation;
    private boolean running;

    public AttackShipRunner(Player p, MapSide displaySide) {
        this.p = p;
        this.displaySide = displaySide;
        running = true;
    }

    @Override
    public void run() {
        if(!running) return;
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aYou are attacking"));
        RayTraceResult result = p.getWorld().rayTraceBlocks(p.getEyeLocation(), p.getLocation().getDirection(), 50);
        if(result == null){
            lastLocation = null;
            return;
        }
        Location loc = Display.unifyLocation(result.getHitPosition().toLocation(p.getWorld()));

        if(displaySide.getOtherPlayerDisplay().getDirection().equals(Direction.POS_Z_DIRECTION) || displaySide.getOtherPlayerDisplay().getDirection().equals(Direction.NEG_Z_DIRECTION)){
            loc.setZ(displaySide.getOtherPlayerDisplay().getMaxZ());
        }else{
            loc.setX(displaySide.getOtherPlayerDisplay().getMaxX());
        }

        if(loc.equals(lastLocation)) return;
        displaySide.getOtherPlayerDisplay().removeSpawnedMarkers();

        if(!displaySide.getOtherPlayerDisplay().isInBounds(loc)) {
            lastLocation = null;
            return;
        }
        if(displaySide.getOtherPlayerDisplay().isAttacked(loc)) {
            lastLocation = null;
            return;
        }

        displaySide.getOtherPlayerDisplay().markLocation(loc);
        lastLocation = loc.clone();

    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
