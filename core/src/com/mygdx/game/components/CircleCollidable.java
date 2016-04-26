package com.mygdx.game.components;

import com.mygdx.game.utils.Collision;

public abstract class CircleCollidable extends Collidable
{
    private final float radius;

    public CircleCollidable(Movable movable, float radius)
    {
        super(movable);

        this.radius = radius;
    }

    @Override
    public boolean collidesWith(Collidable other)
    {
        if (other instanceof CircleCollidable)
        {
            CircleCollidable otherCircle = (CircleCollidable) other;

            return Collision.circleIntersectsCircle(movable.getPosition(), radius, otherCircle.getPosition(), otherCircle.radius);
        }

        throw new AssertionError("Unfinished");
    }
}
