package com.mygdx.game.components.collidables;

import com.mygdx.game.components.movables.Movable;
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
    public void resolveCollision(Collidable other, Collision collision)
    {

    }

    @Override
    public String toString()
    {
        return "TriangleCollidable " + super.toString();
    }
}
