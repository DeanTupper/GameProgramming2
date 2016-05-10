package com.mygdx.game.components.movables;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Component;
import com.mygdx.game.subsystems.MovableSubsystem;

public class Movable implements Component
{
    protected Vector2 position;
    protected Vector2 velocity;

    public Movable(Vector2 position, Vector2 velocity)
    {
        this.position = position;
        this.velocity = velocity;
    }

    public Movable()
    {
        position = new Vector2();
        velocity = new Vector2();
    }

    public void move(float worldTimeStep)
    {
        MovableSubsystem.move(this, worldTimeStep);
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void setPosition(Vector2 position)
    {
        this.position.set(position);
    }

    public Vector2 getVelocity()
    {
        return velocity;
    }

    @Override
    public String toString()
    {
        return "Movable at " + position + " with velocity " + velocity;
    }
}
