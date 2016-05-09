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
        resetInitialVelocities();

        Triangle triangle = triangleCollidable.getBounds();
        Line collidableEdge = triangleCollidable.getBounds().edge;

        circleVelocity = circleCollidable.getVelocity();
        Vector2 center = circleCollidable.getPosition();

        Line circleTrajectory = Line.getLineFromPointAndVelocity(center, circleVelocity);

        Vector2 intersectionPoint = circleTrajectory.findIntersectionPointWith(collidableEdge);

        System.err.println("CircleTriangleCollision::calculateTimeToCollision - intersectionPoint: " + intersectionPoint);

        if (intersectionPoint != null && !triangle.isPointOutOfBounds(intersectionPoint))
        {
            circleVelNor = circleVelocity.cpy().nor();

            Vector2 closestPointOnCircle = CollisionUtils.getPointOnCircle(center, circleVelNor, circleCollidable.getRadius());

            if (intersectionPoint.y == Float.POSITIVE_INFINITY)
            {
            }

            System.err.println("CircleTriangleCollision::calculateTimeToCollision - Vc: " + circleVelocity + "; Pc: " + closestPointOnCircle + "; Pt: " + intersectionPoint);

            float a = circleVelocity.dot(circleVelocity);
            float b = 2 * (closestPointOnCircle.dot(circleVelocity) - intersectionPoint.dot(circleVelocity));
            float c = closestPointOnCircle.dot(closestPointOnCircle) + intersectionPoint.dot(intersectionPoint) - (2 * intersectionPoint.dot(closestPointOnCircle));

            timeToCollision = CollisionUtils.solveQuadraticEquation(a, b, c);

            willCollide = timeToCollision < Float.MAX_VALUE && timeToCollision > 0f;

        }
    }
}
