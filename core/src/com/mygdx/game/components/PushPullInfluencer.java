package com.mygdx.game.components;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Ball;
import com.mygdx.game.entities.ColorType;

public class PushPullInfluencer implements PylonInfluencer
{
    private final ColorType type;
    private final Vector2 position;

    public PushPullInfluencer(Vector2 position, ColorType type)
    {
        this.position = position;
        this.type = type;
    }

    @Override
    public void influence(Ball ball)
    {
        if (this.type.equals(ball.getType()))
        {
            System.out.println("influence");
            pushInfluence(ball);
        }
        else
        {
//            pushInfluence(ball);
        }
    }

    private void pushInfluence(Ball ball)
    {
        Vector2 desired_velocity = ball.getPosition().cpy().sub(position);
        Vector2 steering = ball.getVelocity().cpy().sub(desired_velocity);
        steering.set(-steering.x, -steering.y);
//        System.out.println("steering = " + steering);
        if (ball.getVelocity().cpy().nor().dot(steering.nor()) < -.999f)
        {
            steering.x = steering.x + .5f;
        }
        steering.clamp(-.1f, .1f);
        ball.getVelocity().add(steering);
    }
}
