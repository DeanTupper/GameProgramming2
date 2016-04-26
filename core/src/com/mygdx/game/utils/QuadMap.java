package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameWorld;
import com.mygdx.game.components.Collidable;

import java.util.List;
import java.util.Set;

public class QuadMap
{
    public static final int QUAD_ROWS = 10;
    public static final int QUAD_COLUMNS = 10;

    private final Quad[][] quadMap = new Quad[QUAD_ROWS][QUAD_COLUMNS];

    public QuadMap()
    {
        for (int row = 0; row < quadMap.length; row++)
        {
            for (int col = 0; col < quadMap[0].length; col++)
            {
                quadMap[row][col] = new Quad(row, col);
            }
        }

        for (Quad[] row : quadMap)
        {
            for (Quad quad : row)
            {
                int colWest = getQuadCol(quad.getCol(), -1);
                int colEast = getQuadCol(quad.getCol(), 1);

                int rowNorth = getQuadRow(quad.getRow(), -1);
                int rowSouth = getQuadRow(quad.getRow(), 1);

                quad.addNeighbor(Direction.NORTH, quadMap[rowNorth][quad.getCol()]);
                quad.addNeighbor(Direction.NORTH_EAST, quadMap[rowNorth][colEast]);
                quad.addNeighbor(Direction.EAST, quadMap[quad.getRow()][colEast]);
                quad.addNeighbor(Direction.SOUTH_EAST, quadMap[rowSouth][colEast]);
                quad.addNeighbor(Direction.SOUTH, quadMap[rowSouth][quad.getCol()]);
                quad.addNeighbor(Direction.SOUTH_WEST, quadMap[rowSouth][colWest]);
                quad.addNeighbor(Direction.WEST, quadMap[quad.getRow()][colWest]);
                quad.addNeighbor(Direction.NORTH_WEST, quadMap[rowNorth][colWest]);
            }
        }
    }

    private int getQuadRow(int row, int delta)
    {
        return getQuadIndex(row, delta, QUAD_ROWS);
    }

    private int getQuadCol(int col, int delta)
    {
        return getQuadIndex(col, delta, QUAD_COLUMNS);
    }

    private int getQuadIndex(int index, int delta, int max)
    {
        int i = index + delta;

        if (i < 0)
        {
            i = max - 1;
        }

        if (i == max)
        {
            i = 0;
        }

        return i;
    }

    public void update(Set<Collidable> collidables)
    {
        clearQuadMapCollidables();

        for (Collidable collidable : collidables)
        {
            Quad quad = getQuad(collidable.getPosition());
            quad.addCollidable(collidable);
        }
    }

    private void clearQuadMapCollidables()
    {
        for (Quad[] row : quadMap)
        {
            for (Quad quad : row)
            {
                quad.clearCollidablesList();
            }
        }
    }

    public Quad getQuad(Vector2 position)
    {
        return getQuad(position.x, position.y);
    }

    public Quad getQuad(float x, float y)
    {
        int row = (int) (x / GameWorld.DEFAULT_WORLD_WIDTH);
        int col = (int) (x / GameWorld.DEFAULT_WORLD_HEIGHT);

        return quadMap[row][col];
    }
}
