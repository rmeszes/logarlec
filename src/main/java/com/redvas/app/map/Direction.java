package com.redvas.app.map;

public enum Direction {
    UP(0),
    DOWN(1),
    RIGHT(2),
    LEFT(3),
    TOP_LEFT(4),
    BOTTOM_RIGHT(5),
    TOP_RIGHT(6),
    BOTTOM_LEFT(7);

    private final int value;

    Direction(
            int value
    ) {
        this.value = value;
    }
    public static int getEvenPairValue(Direction d) { return (d.getValue() & 1) == 1 ? d.getValue() - 1 : d.getValue(); }
    public int getValue() { return value; }
    private static final Direction[] enumSingletonsMap = new Direction[8];
    private static final Direction[] reverseEnumSingletonsMap = new Direction[8];
    public Direction getReverse() {
        return reverseEnumSingletonsMap[value];
    }

    static {
        for (Direction d : Direction.values())
            enumSingletonsMap[d.getValue()] = d;

        for (Direction d : Direction.values()) {
            if ((d.getValue() & 1) == 1)
                reverseEnumSingletonsMap[d.getValue()] = enumSingletonsMap[d.getValue() - 1];
            else reverseEnumSingletonsMap[d.getValue()] = enumSingletonsMap[d.getValue() + 1];
        }
    }

    public static Direction valueOf(int value) {
        return enumSingletonsMap[value];
    }
}
