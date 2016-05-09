package com.mygdx.game.subsystems.BoardManagerSubSystem;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameWorld;
import com.mygdx.game.components.collidables.BallCollidable;
import com.mygdx.game.entities.Ball;
import com.mygdx.game.entities.ColorType;
import com.mygdx.game.entities.CornerBumper;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Pylon;
import com.mygdx.game.subsystems.Subsystem;
import com.mygdx.game.utils.UpdateDelta;
import com.mygdx.game.utils.collision.CircleCircleCollision;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BoardManager implements Subsystem
{
    private static final float BALL_RADIUS = 1f;
    private static BoardManager instance;
    private final Random rand = new Random();

    private long nextBallSpawn = Integer.MAX_VALUE;
    private int nextPosition = 0;
    private long nextShiftChange = 0;

    private final Set<Ball> balls = new HashSet<Ball>();
    private final Set<Pylon> pylons = new HashSet<Pylon>();
    private final Set<CornerBumper> cornerBumpers = new HashSet<CornerBumper>();

    private final boolean spawnBalls = false;
    private final boolean spawnPylons = false;

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
    public void update(long deltaInMillis, UpdateDelta updateDelta)
    {
        if (balls.size() < 1)
        {
            //instance.spawnBall(BallSpawns.TEST_SPAWN_1, ColorType.BLUE);
            instance.spawnBall(BallSpawns.TEST_SPAWN_2, ColorType.RED);
        }

        if (spawnBalls)
        {
            ballUpdate(deltaInMillis);
        }
        if (spawnPylons)
        {
            pylonUpdate(deltaInMillis);
        }
    }

    private void ballUpdate(long deltaInMillis)
    {
        if (balls.size() < 10)
        {
            if (nextBallSpawn == Integer.MAX_VALUE)
            {
                decideNextBallSpawn();
            }
            else
            {
                nextBallSpawn = nextBallSpawn - deltaInMillis;
                if (balls.size() == 0 || nextBallSpawn <= 0)
                {
                    System.out.println("spawned a ball " + balls.size());
                    spawnBall(BallSpawns.values()[nextPosition++ % 4], ColorType.BLUE);
                    nextBallSpawn = Integer.MAX_VALUE;
                }
            }
        }
        else
        {
            System.out.println("10 reached");
        }
    }

    private void decideNextBallSpawn()
    {
        nextBallSpawn = rand.nextInt(3000) + 1000;
    }

    private void pylonUpdate(long deltaInMillis)
    {
        nextShiftChange = nextShiftChange - deltaInMillis;
        if (nextShiftChange <= 0)
        {
            shiftChange();
        }
    }

    private void shiftChange()
    {
        clearPreviousShift();
        createNextShift();
    }


    private void clearPreviousShift()
    {
        for (Pylon pylon : pylons)
        {
            pylon.destroy();
        }
        pylons.clear();
    }

    private void createNextShift()
    {
        int numberOfPylons = rand.nextInt(10) + 5;
        for (int i = 0; i < numberOfPylons; i++)
        {
            Pylon.generateRandomPylon(rand);
        }
        nextShiftChange = rand.nextInt(15000) + 10000;
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

    public static Vector2 getRandomPosition(Random rand)
    {
        float x = rand.nextFloat() * (GameWorld.DEFAULT_WORLD_WIDTH - 20f) + 10f;
        float y = rand.nextFloat() * (GameWorld.DEFAULT_WORLD_HEIGHT - 20f) + 10f;
        return new Vector2(x, y);
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

    enum BallState implements State<BoardManager>
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
                        //instance.spawnPylon();
                        //instance.spawnPylon();
                        instance.spawnBall(BallSpawns.TEST_SPAWN_1, ColorType.BLUE);
                        instance.spawnBall(BallSpawns.TEST_SPAWN_2, ColorType.RED);

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
                        if (instance.balls.size() < 10)
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

    private void spawnPylon()
    {
        Pylon.generateRandomPylon(rand);
    }

    enum BallSpawns
    {
        TEST_SPAWN_1(new Vector2(30f, 40f), new Vector2(-0.5f, -0.65f)),
        TEST_SPAWN_2(new Vector2(10f, 50f), new Vector2(0, -0.5f)),
        TEST_SPAWN_MIDLEFT(new Vector2(20f, 50f), new Vector2(0.5f, 0f)),
        TEST_SPAWN_MIDRIGHT(new Vector2(80f, 50f), new Vector2(-0.5f, 0f)),
        BOTTOM_LEFT(new Vector2(10, 10), new Vector2(1f, 1f)),
        BOTTOM_RIGHT(new Vector2(90, 10), new Vector2(-1, 1f)),
        TOP_RIGHT(new Vector2(90, 90), new Vector2(-1f, -1f)),
        TOP_LEFT(new Vector2(10, 90), new Vector2(1, -1));

        private final Vector2 position;
        private final Vector2 velocity;

        BallSpawns(Vector2 position, Vector2 velocity)
        {
            this.position = position;
            this.velocity = velocity;
        }

        public Vector2 getPosition()
        {
            return position.cpy();
        }

        public Vector2 getVelocity()
        {
            return velocity.cpy();
        }
    }
}
