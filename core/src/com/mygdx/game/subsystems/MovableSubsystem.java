package com.mygdx.game.subsystems;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.movables.Movable;
import com.mygdx.game.utils.UpdateDelta;

import java.util.HashSet;
import java.util.Set;

public class MovableSubsystem implements Subsystem
{
    private static MovableSubsystem instance;

    public static MovableSubsystem get()
    {
        if (instance == null)
        {
            instance = new MovableSubsystem();
        }

        return instance;
    }

    private MovableSubsystem()
    {
    }

    private final Set<Movable> movables = new HashSet<Movable>();

    @Override
    public void update(long deltaInMillis, UpdateDelta updateDelta)
    {
        for (Movable current : movables)
        {
            current.move(deltaInMillis);
        }
    }

    public void moveEntities(float worldTimeStep)
    {
        for (Movable current : new HashSet<Movable>(movables))
        {
            current.move(worldTimeStep);
        }
    }

    public void remove(Movable movable)
    {
        movables.remove(movable);
    }

    public void register(Movable movable)
    {
        movables.add(movable);
    }

    public static void move(Movable movable, float worldTimeStep)
    {
        Vector2 position = movable.getPosition();
        Vector2 velocity = movable.getVelocity();

        position.x += velocity.x * worldTimeStep;
        position.y += velocity.y * worldTimeStep;
    }
}
