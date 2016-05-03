package com.mygdx.game.components;

import com.mygdx.game.utils.collision.CircleTriangleCollision;
import com.mygdx.game.utils.collision.Collision;
import com.mygdx.game.utils.shapes.Triangle;

public class TriangleCollidable extends Collidable
{
    private final Triangle triangle;

    public TriangleCollidable(Movable movable, Triangle triangle)
    {
        super(movable);

        this.triangle = triangle;
    }

    public Triangle getBounds()
    {
        return triangle;
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
        Collision collision = null;

        if (collidable instanceof CircleCollidable)
        {
            CircleCollidable circleCollidable = ((CircleCollidable) collidable);
            collision = new CircleTriangleCollision(circleCollidable, this, deltaInMillis);
        }

        return collision;
    }
}
