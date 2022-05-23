package net.quickwrite.miniminigames.display;

public enum Direction {

    POS_X_DIRECTION(0, 0, -1),
    NEG_X_DIRECTION(0, 0, 1),
    POS_Z_DIRECTION(1, 0, 0),
    NEG_Z_DIRECTION(-1, 0, 0);
    ;

    final int xMod, yMod, zMod;

    Direction(int xMod, int yMod, int zMod) {
        this.xMod = xMod;
        this.yMod = yMod;
        this.zMod = zMod;
    }
}
