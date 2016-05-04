package com.mygdx.game.components;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.collision.Collision;

public abstract class Collidable implements Component
{
    protected final Movable movable;
    protected Collision imminentCollision = null;

    public Collidable(Movable movable)
    {
        this.movable = movable;
    }

    public Vector2 getPosition()
    {
        return movable.position;
    }

    public Vector2 getVelocity()
    {
        return movable.velocity;
    }

    public abstract void resolveCollision(Collidable other);

    public abstract Collision checkForCollision(Collidable collidable, long deltaInMillis);

    public void updateImminentCollisionIfSooner(Collision other)
    {
        if (imminentCollision == null || !imminentCollision.willCollide || other.timeToCollision < imminentCollision.timeToCollision)
        {
            imminentCollision = other;
        }
    }

    @Override
    public String toString()
    {
        return "at " + movable.getPosition() + " with velocity " + movable.getVelocity();
    }
}
