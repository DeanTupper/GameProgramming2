package com.mygdx.game.subsystems;

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
    public static final float EPSILON_BEFORE_COLLISION = 0.01f;
    public static final float THRESHOLD_IMMINENT_COLLISION = 1.0f;
    private static CollidableSubsystem instance;

    private final QuadSubsystem quadSubsystem;

    private final BoardManager boardManager;
    private final MovableSubsystem movableSubsystem;

    private final Set<Collidable> collidables = new HashSet<Collidable>();
    private final Map<Ball, Map<Collidable, Collision>> ballCollisionsMap = new HashMap<Ball, Map<Collidable, Collision>>();
    private final PriorityQueue<Collision> imminentCollisions = new PriorityQueue<Collision>();

    private Set<BallCollidable> ballCollidables;

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
    }

    @Override
    public void update(long deltaInMillis, UpdateDelta updateDelta)
    {
        float worldTimeStep = (float) deltaInMillis / updateDelta.threshold;
        float discreteTimeStep = 0f;

        while (worldTimeStep > 0)
        {
            imminentCollisions.clear();

            boolean collisionToResolve = false;

            doCollisionCheckPass(discreteTimeStep);

            Collision nextCollision = imminentCollisions.poll();

            discreteTimeStep = worldTimeStep;

            if (nextCollision != null && nextCollision.timeToCollision < worldTimeStep)
            {
                discreteTimeStep -= (nextCollision.timeToCollision + EPSILON_BEFORE_COLLISION);
                collisionToResolve = true;
            }

            movableSubsystem.moveEntities(discreteTimeStep);

            if (collisionToResolve)
            {
                nextCollision.resolve();
            }

            worldTimeStep -= discreteTimeStep;
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

        for (Collidable potentialCollidable : potentialCollidables)
        {
            checkBallAndCollidable(ball, potentialCollidable, collisionsMap, worldTimeStep);
        }
    }

    private void checkBallAndCollidable(Ball ball, Collidable potentialCollidable, Map<Collidable, Collision> collisionsMap, float worldTimeStep)
    {
        Collision collision = collisionsMap.get(potentialCollidable);

        if (collision == null || !collision.shouldBeUpdated() || !collision.isEasyToUpdate())
        {
            if (potentialCollidable instanceof BallCollidable)
            {
                BallCollidable other = ((BallCollidable) potentialCollidable);
                collision = new CircleCircleCollision(ball.getCollidable(), other);

                Ball otherBall = other.getBall();
                Map<Collidable, Collision> otherBallCollisionsMap = ballCollisionsMap.get(ball);

                if (otherBallCollisionsMap.containsKey(ball.getCollidable()))
                {
                    System.err.println("CollidableSubsystem::checkBallAndCollidable - ball " + ball + " did not have collision for other ball " + otherBall + " or was not flagged for update. How?");
                }

                otherBallCollisionsMap.put(ball.getCollidable(), collision);
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
        }
        else if (worldTimeStep > 0f)
        {
            collision.update(worldTimeStep);
        }

        float threshold = worldTimeStep > THRESHOLD_IMMINENT_COLLISION ? worldTimeStep : THRESHOLD_IMMINENT_COLLISION;

        if (collision.willCollide && collision.timeToCollision < threshold) {
            imminentCollisions.add(collision);
        }
    }

//    private Collision getEmptyCollision(Ball ball, Collidable potentialCollidable)
//    {
//        Collision collision;
//
//        if (potentialCollidable instanceof BallCollidable)
//        {
//            BallCollidable other = ((BallCollidable) potentialCollidable);
//            collision = new CircleCircleCollision(ball.getCollidable(), other);
//        }
//        else if (potentialCollidable instanceof CircleCollidable)
//        {
//            CircleCollidable other = ((CircleCollidable) potentialCollidable);
//            collision = new CircleCircleCollision(ball.getCollidable(), other);
//        }
//        else if (potentialCollidable instanceof TriangleCollidable)
//        {
//            TriangleCollidable other = ((TriangleCollidable) potentialCollidable);
//            collision = new CircleTriangleCollision(ball.getCollidable(), other);
//        }
//        else if (potentialCollidable instanceof RectangleCollidable)
//        {
//            RectangleCollidable other = ((RectangleCollidable) potentialCollidable);
//            collision = new CircleRectangleCollision(ball.getCollidable(), other);
//        }
//        else
//        {
//            throw new AssertionError("Unknown collidable type " + potentialCollidable);
//        }
//
//        return collision;
//    }

    public void register(Collidable collidable)
    {
        collidables.add(collidable);
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
