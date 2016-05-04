package com.mygdx.game.utils.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.CircleCollidable;
import com.mygdx.game.components.TriangleCollidable;
import com.mygdx.game.utils.shapes.Line;
import com.mygdx.game.utils.shapes.Triangle;

public class CircleTriangleCollision extends Collision
{
    private CircleCollidable circleCollidable;
    private TriangleCollidable triangleCollidable;

    public Vector2 circleVelocity;
    public Vector2 circleVelNor;

    public CircleTriangleCollision(CircleCollidable a, TriangleCollidable b, long deltaInMillis)
    {
        super(a, b, deltaInMillis);

        circleCollidable = a;
        triangleCollidable = b;
    }

    public void calculateTimeToIntersection()
    {
        Triangle triangle = triangleCollidable.getBounds();
        Line collidableEdge = triangleCollidable.getBounds().edge;

        circleVelocity = circleCollidable.getVelocity();
        Vector2 center = circleCollidable.getPosition();

        Line circleTrajectory = Line.getLineFromPointAndVelocity(center, circleVelocity);

        Vector2 intersectionPoint = circleTrajectory.findIntersectionPointWith(collidableEdge);

        if (intersectionPoint != null && !triangle.isPointOutOfBounds(intersectionPoint))
        {
            circleVelNor = circleVelocity.cpy().nor();

            Vector2 closestPointOnCircle = CollisionUtils.getPointOnCircle(center, circleVelNor, circleCollidable.getRadius());

            if (intersectionPoint.y == Float.POSITIVE_INFINITY)
            {
                System.err.println("CircleTriangleCollision::calculateTimeToIntersection - intersectionPoint.y == Float.POSITIVE_INFINITY for circle " + center + " w/ v: " + circleVelocity + " and triangle: " + triangleCollidable.getBounds());
            }

            timeToCollisionX = (intersectionPoint.x - closestPointOnCircle.x) / circleVelocity.x;
            timeToCollisionY = (intersectionPoint.y - closestPointOnCircle.y) / circleVelocity.y;

            closestPointOnA = closestPointOnCircle;
            closestPointOnB = intersectionPoint;

            System.err.println("CircleTriangleCollision::calculateTimeToIntersection - t: " + timeToCollisionX + "," + timeToCollisionY);
        }
    }

    private Vector2 getIntersectionPointOnTriangle()
    {
        return null;
    }

    public CircleTriangleCollision update()
    {
        closestPointOnA = CollisionUtils.getPointOnCircle(circleCollidable.getPosition(), circleVelNor, circleCollidable.getRadius());

        timeToCollisionX = (closestPointOnB.x - closestPointOnA.x) / circleVelocity.x;
        timeToCollisionY = (closestPointOnB.y - closestPointOnA.y) / circleVelocity.y;
        System.err.println("CircleTriangleCollision::update - timeToCollision: " + timeToCollisionX + "," + timeToCollisionY);

        return this;
    }
}
