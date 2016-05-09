package com.mygdx.game.components.collidables;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.movables.Movable;
import com.mygdx.game.entities.Ball;
import com.mygdx.game.utils.collision.Collision;

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
    public void resolveCollision(Collidable other, Collision collision)
    {
        if (collision.willCollide)
        {
            if (other instanceof CircleCollidable)
            {
                if (other instanceof BallCollidable)
                {
                    BallCollidable otherBall = ((BallCollidable) other);

                    Vector2 thisVelCopy = getVelocity().cpy();
                    getVelocity().set(otherBall.getVelocity());
                    otherBall.getVelocity().set(thisVelCopy);

                    collision.willCollide = false;
                }
                else
                {
                    CircleCollidable otherCircle = ((CircleCollidable) other);
                }
            }
        }
    }
}
