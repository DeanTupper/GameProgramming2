package com.mygdx.game.components;

import com.badlogic.gdx.math.Vector2;

public class BallCollidable extends CircleCollidable
{
    public BallCollidable(Movable movable, float radius)
    {
        super(movable, radius);
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
