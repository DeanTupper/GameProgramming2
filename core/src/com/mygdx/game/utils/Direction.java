package com.mygdx.game.utils;

public enum Direction
{
    NORTH_WEST(-1, -1),
    NORTH(-1, 0),
    NORTH_EAST(-1, 1),
    EAST(0, 1),
    SOUTH_EAST(1, 1),
    SOUTH(1, 0),
    SOUTH_WEST(1, -1),
    WEST(0, -1);

    private final int rowDelta;
    private final int colDelta;

    Direction(int rowDelta, int colDelta)
    {
        this.rowDelta = rowDelta;
        this.colDelta = colDelta;
    }
}
