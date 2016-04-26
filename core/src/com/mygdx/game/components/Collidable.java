package com.mygdx.game.components;

import com.badlogic.gdx.math.Vector2;

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

    public abstract boolean collidesWith(Collidable other);
    public abstract void resolveCollision(Collidable other);
}
