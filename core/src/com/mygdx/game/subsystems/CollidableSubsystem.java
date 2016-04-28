package com.mygdx.game.subsystems;

import com.mygdx.game.components.Collidable;
import com.mygdx.game.utils.Quad;

import java.util.HashSet;
import java.util.Set;

public class CollidableSubsystem implements Subsystem
{
    private final Set<Collidable> collidables = new HashSet<Collidable>();

    private static CollidableSubsystem instance;

    private final QuadSubsystem quadSubsystem;

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
    }

    @Override
    public void update(long deltaInMillis)
    {
        for (Collidable collidable : collidables)
        {
            checkForPotentialCollisions(collidable);
        }
    }

    private void checkForPotentialCollisions(Collidable collidable)
    {
        Quad collidableQuad = quadSubsystem.getQuad(collidable.getPosition());

        Set<Collidable> potentialCollidables = collidableQuad.getCollidableEntitiesInRegion();

        for (Collidable potentialCollidable : potentialCollidables)
        {
            if (!potentialCollidable.equals(collidable))
            {
                if (potentialCollidable.collidesWith(collidable))
                {
                    potentialCollidable.resolveCollision(collidable);
                    collidable.resolveCollision(potentialCollidable);
                }
            }
        }
    }

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
