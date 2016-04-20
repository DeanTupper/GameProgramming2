package com.mygdx.game.components;

import com.badlogic.gdx.math.Vector2;

public class Movable implements Component
{
    private final Vector2 position;
    private final Vector2 velocity;

    public Movable(Vector2 position, Vector2 velocity)
    {
        this.position = position;
        this.velocity = velocity;
    }

    public void move()
    {
    }
}
