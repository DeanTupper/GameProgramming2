package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.CornerBumperRenderable;
import com.mygdx.game.subsystems.RenderSubsystem;

public class CornerBumper extends Entity
{
    private final CornerBumperRenderable renderable;

    public CornerBumper(Vector2 point1, Vector2 point2, Vector2 point3, Color color)
    {
        renderable = new CornerBumperRenderable(point1, point2, point3, color);
        RenderSubsystem.get().register(renderable);
    }

    @Override
    public void destroy()
    {

    }
}
