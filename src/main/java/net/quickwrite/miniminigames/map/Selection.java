package net.quickwrite.miniminigames.map;

import org.bukkit.Location;

public class Selection {

    public Location pos1, pos2;

    public Selection(){

    }

    public boolean isFinished(){
        return pos1 != null && pos2 != null;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }
}
