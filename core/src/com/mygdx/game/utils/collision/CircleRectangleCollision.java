package com.mygdx.game.utils.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.collidables.CircleCollidable;
import com.mygdx.game.components.collidables.GoalCollidable;
import com.mygdx.game.components.collidables.RectangleCollidable;
import com.mygdx.game.entities.Goal;
import com.mygdx.game.utils.shapes.Line;
import com.mygdx.game.utils.shapes.LineSegment;

public class CircleRectangleCollision extends Collision
{
    private final CircleCollidable circleCollidable;
    private final RectangleCollidable rectangle;

    public CircleRectangleCollision(CircleCollidable a, RectangleCollidable b)
    {
        super(a, b);

        circleCollidable = a;
        rectangle = b;
    }

    @Override
    public void calculateTimeToCollision()
    {
        System.err.println("CircleRectangleCollision::calculateTimeToCollision");
        resetInitialVelocities();
        LineSegment collidableEdge = rectangle.getCollidableEdge();

        timeToCollision = CollisionUtils.getTimeToCollisionOfCircleWithEdge(circleCollidable.getPosition(), circleCollidable.getVelocity(), circleCollidable.getRadius(), collidableEdge);

        willCollide = timeToCollision < Float.MAX_VALUE && timeToCollision > 0f;

        System.err.println("CircleRectangleCollision::calculateTimeToCollision - timeToCollision: " + timeToCollision);
    }

    public Vector2 getIntersectionPoint()
    {
        Line circleTrajectory = Line.getLineFromPointAndVelocity(circleCollidable.getPosition(), circleCollidable.getVelocity());

        LineSegment collidableEdge = rectangle.getCollidableEdge();
        Line otherLine = Line.getLineFromTwoPoints(collidableEdge.pointA(), collidableEdge.pointB());

        return circleTrajectory.findIntersectionPointWith(otherLine);
    }

    public String toString()
    {
        if (rectangle instanceof GoalCollidable)
        {
            return "Goal!";
        }
        else
        {
            return super.toString();
        }
    }
}
