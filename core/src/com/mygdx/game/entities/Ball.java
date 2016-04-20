package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Movable;
import com.mygdx.game.subsystems.MovableSubsystem;

public class Ball extends Entity
{
    private final Movable movable;

    public Ball(Vector2 position, Vector2 velocity)
    {
        movable = new Movable(position, velocity);
        MovableSubsystem.get().register(movable);
    }

    @Override
    public void destroy()
    {

    }
}
