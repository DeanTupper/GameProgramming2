package com.mygdx.game.components;

import com.mygdx.game.utils.collision.Collision;

public class RectangleCollidable extends Collidable
{
    public RectangleCollidable(Movable movable)
    {
        super(movable);
    }

    @Override
    public boolean collidesWith(Collidable other, long deltaInMillis)
    {
        return false;
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
}
