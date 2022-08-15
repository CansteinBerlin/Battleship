package net.quickwrite.battleship.display;

public enum Direction {

    POS_X_DIRECTION(0, 0, -1, 0, 1, -1, 0),
    NEG_X_DIRECTION(0, 0, 1, 0, -1, 1, 0),
    POS_Z_DIRECTION(1, 0, 0, -1, 0, 0, -1),
    NEG_Z_DIRECTION(-1, 0, 0, 1, 0, 0, 1);
    ;

    final int xMod, yMod, zMod, horizontalX, horizontalZ, verticalX, verticalZ;

    Direction(int xMod, int yMod, int zMod, int horizontalX, int horizontalZ, int verticalX, int verticalZ) {
        this.xMod = xMod;
        this.yMod = yMod;
        this.zMod = zMod;
        this.horizontalX = horizontalX;
        this.horizontalZ = horizontalZ;
        this.verticalX = verticalX;
        this.verticalZ = verticalZ;
    }

    public int getxMod() {
        return xMod;
    }

    public int getyMod() {
        return yMod;
    }

    public int getzMod() {
        return zMod;
    }

    public int getHorizontalX() {
        return horizontalX;
    }

    public int getHorizontalZ() {
        return horizontalZ;
    }

    public int getVerticalX() {
        return verticalX;
    }

    public int getVerticalZ() {
        return verticalZ;
    }
}
