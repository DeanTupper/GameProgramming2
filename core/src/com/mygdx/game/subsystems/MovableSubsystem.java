package com.mygdx.game.subsystems;

import com.mygdx.game.components.Movable;

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
    public void update(float deltaInMillis)
    {

    }

    public void remove(Movable movable)
    {
        movables.remove(movable);
    }

    public void register(Movable movable)
    {
        movables.add(movable);
    }

    public static void move(Movable movable)
    {
    }
}
