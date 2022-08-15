package net.quickwrite.battleship.util;

public class DebugMessage {

    private final Object[] objects;

    public DebugMessage(Object... values){
        objects = values;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Values: ");
        for(Object value : objects){
            sb.append(value).append(" | ");
        }
        return sb.substring(0, sb.length() - 2).toString();
    }
}
