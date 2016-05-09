package com.mygdx.game.components.movables;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameWorld;
import com.mygdx.game.entities.Ball;

public class BallMovable extends Movable
{

    private final Ball ball;

    public BallMovable(Vector2 position, Vector2 velocity, Ball ball)
    {
        super(position, velocity);
        this.ball = ball;
    }

    @Override
    public void move(float worldTimeStep)
    {
        super.move(worldTimeStep);
        if (position.x < -5)
        {
            GameWorld.player4.decrementScore();
            ball.destroy();
        }
        else if (position.x > 105)
        {
            GameWorld.player2.decrementScore();
            ball.destroy();
        }
        else if (position.y > 105)
        {
            GameWorld.player3.decrementScore();
            ball.destroy();
        }
        else if (position.y < -5)
        {
            GameWorld.player1.decrementScore();
            ball.destroy();
        }
    }
}
