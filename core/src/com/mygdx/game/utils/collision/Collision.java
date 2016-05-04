package com.mygdx.game.utils.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.collidables.Collidable;

import java.util.Arrays;

public abstract class Collision implements Comparable<Collision>
{
    public final Collidable a;
    public final Collidable b;

    public boolean willCollide = false;

    public Vector2 initVelocityA;
    public Vector2 initVelocityB;

    public Vector2 closestPointOnA = new Vector2();
    public Vector2 closestPointOnB = new Vector2();

    public float timeToCollisionX = Float.MAX_VALUE;
    public float timeToCollisionY = Float.MAX_VALUE;

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
        if (willCollide && timeToCollision != Float.MAX_VALUE && timeToCollision > 0f) {
            timeToCollision -= worldTimeStep;
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
        return timeToCollision > 0;
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
        a.resolveCollision(b);
        b.resolveCollision(a);
    }
}
