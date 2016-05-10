package com.mygdx.game.subsystems;

import com.mygdx.game.GameWorld;
import com.mygdx.game.components.collidables.BallCollidable;
import com.mygdx.game.components.collidables.CircleCollidable;
import com.mygdx.game.components.collidables.Collidable;
import com.mygdx.game.components.collidables.RectangleCollidable;
import com.mygdx.game.components.collidables.TriangleCollidable;
import com.mygdx.game.entities.Ball;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;
import com.mygdx.game.utils.Quad;
import com.mygdx.game.utils.UpdateDelta;
import com.mygdx.game.utils.collision.CircleCircleCollision;
import com.mygdx.game.utils.collision.CircleRectangleCollision;
import com.mygdx.game.utils.collision.CircleTriangleCollision;
import com.mygdx.game.utils.collision.Collision;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class CollidableSubsystem implements Subsystem
{
    public static final float EPSILON_BEFORE_COLLISION = 0.005f;
    public static final float THRESHOLD_IMMINENT_COLLISION = 1.0f;
    private static CollidableSubsystem instance;

    private GameWorld gameWorld;

    private final QuadSubsystem quadSubsystem;
    private final BoardManager boardManager;
    private final MovableSubsystem movableSubsystem;
    private final RenderSubsystem renderSubsystem;

    private final Set<Collidable> collidables = new HashSet<Collidable>();
    private final Set<RectangleCollidable> rectangleCollidables = new HashSet<RectangleCollidable>();
    private final Map<Ball, Map<Collidable, Collision>> ballCollisionsMap = new HashMap<Ball, Map<Collidable, Collision>>();
    private final PriorityQueue<Collision> imminentCollisions = new PriorityQueue<Collision>();
    private final Set<Collision> ballOnBallCollisionsChecked = new HashSet<Collision>();

    private float totalWorldTime = 0f;

    public static CollidableSubsystem get()
    {
        if (instance == null)
        {
            instance = new CollidableSubsystem();
        }

        return instance;
    }

    private CollidableSubsystem()
    {
        quadSubsystem = QuadSubsystem.get();
        boardManager = BoardManager.get();

        movableSubsystem = MovableSubsystem.get();
        renderSubsystem = RenderSubsystem.get();
    }

    @Override
    public void update(long deltaInMillis, UpdateDelta updateDelta)
    {
        float worldTimeStep = (float) deltaInMillis / updateDelta.threshold;
        totalWorldTime += worldTimeStep;

        while (worldTimeStep > 0f)
        {
            imminentCollisions.clear();
            boolean collisionToResolve = false;

            doCollisionCheckPass(worldTimeStep);

            Collision nextCollision = imminentCollisions.poll();
            float discreteTimeStep = worldTimeStep;

            if (nextCollision != null && nextCollision.timeToCollision < worldTimeStep)
            {
                discreteTimeStep = nextCollision.timeToCollision - EPSILON_BEFORE_COLLISION;
                collisionToResolve = true;
            }

            updateCollisions(discreteTimeStep);
            movableSubsystem.moveEntities(discreteTimeStep);

            if (collisionToResolve)
            {
                System.err.println("CollidableSubsystem::update - resolving collision");
                nextCollision.resolve();
            }

            worldTimeStep -= discreteTimeStep;

            RenderSubsystem.get().renderWorld();
        }
    }

    private void updateCollisions(float discreteTimeStep)
    {
        ballOnBallCollisionsChecked.clear();

        Set<Ball> balls = boardManager.getBalls();

        for (Ball ball : balls)
        {
            Map<Collidable, Collision> ballCollisionMap = ballCollisionsMap.get(ball);

            for (Map.Entry<Collidable, Collision> entry : ballCollisionMap.entrySet())
            {
                Collision collision = entry.getValue();

                if (entry.getKey() instanceof BallCollidable)
                {
                    if (!ballOnBallCollisionsChecked.contains(collision))
                    {
                        collision.update(discreteTimeStep);
                        ballOnBallCollisionsChecked.add(collision);
                    }
                }
                else
                {
                    collision.update(discreteTimeStep);
                }
            }
        }
    }

    private void doCollisionCheckPass(float worldTimeStep)
    {
        Set<Ball> balls = boardManager.getBalls();

        for (Ball ball : balls)
        {
            checkBallForCollisions(ball, worldTimeStep);
        }
    }

    private void checkBallForCollisions(Ball ball, float worldTimeStep)
    {
        Quad ballQuad = quadSubsystem.getQuad(ball.getPosition());
        BallCollidable ballCollidable = ball.getCollidable();

        Set<Collidable> potentialCollidables = ballQuad.getCollidableEntitiesInRegion();
        potentialCollidables.remove(ballCollidable);

        Map<Collidable, Collision> collisionsMap = ballCollisionsMap.get(ball);

        if (collisionsMap == null)
        {
            collisionsMap = new HashMap<Collidable, Collision>();
            ballCollisionsMap.put(ball, collisionsMap);
        }

        float threshold = worldTimeStep > THRESHOLD_IMMINENT_COLLISION ? worldTimeStep : THRESHOLD_IMMINENT_COLLISION;

        for (Collidable potentialCollidable : potentialCollidables)
        {
            System.err.println("CollidableSubsystem::checkBallForCollisions - potentialCollidable: " + potentialCollidable);
            checkBallAndCollidable(ball, potentialCollidable, collisionsMap, worldTimeStep, threshold);
        }

        for (RectangleCollidable rect : rectangleCollidables)
        {
            if (rect.isCollidableInRectangle(ball.getCollidable()))
            {
                Collision collision = collisionsMap.get(rect);

                if (collision == null || !collision.isEasyToUpdate())
                {
                    collision = new CircleRectangleCollision(ball.getCollidable(), rect);

                    collision.calculateTimeToCollision();
                    collisionsMap.put(rect, collision);
                }

                if (collision.willCollide && collision.timeToCollision < threshold)
                {
                    imminentCollisions.add(collision);
                }
            }
        }
    }

    private void checkBallAndCollidable(Ball ball, Collidable potentialCollidable, Map<Collidable, Collision> collisionsMap, float worldTimeStep, float threshold)
    {
        boolean ballBallCollision = potentialCollidable instanceof BallCollidable;

        Collision collision = collisionsMap.get(potentialCollidable);

        if (collision == null || !collision.isEasyToUpdate())
        {
            if (ballBallCollision)
            {
                BallCollidable other = ((BallCollidable) potentialCollidable);
                collision = new CircleCircleCollision(ball.getCollidable(), other);

                Ball otherBall = other.getBall();
                Map<Collidable, Collision> otherBallCollisionsMap = ballCollisionsMap.get(otherBall);

                if (otherBallCollisionsMap == null)
                {
                    otherBallCollisionsMap = new HashMap<Collidable, Collision>();
                    ballCollisionsMap.put(otherBall, otherBallCollisionsMap);
                }


                // Don't want balls to maintain separate collisions for each other
                if (!otherBallCollisionsMap.containsKey(ball.getCollidable()))
                {
                    otherBallCollisionsMap.put(ball.getCollidable(), collision);
                }

            }
            else if (potentialCollidable instanceof CircleCollidable)
            {
                CircleCollidable other = ((CircleCollidable) potentialCollidable);
                collision = new CircleCircleCollision(ball.getCollidable(), other);
            }
            else if (potentialCollidable instanceof TriangleCollidable)
            {
                TriangleCollidable other = ((TriangleCollidable) potentialCollidable);
                collision = new CircleTriangleCollision(ball.getCollidable(), other);
            }
            else if (potentialCollidable instanceof RectangleCollidable)
            {
                RectangleCollidable other = ((RectangleCollidable) potentialCollidable);
                collision = new CircleRectangleCollision(ball.getCollidable(), other);
            }
            else
            {
                throw new AssertionError("Unknown collidable type " + potentialCollidable);
            }

            collision.calculateTimeToCollision();
            collisionsMap.put(potentialCollidable, collision);
        }

        if (collision.willCollide && collision.timeToCollision < threshold)
        {
            imminentCollisions.add(collision);
        }
    }

    public void setGameWorld(GameWorld gameWorld)
    {
        this.gameWorld = gameWorld;
    }

    public void register(Collidable collidable)
    {
        if (collidable instanceof RectangleCollidable)
        {
            RectangleCollidable rect = ((RectangleCollidable) collidable);
            rectangleCollidables.add(rect);
        }
        else
        {
            collidables.add(collidable);
        }
    }

    public void remove(Collidable collidable)
    {
        collidables.remove(collidable);
    }

    public Set<Collidable> getCollidables()
    {
        return collidables;
    }
}
