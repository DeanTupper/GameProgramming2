package com.mygdx.game.subsystems;

import com.mygdx.game.components.BallCollidable;
import com.mygdx.game.components.Collidable;
import com.mygdx.game.entities.Ball;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;
import com.mygdx.game.utils.Quad;
import com.mygdx.game.utils.collision.Collision;

import java.util.HashSet;
import java.util.Set;

public class CollidableSubsystem implements Subsystem
{
    private final Set<Collidable> collidables = new HashSet<Collidable>();

    private static CollidableSubsystem instance;

    private final QuadSubsystem quadSubsystem;
    private Set<BallCollidable> ballCollidables;
    private final BoardManager boardManager;

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
    }

    @Override
    public void update(long deltaInMillis)
    {
        Set<Ball> balls = boardManager.getBalls();

        for (Ball ball : balls)
        {
            checkBallForCollisions(ball, deltaInMillis);
        }
    }

    private void checkBallForCollisions(Ball ball, long deltaInMillis)
    {
        Quad ballQuad = quadSubsystem.getQuad(ball.getPosition());

        Set<Collidable> potentialCollidables = ballQuad.getCollidableEntitiesInRegion();
        potentialCollidables.remove(ball.getCollidable());

        BallCollidable ballCollidable = ball.getCollidable();

        for (Collidable potentialCollidable : potentialCollidables)
        {
            Collision potentialCollision = ballCollidable.checkForCollision(potentialCollidable, deltaInMillis);
            ballCollidable.updateCollisionIfSooner(potentialCollision);
        }
    }

    private void checkForPotentialCollisions(Collidable collidable, long deltaInMillis)
    {
        Quad collidableQuad = quadSubsystem.getQuad(collidable.getPosition());

        Set<Collidable> potentialCollidables = collidableQuad.getCollidableEntitiesInRegion();

        for (Collidable potentialCollidable : potentialCollidables)
        {
            if (!potentialCollidable.equals(collidable))
            {
                Collision potentialCollision = potentialCollidable.checkForCollision(collidable, deltaInMillis);

//                if (potentialCollidable.collidesWith(collidable, deltaInMillis))
//                {
//                    potentialCollidable.resolveCollision(collidable);
//                    collidable.resolveCollision(potentialCollidable);
//                }
            }
        }
    }

    public void registerBall(BallCollidable ball){ballCollidables.add(ball);}
    public void removeBall(Ball ball){ballCollidables.remove(ball);}
    public void register(Collidable collidable)
    {
        System.err.println("CollidableSubsystem.register - registering " + collidable);
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
