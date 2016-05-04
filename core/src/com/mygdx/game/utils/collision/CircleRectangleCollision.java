package com.mygdx.game.utils.collision;

import com.mygdx.game.components.collidables.CircleCollidable;
import com.mygdx.game.components.collidables.RectangleCollidable;

public class CircleRectangleCollision extends Collision
{
    private final CircleCollidable circle;
    private final RectangleCollidable rectangle;

    public CircleRectangleCollision(CircleCollidable a, RectangleCollidable b)
    {
        super(a, b);

        circle = a;
        rectangle = b;
    }

    @Override
    public void calculateTimeToCollision()
    {

    }
}
