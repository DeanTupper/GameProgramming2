package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;

public class Pylon extends Entity
{
    private final Vector2 position;
    private final ColorType type;

    public Pylon(Vector2 position, ColorType type)
    {
        this.position = position;
        this.type = type;

        BoardManager.get().registerPylon(this);
    }

    @Override
    public void destroy()
    {
        BoardManager.get().removePylon(this);
    }
}