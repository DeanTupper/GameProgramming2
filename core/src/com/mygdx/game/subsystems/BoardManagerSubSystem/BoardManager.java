package com.mygdx.game.subsystems.BoardManagerSubSystem;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameWorld;
import com.mygdx.game.components.collidables.BallCollidable;
import com.mygdx.game.entities.*;
import com.mygdx.game.subsystems.Subsystem;
import com.mygdx.game.utils.UpdateDelta;
import com.mygdx.game.utils.collision.CircleCircleCollision;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


public class BoardManager implements Subsystem
{
    private static final float BALL_RADIUS = 1f;
    private static BoardManager instance;

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();
    private float lastUpdate = 0;
    private final Random rand = new Random();

    private long nextBallSpawn = Integer.MAX_VALUE;
    private int nextPosition = 0;
    private long nextShiftChange = 0;

    private final Set<Ball> balls = new HashSet<Ball>();
    private final Set<Pylon> pylons = new HashSet<Pylon>();
    private final Set<CornerBumper> cornerBumpers = new HashSet<CornerBumper>();

    private final boolean spawnBalls = true;
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
//        if (balls.size() < 4)
//        {
//            instance.spawnBall(BallSpawns.BOTTOM_LEFT);
//            instance.spawnBall(BallSpawns.TOP_RIGHT);
//
//            System.err.println("BoardManager::update - balls.size: " + balls.size());
//
//            Ball[] ballArr = new Ball[balls.size()];
//            balls.toArray(ballArr);
//            BallCollidable a = ballArr[0].getCollidable();
//            BallCollidable b = ballArr[1].getCollidable();
//
//            CircleCircleCollision test = new CircleCircleCollision(a, b);
//            test.calculateTimeToCollision();
//            System.err.println("BoardManager::update - a: " + ballArr[0] + "; b: " + ballArr[1]);
//
//            instance.spawnBall(BallSpawns.TEST_SPAWN_MIDLEFT);
//            instance.spawnBall(BallSpawns.TEST_SPAWN_MIDRIGHT);
//
//            System.err.println("*******************************************************************\n");
//        }

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
        if (balls.size() < 1)
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
                    spawnBall(BallSpawns.values()[nextPosition++ % 4]);
                    nextBallSpawn = Integer.MAX_VALUE;
                }
            }
        }
        else
        {
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

    private void spawnBall(BallSpawns ballSpawns)
    {
        new Ball(ballSpawns.getPosition(), ballSpawns.getVelocity(), ColorType.getRandomColorType(rand), BALL_RADIUS);
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

    public void spawnBall()
    {
        spawnBall(BallSpawns.values()[nextPosition++ % 4]);
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
                            instance.spawnBall(BallSpawns.values()[random.nextInt(BallSpawns.values().length)]);
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

    public void spawnPylon()
    {
        Pylon.generateRandomPylon(rand);
    }

    enum BallSpawns
    {
//        TEST_SPAWN_1(new Vector2(40f, 40f), new Vector2(0.5f, 0.5f)),
//        TEST_SPAWN_2(new Vector2(60f, 60f), new Vector2(-0.5f, -0.5f)),
//        TEST_SPAWN_MIDLEFT(new Vector2(20f, 50f), new Vector2(0.5f, 0f)),
//        TEST_SPAWN_MIDRIGHT(new Vector2(80f, 50f), new Vector2(-0.5f, 0f)),
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
