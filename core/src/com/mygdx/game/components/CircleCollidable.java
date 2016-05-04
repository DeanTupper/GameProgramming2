package com.mygdx.game.components;

import com.mygdx.game.utils.collision.CircleCircleCollision;
import com.mygdx.game.utils.collision.CircleTriangleCollision;
import com.mygdx.game.utils.collision.Collision;

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

            return checkCircleCircleCollision(otherCircle, deltaInMillis);
        }
        else if (other instanceof TriangleCollidable)
        {
            TriangleCollidable otherTriangle = ((TriangleCollidable) other);

            return checkCircleTriangleCollision(otherTriangle, deltaInMillis);
        }

        return imminentCollision;
    }

    private CircleCircleCollision checkCircleCircleCollision(CircleCollidable other, long deltaInMillis)
    {
        CircleCircleCollision collision;

        if (imminentCollision != null && imminentCollision.isOf(this, other))
        {
            collision = ((CircleCircleCollision) imminentCollision);

            if (collision.willCollide)
            {
                if (simpleToUpdate(collision))
                {
                    return collision.update(deltaInMillis);
                }
            }
            else
            {
                System.err.println("CircleCollidable::checkCircleCircleCollision - not bothering to update collision");
                return collision;
            }
        }

        return calcNewCircleCircleCollision(other, deltaInMillis);
    }

    private boolean shouldCheckForUpdate(CircleCircleCollision collision)
    {
        return collision.timeToCollision >= 0f;
    }

    private CircleCircleCollision calcNewCircleCircleCollision(CircleCollidable other, long deltaInMillis)
    {
        CircleCircleCollision collision = new CircleCircleCollision(this, other, deltaInMillis);
        collision.calculateTimeToIntersection();
        return collision;
    }

    private CircleTriangleCollision checkCircleTriangleCollision(TriangleCollidable triangle, long deltaInMillis)
    {
        CircleTriangleCollision collision;

        if (imminentCollision != null && soonestCollisionWithSameTriangle(triangle))
        {
            collision = ((CircleTriangleCollision) imminentCollision);

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
        return imminentCollision.timeToCollisionX > 0 && imminentCollision.timeToCollisionY > 0;
    }

    private boolean soonestCollisionWithSameTriangle(TriangleCollidable triangle)
    {
        return imminentCollision.isOf(triangle, this);
    }

    private boolean simpleToUpdate(CircleCircleCollision collision)
    {
        return collision.a.getVelocity().equals(collision.velocityA) && collision.b.getVelocity().equals(collision.velocityB);
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

    public float getRadius()
    {
        return radius;
    }

    @Override
    public String toString()
    {
        return "CircleCollidable " + super.toString();
    }
}
