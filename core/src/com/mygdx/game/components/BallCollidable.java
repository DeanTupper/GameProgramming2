package com.mygdx.game.components;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Ball;

public class BallCollidable extends CircleCollidable
{
    private final Ball ball;

    public BallCollidable(Ball ball, Movable movable, float radius)
    {
        super(movable, radius);

        this.ball = ball;
    }

    public void resolveCollision(Collidable other)
    {
        if (other instanceof BallCollidable)
        {
            BallCollidable otherBall = ((BallCollidable) other);

            Vector2 myOrientVec = movable.getPosition().cpy().nor();
            Vector2 otherOrientVec = otherBall.getPosition().cpy().nor();

            float crossProduct = myOrientVec.crs(otherOrientVec);

            Vector2 myVelocity = movable.getVelocity();
            Vector2 otherVelocity;

            System.err.println("BallCollidable::resolveCollision - crossProduct: " + crossProduct);

            if (crossProduct > 0)
            {
                myVelocity.x = -myVelocity.x;
            }
            else
            {

            }
        }
    }
}
