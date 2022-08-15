package net.quickwrite.miniminigames.game;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.quickwrite.miniminigames.Battleship;
import net.quickwrite.miniminigames.display.Direction;
import net.quickwrite.miniminigames.display.Display;
import net.quickwrite.miniminigames.map.MapSide;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;

public class AttackShipRunner extends BukkitRunnable {

    public static String attackingString = "";
    public static String waitingString = "";

    private final Player p;
    private final MapSide displaySide;
    private Location lastLocation;
    private boolean running;

    public AttackShipRunner(Player p, MapSide displaySide) {
        this.p = p;
        this.displaySide = displaySide;
        running = true;
        attackingString = Battleship.getLang("display.attackShipRunner.attackingActionBar");
        waitingString = Battleship.getLang("display.attackShipRunner.waitingForOpponentAttack");
    }

    @Override
    public void run() {
        if(!running) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(waitingString));
            return;
        }
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(attackingString));
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
