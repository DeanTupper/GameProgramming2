package com.mygdx.game.utils.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Collidable;

import java.util.Arrays;

public abstract class Collision
{
    public final Collidable a;
    public final Collidable b;
    public final long deltaInMillis;

    public boolean willCollide = false;

    public Vector2 closestPointOnA = new Vector2();
    public Vector2 closestPointOnB = new Vector2();

    public float timeToCollisionX = Float.MAX_VALUE;
    public float timeToCollisionY = Float.MAX_VALUE;

    public float timeToCollision = Float.MAX_VALUE;

    public Collision(Collidable a, Collidable b, long deltaInMillis)
    {
        this.a = a;
        this.b = b;
        this.deltaInMillis = deltaInMillis;
    }

    public boolean isOf(Collidable a, Collidable b)
    {
        return (a.equals(this.a) && b.equals(this.b))
                || (a.equals(this.b) && b.equals((this.a)));
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
}
