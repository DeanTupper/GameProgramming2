package com.mygdx.game.components.collidables;

import com.mygdx.game.components.movables.Movable;

public abstract class CircleCollidable extends Collidable
{
    private final float radius;

    public CircleCollidable(Movable movable, float radius)
    {
        super(movable);

        this.radius = radius;
    }

    public float getRadius()
    {
        return radius;
    }

    @Override
    public String toString()
    {
        return "CircleCollidable " + super.toString();
    }
}
