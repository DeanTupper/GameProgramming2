package com.mygdx.game.subsystems.BoardManagerSubSystem;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Ball;
import com.mygdx.game.entities.ColorType;
import com.mygdx.game.entities.CornerBumper;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Pylon;
import com.mygdx.game.subsystems.Subsystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class BoardManager implements Subsystem
{
    private static final float BALL_RADIUS = 1f;
    private static BoardManager instance;

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    private final Set<Ball> balls = new HashSet<Ball>();
    private final Set<Pylon> pylons = new HashSet<Pylon>();
    private final Set<CornerBumper> cornerBumpers = new HashSet<CornerBumper>();

    private static final DefaultStateMachine<BoardManager, BallState> ballState = new DefaultStateMachine<BoardManager, BallState>(BoardManager.get(), BallState.INITIAL_STATE);

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

    private void spawnBall(BallSpawns ballSpawns, ColorType colorType)
    {
        new Ball(ballSpawns.getPosition(), ballSpawns.getVelocity(), colorType, BALL_RADIUS);
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

    public void register(Entity entity)
    {
        if (entity instanceof Ball)
        {
            balls.add(((Ball) entity));
        }
        else if (entity instanceof Pylon)
        {
            pylons.add(((Pylon) entity));
        }
        else if (entity instanceof CornerBumper)
        {
            cornerBumpers.add(((CornerBumper) entity));
        }
        else
        {
            throw new AssertionError("Unknown entity type: " + entity);
        }
    }

    public void remove(Entity entity)
    {
        if (entity instanceof Ball)
        {
            balls.remove(((Ball) entity));
        }
        else if (entity instanceof Pylon)
        {
            pylons.remove(((Pylon) entity));
        }
        else if (entity instanceof CornerBumper)
        {
            cornerBumpers.remove(((CornerBumper) entity));
        }
        else
        {
            throw new AssertionError("Unknown entity type: " + entity);
        }
    }

    public Set<CornerBumper> getCornerBumpers()
    {
        return cornerBumpers;
    }

    public Set<Ball> getBalls()
    {
        return balls;
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
                        //instance.spawnBall(BallSpawns.BOTTOM_LEFT, ColorType.BLUE);
                        instance.spawnBall(BallSpawns.TOP_RIGHT, ColorType.RED);

                        //instance.spawnPylon(60,50);
                        //instance.spawnPylon(55,50);

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
        new Pylon(new Vector2(x, y), ColorType.BLUE);
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
