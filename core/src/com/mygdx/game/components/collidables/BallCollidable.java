package com.mygdx.game.components.collidables;

import com.mygdx.game.components.movables.Movable;
import com.mygdx.game.entities.Ball;

public class BallCollidable extends CircleCollidable
{
    private final Ball ball;

    public BallCollidable(Ball ball, Movable movable, float radius)
    {
        super(movable, radius);

        this.ball = ball;
    }

    public Ball getBall()
    {
        return ball;
    }

    @Override
    public void resolveCollision(Collidable other)
    {
    }
}
