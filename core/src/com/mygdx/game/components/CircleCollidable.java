package com.mygdx.game.components;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.collision.CircleCircleCollision;
import com.mygdx.game.utils.collision.CircleTriangleCollision;
import com.mygdx.game.utils.collision.Collision;
import com.mygdx.game.utils.collision.CollisionUtils;

public abstract class CircleCollidable extends Collidable
{
    private final float radius;

    public CircleCollidable(Movable movable, float radius)
    {
        super(movable);

        this.radius = radius;
    }

    @Override
    public Collision checkForCollision(Collidable other, long deltaInMillis)
    {
        if (other instanceof CircleCollidable)
        {
            CircleCollidable otherCircle = ((CircleCollidable) other);

            Movable otherMovable = otherCircle.movable;
            Vector2 otherVelocity = otherMovable.velocity;

            if (otherVelocity.x == 0f && otherVelocity.y == 0f)
            {
                float distSq = otherCircle.getPosition().dst2(getPosition());
                float sumRadiiSq = (radius * radius) + (otherCircle.radius * otherCircle.radius);

                distSq -= sumRadiiSq;

                if (movable.getVelocity().len2() < distSq)
                {
                    soonestCollision.willCollide = true;
                    return soonestCollision;
                }

                Vector2 velocityNorm = movable.getVelocity().cpy().nor();
            }
        }
        else if (other instanceof TriangleCollidable)
        {
            TriangleCollidable otherTriangle = ((TriangleCollidable) other);

            return checkCircleTriangleCollision(otherTriangle, deltaInMillis);
        }

        return soonestCollision;
    }

    private CircleCircleCollision checkCircleCircleCollision(CircleCollidable other, long deltaInMillis)
    {
        CircleCircleCollision circleCircleCollision = null;

        if (soonestCollision.isOf(this, other))
        {
            circleCircleCollision = ((CircleCircleCollision) soonestCollision);
        }

        return circleCircleCollision;
    }

    private CircleTriangleCollision checkCircleTriangleCollision(TriangleCollidable triangle, long deltaInMillis)
    {
        CircleTriangleCollision collision;

        if (soonestCollision != null && soonestCollisionWithSameTriangle(triangle))
        {
            collision = ((CircleTriangleCollision) soonestCollision);

            if (shouldCheckForUpdate())
            {
                if (simpleToUpdate(collision))
                {
                    return collision.update();
                }
            }
            else
            {
                return collision;
            }
        }

        return calcNewCircleTriangleCollision(triangle, deltaInMillis);
    }

    private boolean shouldCheckForUpdate()
    {
        return soonestCollision.timeToCollisionX > 0 && soonestCollision.timeToCollisionY > 0;
    }

    private boolean soonestCollisionWithSameTriangle(TriangleCollidable triangle)
    {
        return soonestCollision.isOf(triangle, this);
    }

    private boolean simpleToUpdate(CircleTriangleCollision collision)
    {
        return movable.getVelocity().equals(collision.circleVelocity);
    }

    private CircleTriangleCollision calcNewCircleTriangleCollision(TriangleCollidable other, long deltaInMillis)
    {
        CircleTriangleCollision collision = new CircleTriangleCollision(this, other, deltaInMillis);
        collision.calculateTimeToIntersection();
        return collision;
    }


    @Override
    public boolean collidesWith(Collidable other, long deltaInMillis)
    {
        if (other instanceof CircleCollidable)
        {
            CircleCollidable otherCircle = (CircleCollidable) other;

            return CollisionUtils.circleIntersectsCircle(movable.getPosition(), radius, otherCircle.getPosition(), otherCircle.radius);
        }

        throw new AssertionError("CircleCollidable::collidesWith - Unfinished");
    }

    public float getRadius()
    {
        return radius;
    }
}
