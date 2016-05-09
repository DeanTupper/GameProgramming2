package com.mygdx.game.components.collidables;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Component;
import com.mygdx.game.components.movables.Movable;
import com.mygdx.game.utils.collision.Collision;

public abstract class Collidable implements Component
{
    protected final Movable movable;

    public Collidable(Movable movable)
    {
        this.movable = movable;
    }

    public Vector2 getPosition()
    {
        return movable.getPosition();
    }

    public Vector2 getVelocity()
    {
        return movable.getVelocity();
    }

    public abstract void resolveCollision(Collidable other, Collision collision);


    @Override
    public String toString()
    {
        return "at " + movable.getPosition() + " with velocity " + movable.getVelocity();
    }
}
