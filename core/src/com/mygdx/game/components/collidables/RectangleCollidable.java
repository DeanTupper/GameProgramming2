package com.mygdx.game.components.collidables;

import com.mygdx.game.components.movables.Movable;
import com.mygdx.game.utils.collision.Collision;
import com.mygdx.game.utils.shapes.Rectangle;

public class RectangleCollidable extends Collidable
{
    private final Rectangle movementBounds;

    private final float paddleWidth;
    private final float paddleHeight;

    public RectangleCollidable(Movable movable, Rectangle movementBounds, float width, float height)
    {
        super(movable);

        this.movementBounds = movementBounds;

        paddleWidth = width;
        paddleHeight = height;
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
