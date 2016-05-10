package com.mygdx.game.components.collidables;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.movables.Movable;
import com.mygdx.game.entities.Ball;
import com.mygdx.game.utils.Direction;
import com.mygdx.game.utils.collision.CircleRectangleCollision;
import com.mygdx.game.utils.collision.Collision;
import com.mygdx.game.utils.collision.CollisionUtils;
import com.mygdx.game.utils.shapes.Rectangle;

public class BallCollidable extends CircleCollidable
{
    private final Ball ball;

    public BallCollidable(Ball ball, Movable movable, float radius)
    {
        super(movable, radius);

        this.ball = ball;
    }

    public Ball getBall()
    {
        return ball;
    }

    @Override
    public void resolveCollision(Collidable other, Collision collision)
    {
        if (collision.willCollide)
        {
            if (other instanceof CircleCollidable)
            {
                if (other instanceof BallCollidable)
                {
                    BallCollidable otherBall = ((BallCollidable) other);

                    Vector2 thisVelCopy = getVelocity().cpy();
                    getVelocity().set(otherBall.getVelocity());
                    otherBall.getVelocity().set(thisVelCopy);

                    collision.willCollide = false;
                }
                else
                {
                    CircleCollidable otherCircle = ((CircleCollidable) other);
                }
            }
            else if (other instanceof TriangleCollidable)
            {
                TriangleCollidable triangle = ((TriangleCollidable) other);

                Vector2 edgeNormal = triangle.getBounds().edgeNormal;

                Vector2 vel = getVelocity();

                Vector2 newVel = new Vector2(-vel.y, -vel.x);

                float dot = newVel.dot(edgeNormal);

                if (dot < 0f)
                {
                    newVel.x = -newVel.x;
                    newVel.y = -newVel.y;
                }

                getVelocity().set(newVel);
            }
            else if (other instanceof RectangleCollidable)
            {
                RectangleCollidable rect = ((RectangleCollidable) other);
                Vector2 vel = getVelocity();

                if (other instanceof GoalCollidable)
                {
                    CircleRectangleCollision circleRectangleCollision = ((CircleRectangleCollision) collision);
                    GoalCollidable goalCollidable = ((GoalCollidable) other);

                    Vector2 intersectionPoint = circleRectangleCollision.getIntersectionPoint();

                    Rectangle playerPaddleBounds = goalCollidable.getPlayerPaddleBounds();

                    if (CollisionUtils.isPointInRectangle(intersectionPoint, playerPaddleBounds))
                    {
                        changeVelocityBasedOnCollisionEdge(rect.getCollidableEdgeDir());
                    }
                    else
                    {
                        circleRectangleCollision.willCollide = false;
                        circleRectangleCollision.timeToCollision = Float.MAX_VALUE;
                    }
                }
                else
                {
                    changeVelocityBasedOnCollisionEdge(rect.getCollidableEdgeDir());
                }
            }
        }
    }

    private void changeVelocityBasedOnCollisionEdge(Direction dir)
    {
        Vector2 vel = getVelocity();

        switch (dir)
        {
            case NORTH:
            case SOUTH:
                vel.y = -vel.y;
                break;
            case EAST:
            case WEST:
                vel.x = -vel.x;
                break;
            default:
                throw new AssertionError("shouldn't happen");
        }
    }
}
