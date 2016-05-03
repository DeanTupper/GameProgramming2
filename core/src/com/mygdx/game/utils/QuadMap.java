package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Collidable;

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
                int colWest = quad.getCol() - 1;
                int colEast = quad.getCol() + 1;

                int rowNorth = quad.getRow() - 1;
                int rowSouth = quad.getRow() + 1;

                boolean colWestValid = isValidCol(colWest);
                boolean colEastValid = isValidCol(colEast);

                boolean rowNorthValid = isValidRow(rowNorth);
                boolean rowSouthValid = isValidRow(rowSouth);

                if (colEastValid)
                {
                    quad.addNeighbor(Direction.EAST, quadMap[quad.getRow()][colEast]);
                }

                if (colWestValid)
                {
                    quad.addNeighbor(Direction.WEST, quadMap[quad.getRow()][colWest]);
                }

                if (rowSouthValid)
                {
                    quad.addNeighbor(Direction.SOUTH, quadMap[rowSouth][quad.getCol()]);

                    if (colEastValid)
                    {
                        quad.addNeighbor(Direction.SOUTH_EAST, quadMap[rowSouth][colEast]);
                    }

                    if (colWestValid)
                    {
                        quad.addNeighbor(Direction.SOUTH_WEST, quadMap[rowSouth][colWest]);
                    }
                }

                if (rowNorthValid)
                {
                    quad.addNeighbor(Direction.NORTH, quadMap[rowNorth][quad.getCol()]);

                    if (colEastValid)
                    {
                        quad.addNeighbor(Direction.NORTH_EAST, quadMap[rowNorth][colEast]);
                    }

                    if (colWestValid)
                    {
                        quad.addNeighbor(Direction.NORTH_WEST, quadMap[rowNorth][colWest]);
                    }
                }
            }
        }
    }

    private boolean isValidCol(int val)
    {
        return isValidIndex(val, quadMap[0].length - 1);
    }

    private boolean isValidRow(int val)
    {
        return isValidIndex(val, quadMap.length - 1);
    }

    private boolean isValidIndex(int val, int max)
    {
        return val <= max && val >= 0;
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
        int row = (int) (y / QUAD_ROWS);
        int col = (int) (x / QUAD_COLUMNS);

        //System.err.println("QuadMap::getQuad - x:[" + x + "], y:[" + y + "]; row,col: " + row + "," + col);

        return quadMap[row][col];
    }

    public void render(ShapeRenderer renderer)
    {
        Color rowColor = Color.RED;

        for (int row = 0; row < quadMap.length; row++)
        {
            float y = 55f + row * 4.5f;

            for (int col = 0; col < quadMap[0].length; col++)
            {
                float x = col * 4.5f;

                renderer.setColor(rowColor.r - col * 0.05f, rowColor.g + col * 0.05f, 0.0f, 1.0f);
                renderer.rect(x, y, 4.5f, 4.5f);
            }

            rowColor = new Color(rowColor.r - 0.05f, rowColor.g + 0.05f, 0.0f, 1.0f);
        }
    }
}
