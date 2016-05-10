package com.mygdx.game.subsystems.BoardManagerSubSystem;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameWorld;
import com.mygdx.game.entities.*;
import com.mygdx.game.subsystems.Subsystem;
import com.mygdx.game.utils.UpdateDelta;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


public class BoardManager implements Subsystem
{
    private static final float BALL_RADIUS = 1f;
    private static BoardManager instance;

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final Random rand = new Random();

    private long nextBallSpawn = Long.MAX_VALUE;
    private int nextPosition = 0;
    private long nextShiftChange = 0;

    private GameWorld gameWorld;

    private final Set<Ball> balls = new HashSet<Ball>();
    private final Set<Pylon> pylons = new HashSet<Pylon>();
    private final Set<CornerBumper> cornerBumpers = new HashSet<CornerBumper>();

    private final boolean spawnBalls = true;
    private final boolean spawnPylons = true;

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
                    spawnBall(BallSpawns.values()[nextPosition++ % 4]);
                    nextBallSpawn = Long.MAX_VALUE;
                }
            }
        }
    }

    private void decideNextBallSpawn()
    {
        nextBallSpawn = rand.nextInt(3) + 1;
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
        new Ball(ballSpawns.getPosition(), randomizeVelocity(ballSpawns.getVelocity()), ColorType.getRandomColorType(rand), BALL_RADIUS);
    }

    private Vector2 randomizeVelocity(Vector2 velocity)
    {

        if (rand.nextBoolean())
        {
            velocity.x = velocity.x + rand.nextFloat();
        }
        else
        {
            velocity.x = velocity.x - rand.nextFloat();
        }
        if (rand.nextBoolean())
        {
            velocity.y = velocity.y + rand.nextFloat();
        }
        else
        {
            velocity.y = velocity.y - rand.nextFloat();
        }
        return velocity;
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

    public void spawnPylon()
    {
        Pylon.generateRandomPylon(rand);
    }

    public void setGameWorld(GameWorld gameWorld)
    {
        this.gameWorld = gameWorld;
    }

    public GameWorld getGameWorld()
    {
        return gameWorld;
    }

    enum BallSpawns
    {
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
