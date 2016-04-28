package com.mygdx.game.subsystems.BoardManagerSubSystem;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Ball;
import com.mygdx.game.entities.ColorType;
import com.mygdx.game.entities.Pylon;
import com.mygdx.game.subsystems.Subsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BoardManager implements Subsystem
{
    private static final float BALL_RADIUS = 1f;
    private static BoardManager instance;

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final List<Ball> balls = new ArrayList<Ball>();
    private final List<Pylon> pylons = new ArrayList<Pylon>();
    private static final DefaultStateMachine<BoardManager, BallState> ballState = new DefaultStateMachine(BoardManager.get(), BallState.INITIAL_STATE);
    private float lastUpdate = 0;

    public static BoardManager get()
    {
        if (instance == null)
        {
            instance = new BoardManager();
        }

        return instance;
    }

    private BoardManager()
    {
    }

    @Override
    public void update(long deltaInMillis)
    {
        ballState.update();
    }

    private void spawnBall(BallSpawns ballSpawns)
    {
        new Ball(ballSpawns.getPosition(), ballSpawns.getVelocity(), ColorType.BLUE, BALL_RADIUS);
    }

    public void registerBall(Ball ball)
    {
        balls.add(ball);
    }

    public void removeBall(Ball ball)
    {
        balls.remove(ball);
    }

    public void registerPylon(Pylon pylon)
    {
        pylons.add(pylon);
    }

    public void removePylon(Pylon pylon)
    {
        pylons.remove(pylon);
    }


    public enum BallState implements State<BoardManager>
    {
        INITIAL_STATE()
                {
                    @Override
                    public void enter(BoardManager entity)
                    {

                    }

                    @Override
                    public void update(BoardManager entity)
                    {
                        System.out.println("ballStateeee");
                        //instance.spawnBall(BallSpawns.values()[0]);
                        //instance.spawnBall(BallSpawns.values()[3]);
                        ballState.changeState(BallState.NORMAL_STATE);
                    }

                    @Override
                    public void exit(BoardManager entity)
                    {

                    }

                    @Override
                    public boolean onMessage(BoardManager entity, Telegram telegram)
                    {
                        return false;
                    }
                },
        NORMAL_STATE()
                {
                    @Override
                    public void enter(BoardManager entity)
                    {

                    }

                    @Override
                    public void update(BoardManager entity)
                    {
                        if (instance.balls.isEmpty())
                        {
                            //instance.spawnBall(BallSpawns.values()[random.nextInt(BallSpawns.values().length)]);
                        }
                    }

                    @Override
                    public void exit(BoardManager entity)
                    {

                    }

                    @Override
                    public boolean onMessage(BoardManager entity, Telegram telegram)
                    {
                        return false;
                    }
                }
    }

    private void spawnPylon(int x, int y)
    {
        new Pylon(new Vector2(x,y),ColorType.BLUE);
    }

    public enum BallSpawns
    {
        BOTTOM_LEFT(new Vector2(10, 10), new Vector2(1, 1)),
        BOTTOM_RIGHT(new Vector2(90, 10), new Vector2(-1, 1)),
        TOP_LEFT(new Vector2(10, 90), new Vector2(1, -1)),
        TOP_RIGHT(new Vector2(90, 90), new Vector2(-1, -1));

        private final Vector2 position;
        private final Vector2 velocity;

        BallSpawns(Vector2 position, Vector2 velocity)
        {
            this.position = position;
            this.velocity = velocity;
        }

        public Vector2 getPosition()
        {
            return position;
        }

        public Vector2 getVelocity()
        {
            return velocity;
        }
    }
}
