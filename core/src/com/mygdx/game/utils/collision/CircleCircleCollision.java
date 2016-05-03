package com.mygdx.game.utils.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.CircleCollidable;

public class CircleCircleCollision extends Collision
{
    public Vector2 velocityA;
    public Vector2 velocityB;

    public CircleCircleCollision(CircleCollidable a, CircleCollidable b, long deltaInMillis)
    {
        super(a, b, deltaInMillis);
    }
}
