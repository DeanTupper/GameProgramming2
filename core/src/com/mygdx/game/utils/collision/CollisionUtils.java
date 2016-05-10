package com.mygdx.game.utils.collision;

import static com.badlogic.gdx.math.Interpolation.circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.shapes.Line;
import com.mygdx.game.utils.shapes.LineSegment;
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

    public static Vector2 closestPointOnLineTo(Vector2 point, Vector2 lineSegA, Vector2 lineSegB)
    {
        float A1 = lineSegB.y - lineSegA.y;
        float B1 = lineSegA.x - lineSegB.x;

        float C1 = (A1 * lineSegA.x) + (B1 * lineSegA.y);
        float C2 = -B1 * point.x + A1 * point.y;

        float determinant = (A1 * A1) - (-B1 * B1);
        float cx;
        float cy;

        if (determinant != 0f)
        {
            cx = (A1 * C1 - B1 * C2) / determinant;
            cy = (A1 * C2 - (-B1 * C1)) / determinant;
        }
        else
        {
            cx = point.x;
            cy = point.y;
        }

        return new Vector2(cx, cy);
    }

    public static float solveQuadraticEquation(float a, float b, float c)
    {
        float timeToCollision;

        float discriminant = b * b - (4 * a * c);
        System.err.println("CollisionUtils::solveQuadraticEquation - a:[" + a + "], b:[" + b + "], c:[" + c + "] - discriminant: " + discriminant);

        if (Math.abs(discriminant) - 0.1f < 0f)
        {
            timeToCollision = -b / (2 * a);
        }
        else if (discriminant < 0f)
        {
            timeToCollision = Float.MAX_VALUE;
        }
        else
        {
            discriminant = (float) Math.sqrt(discriminant);

            float a2 = a * 2;

            float t1 = (-b - discriminant) / a2;
            float t2 = (-b + discriminant) / a2;

            boolean t1Neg = t1 < 0;
            boolean t2Neg = t2 < 0;

            if (t1Neg && t2Neg)
            {
                timeToCollision = Float.MAX_VALUE;
            }
            else if (t1Neg)
            {
                timeToCollision = t2;
            }
            else if (t2Neg)
            {
                timeToCollision = t1;
            }
            else
            {
                timeToCollision = t1 < t2 ? t1 : t2;
            }
        }

        return timeToCollision;
    }

    public static float getTimeToCollisionOfCircleWithEdge(Vector2 circleCenter, Vector2 circleVelocity, float radius, Vector2 edgePointA, Vector2 edgePointB)
    {
        return getTimeToCollisionOfCircleWithEdge(circleCenter, circleVelocity, radius, new LineSegment(edgePointA, edgePointB));
    }

    public static float getTimeToCollisionOfCircleWithEdge(Vector2 circleCenter, Vector2 circleVelocity, float radius, LineSegment edge)
    {
        Line circleTrajectory = Line.getLineFromPointAndVelocity(circleCenter, circleVelocity);

        Line otherLine = Line.getLineFromTwoPoints(edge.pointA(), edge.pointB());
        System.err.println("CollisionUtils::getTimeToCollisionOfCircleWithEdge - otherLine: " + otherLine);
        Vector2 intersectionPoint = circleTrajectory.findIntersectionPointWith(otherLine);
        System.err.println("CollisionUtils::getTimeToCollisionOfCircleWithEdge - intersectionPoint: " + intersectionPoint + "; edge.pointIsOnLine: " + edge.pointIsOnLine(intersectionPoint));

        if (intersectionPoint != null && edge.pointIsOnLine(intersectionPoint))
        {
            Vector2 circleVelNor = circleVelocity.cpy().nor();

            Vector2 closestPointOnCircle = CollisionUtils.getPointOnCircle(circleCenter, circleVelNor, radius);

            float a = circleVelocity.dot(circleVelocity);
            float b = 2 * (closestPointOnCircle.dot(circleVelocity) - intersectionPoint.dot(circleVelocity));
            float c = closestPointOnCircle.dot(closestPointOnCircle) + intersectionPoint.dot(intersectionPoint) - (2 * intersectionPoint.dot(closestPointOnCircle));

            return CollisionUtils.solveQuadraticEquation(a, b, c);
        }

        return Float.MAX_VALUE;
    }

    public static boolean isPointInRectangle(Vector2 intersectionPoint, Rectangle rect)
    {
        boolean pointWithinXBounds = intersectionPoint.x >= rect.x && intersectionPoint.x <= rect.right;
        boolean pointWithinYBounds = intersectionPoint.y >= rect.y && intersectionPoint.y <= rect.top;
        return pointWithinXBounds && pointWithinYBounds;
    }
}