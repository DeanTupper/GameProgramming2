package com.mygdx.game.utils.collision;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.shapes.Rectangle;

public final class CollisionUtils
{
    public static boolean circleIntersectsRectangle(Vector2 circle, float radius, Rectangle rect)
    {
        boolean circleWithinRectXBounds = circle.x > rect.x && circle.x < rect.right;
        boolean circleWithinRectYBounds = circle.y > rect.y && circle.y < rect.top;

        if (circleWithinRectXBounds && circleWithinRectYBounds)
        {
            return true;
        }
        else
        {
            float closestX = MathUtils.clamp(circle.x, rect.x, rect.right);
            float closestY = MathUtils.clamp(circle.y, rect.y, rect.top);

            return isPointWithinCircle(circle, radius, closestX, closestY);
        }
    }

    public static boolean isPointWithinCircle(Vector2 circle, float radius, float x, float y)
    {
        float dist = getSquaredDistBtwnPts(x, y, circle.x, circle.y);

        return dist < radius * radius;
    }

    public static float getSquaredDistBtwnPts(float x1, float y1, float x2, float y2)
    {
        float distX = x1 - x2;
        float distY = y1 - y2;

        return (distX * distX) + (distY * distY);
    }

    public static boolean circleIntersectsCircle(Vector2 a, float radiusA, Vector2 b, float radiusB)
    {
        float deltaX = a.x - b.x;
        float deltaY = a.y - b.y;
        float deltaRadius = radiusA + radiusB;

        return (deltaX * deltaX) + (deltaY * deltaY) <= (deltaRadius * deltaRadius);
    }

    public static Vector2 getPointOnCircle(Vector2 center, Vector2 direction, float radius)
    {
        return new Vector2(center.x + direction.x * radius, center.y + direction.y * radius);
    }
}