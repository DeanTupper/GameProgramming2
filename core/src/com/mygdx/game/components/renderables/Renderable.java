package com.mygdx.game.components.renderables;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Component;
import com.mygdx.game.entities.ColorType;
import com.mygdx.game.subsystems.RenderSubsystem;

public abstract class Renderable implements Component
{
    protected final Vector2 position;

    public Renderable(Vector2 position)
    {
        this.position = position;
    }

    public Renderable()
    {
        position = null;
    }

    public abstract void render(ShapeRenderer renderer);

    public Vector2 getPosition()
    {
        return position;
    }
}
