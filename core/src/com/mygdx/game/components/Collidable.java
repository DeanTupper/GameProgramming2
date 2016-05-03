package com.mygdx.game.components;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.collision.Collision;

public abstract class Collidable implements Component
{
    protected final Movable movable;
    protected Collision soonestCollision = null;

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

    public abstract boolean collidesWith(Collidable other, long deltaInMillis);

    public abstract void resolveCollision(Collidable other);

    public abstract Collision checkForCollision(Collidable collidable, long deltaInMillis);

    public void updateCollisionIfSooner(Collision other)
    {
        if (soonestCollision == null || !soonestCollision.willCollide)
        {
            soonestCollision = other;
        }
    }
}
