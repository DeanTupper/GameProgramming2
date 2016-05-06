package com.mygdx.game.utils;

import com.mygdx.game.components.Collidable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Quad
{
    private final int row;
    private final int col;

    public final List<Collidable> collidables = new ArrayList<Collidable>();

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

    public Set<Quad> getNeighbors()
    {
        return neighbors;
    }

    public Set<Collidable> getCollidableEntitiesInRegion()
    {
        Set<Collidable> collidableEntities = new HashSet<Collidable>();

        for (Quad neighbor : neighbors)
        {
            collidableEntities.addAll(neighbor.collidables);
        }

        collidableEntities.addAll(collidables);

        return collidableEntities;
    }

    public String toString()
    {
        return "[" + row + "," + col + "]";
    }

    void clearCollidablesList()
    {
        collidables.clear();
    }

    public void addCollidable(Collidable collidable)
    {
        collidables.add(collidable);
    }

}