package com.mygdx.game.components.collidables;

import com.mygdx.game.components.movables.Movable;

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
    public String toString()
    {
        return "RectangleCollidable " + super.toString();
    }
}
