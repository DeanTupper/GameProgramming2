package com.mygdx.game.utils.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.collidables.CircleCollidable;
import com.mygdx.game.components.collidables.TriangleCollidable;
import com.mygdx.game.subsystems.RenderSubsystem;
import com.mygdx.game.utils.shapes.Line;
import com.mygdx.game.utils.shapes.Triangle;

public class CircleTriangleCollision extends Collision
{
    private CircleCollidable circleCollidable;
    private TriangleCollidable triangleCollidable;

    public Vector2 circleVelocity;
    public Vector2 circleVelNor;

    public CircleTriangleCollision(CircleCollidable a, TriangleCollidable b)
    {
        super(a, b);

        circleCollidable = a;
        triangleCollidable = b;
    }

    public void calculateTimeToCollision()
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
                System.err.println("CircleTriangleCollision::calculateTimeToCollision - intersectionPoint.y == Float.POSITIVE_INFINITY for circle " + center + " w/ v: " + circleVelocity + " and triangle: " + triangleCollidable.getBounds());
            }

            timeToCollisionX = (intersectionPoint.x - closestPointOnCircle.x) / circleVelocity.x;
            timeToCollisionY = (intersectionPoint.y - closestPointOnCircle.y) / circleVelocity.y;

            closestPointOnA = closestPointOnCircle;
            closestPointOnB = intersectionPoint;

            System.err.println("CircleTriangleCollision::calculateTimeToCollision - t: " + timeToCollisionX + "," + timeToCollisionY);
        }
    }
}
