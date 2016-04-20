package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Movable;
import com.mygdx.game.subsystems.MovableSubsystem;

public class Ball2 extends Entity2
{
    public Ball2(Vector2 position, Vector2 velocity)
    {
        Movable movable = new Movable(position, velocity);
        components.add(movable);
        MovableSubsystem.get().register(movable);
    }
}
