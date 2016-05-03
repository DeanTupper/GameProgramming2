package com.mygdx.game.utils.shapes;

import com.badlogic.gdx.math.Vector2;

public class Triangle
{
    public final Vector2 origin;
    public final Vector2 edge1;
    public final Vector2 edge2;
    public final Line edge;

    public final float minX, maxX;
    public final float minY, maxY;

    public Triangle(Vector2 origin, Vector2 edge1, Vector2 edge2)
    {
        this.origin = origin;

        this.edge1 = edge1;
        this.edge2 = edge2;

        edge = new Line(edge1, edge2);

        if (edge1.x < edge2.x)
        {
            minX = edge1.x;
            maxX = edge2.x;
        }
        else
        {
            minX = edge2.x;
            maxX = edge1.x;
        }

        if (edge1.y < edge2.y)
        {
            minY = edge1.y;
            maxY = edge2.y;
        }
        else
        {
            minY = edge2.y;
            maxY = edge1.y;
        }
    }

    @Override
    public String toString()
    {
        return "triangle: [" + origin + "," + edge1 + "," + edge2 + "]";
    }

    public boolean isPointOutOfBounds(Vector2 intersectionPoint)
    {
        return intersectionPoint.x < minX || intersectionPoint.x > maxX || intersectionPoint.y < minY || intersectionPoint.y > maxY;
    }
}
