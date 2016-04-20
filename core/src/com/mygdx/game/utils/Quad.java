package com.mygdx.game.utils;

import com.mygdx.game.components.Movable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;

public final class Quad
{
    private final int row;
    private final int col;

    private final List<Movable> movables = new ArrayList<Movable>();

    private final EnumMap<Direction, Quad> neighborMap = new EnumMap<Direction, Quad>(Direction.class);
    private final HashSet<Quad> neighbors = new HashSet<Quad>();

    Quad(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    void addNeighbor(Direction dir, Quad neighbor)
    {
        neighborMap.put(dir, neighbor);
        neighbors.add(neighbor);
    }

    boolean isNeighbor(Quad quad)
    {
        return neighbors.contains(quad);
    }

    Quad getNeighbor(Direction dir)
    {
        return neighborMap.get(dir);
    }

    public String toString()
    {
        return "[" + row + "," + col + "]";
    }

    void clearMovablesList()
    {
        movables.clear();
    }

    public void addMovable(Movable movable)
    {
        movables.add(movable);
    }
}