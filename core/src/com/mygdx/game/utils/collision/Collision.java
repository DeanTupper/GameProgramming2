package com.mygdx.game.utils.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.collidables.BallCollidable;
import com.mygdx.game.components.collidables.CircleCollidable;
import com.mygdx.game.components.collidables.Collidable;
import com.mygdx.game.components.collidables.GoalCollidable;
import com.mygdx.game.components.collidables.RectangleCollidable;
import com.mygdx.game.components.collidables.TriangleCollidable;
import com.mygdx.game.entities.Ball;

import java.util.Arrays;

public abstract class Collision implements Comparable<Collision>
{
    public final Collidable a;
    public final Collidable b;

    public boolean willCollide = false;

    public Vector2 initVelocityA;
    public Vector2 initVelocityB;

    public float timeToCollision = Float.MAX_VALUE;

    public Collision(Collidable a, Collidable b)
    {
        this.a = a;
        this.b = b;
    }

    public abstract void calculateTimeToCollision();

    protected void resetInitialVelocities()
    {
        initVelocityA = a.getVelocity().cpy();
        initVelocityB = b.getVelocity().cpy();
    }

    public boolean isOf(Collidable a, Collidable b)
    {
        return (a.equals(this.a) && b.equals(this.b))
                || (a.equals(this.b) && b.equals((this.a)));
    }

    public Collision update(float worldTimeStep)
    {
        if (willCollide)
        {
            timeToCollision -= worldTimeStep;

            System.err.println("Collision::update - worldTimeStep:[" + worldTimeStep + "] - timeToCollision: " + timeToCollision);
        }

        return this;
    }

    public boolean isEasyToUpdate()
    {
        return a.getVelocity().equals(initVelocityA)
                && b.getVelocity().equals(initVelocityB);
    }

    public boolean shouldBeUpdated()
    {
        return timeToCollision > 0 && timeToCollision != Float.MAX_VALUE;
    }

    @Override
    public String toString()
    {
        String collisionStr = willCollide ? " will collide in " : " will not collide in ";
        return "Collision between " + a + " and " + b + collisionStr + timeToCollision;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Collision))
        {
            return false;
        }
        Collision other = ((Collision) o);

        return other == this || other.isOf(a, b);
    }

    @Override
    public int hashCode()
    {
        Object[] toHash = {a, b};
        return Arrays.hashCode(toHash);
    }

    @Override
    public int compareTo(Collision other)
    {
        return Float.compare(timeToCollision, other.timeToCollision);
    }

    public void resolve()
    {
        a.resolveCollision(b, this);
        b.resolveCollision(a, this);
    }

    public static Collision getCollision(Collidable a, Collidable b)
    {
        boolean collidableAIsCircle = a instanceof CircleCollidable;
        boolean collidableBIsCircle = b instanceof CircleCollidable;

        if (collidableAIsCircle)
        {
            if (collidableBIsCircle)
            {
                return new CircleCircleCollision(((CircleCollidable) a), ((CircleCollidable) b));
            }
            else if (b instanceof TriangleCollidable)
            {
                return new CircleTriangleCollision(((CircleCollidable) a), ((TriangleCollidable) b));
            }
            else if (b instanceof RectangleCollidable)
            {
                return new CircleRectangleCollision(((CircleCollidable) a), ((RectangleCollidable) b));
            }
        }
        else if (collidableBIsCircle)
        {
            if (a instanceof TriangleCollidable)
            {
                return new CircleTriangleCollision(((CircleCollidable) b), ((TriangleCollidable) a));
            }
            else if (a instanceof RectangleCollidable)
            {
                return new CircleRectangleCollision(((CircleCollidable) b), ((RectangleCollidable) a));
            }
        }

        throw new IllegalArgumentException("Neither collidable is a circle and at least one must be a circle");
    }
}
