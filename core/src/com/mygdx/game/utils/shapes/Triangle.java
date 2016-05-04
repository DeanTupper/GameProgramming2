package com.mygdx.game.utils.shapes;

import com.badlogic.gdx.math.Vector2;

public class Triangle
{
    public final Vector2 origin;
    public final Vector2 edgePointA;
    public final Vector2 edgePointB;
    public final Line edge;

    public final LineSegment edge1;
    public final Vector2 edgeNormal;

    public final float minX, maxX;
    public final float minY, maxY;

    public Triangle(Vector2 origin, Vector2 edgePointA, Vector2 edgePointB)
    {
        this.origin = origin;

        this.edgePointA = edgePointA;
        this.edgePointB = edgePointB;

        edge1 = new LineSegment(edgePointA, edgePointB);
        Vector2 midPoint = edge1.midPoint();

        float dx = edgePointA.x - edgePointB.x;
        float dy = edgePointA.y - edgePointB.y;

        Vector2 normal1 = new Vector2(-dy, dx);
        Vector2 normal2 = new Vector2(dy, -dx);

        Vector2 point1 = midPoint.cpy().add(normal1);
        Vector2 point2 = midPoint.cpy().add(normal2);

        if (point1.dst2(origin) > point2.dst2(origin))
        {
            edgeNormal = normal1;
        }
        else
        {
            edgeNormal = normal2;
        }

        edge = new Line(edgePointA, edgePointB);

        if (edgePointA.x < edgePointB.x)
        {
            minX = edgePointA.x;
            maxX = edgePointB.x;
        }
        else
        {
            minX = edgePointB.x;
            maxX = edgePointA.x;
        }

        if (edgePointA.y < edgePointB.y)
        {
            minY = edgePointA.y;
            maxY = edgePointB.y;
        }
        else
        {
            minY = edgePointB.y;
            maxY = edgePointA.y;
        }
    }

    @Override
    public String toString()
    {
        return "triangle: [" + origin + "," + edgePointA + "," + edgePointB + "]";
    }

    public boolean isPointOutOfBounds(Vector2 intersectionPoint)
    {
        return intersectionPoint.x < minX || intersectionPoint.x > maxX || intersectionPoint.y < minY || intersectionPoint.y > maxY;
    }
}
