package com.mygdx.game.components.collidables;

import com.mygdx.game.components.movables.Movable;
import com.mygdx.game.utils.collision.Collision;

public class RectangleCollidable extends Collidable
{
    public RectangleCollidable(Movable movable)
    {
        super(movable);
    }

    @Override
    public void resolveCollision(Collidable other, Collision collision)
    {
    }

    @Override
    public String toString()
    {
        return "RectangleCollidable " + super.toString();
    }
}
