package com.mygdx.game.components;

import com.mygdx.game.utils.collision.Collision;

public class RectangleCollidable extends Collidable
{
    public RectangleCollidable(Movable movable)
    {
        super(movable);
    }

    @Override
    public void resolveCollision(Collidable other)
    {

    }

    @Override
    public Collision checkForCollision(Collidable collidable, long deltaInMillis)
    {
        return null;
    }

    @Override
    public String toString()
    {
        return "RectangleCollidable " + super.toString();
    }
}
