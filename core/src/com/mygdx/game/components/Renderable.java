package com.mygdx.game.components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.ColorType;
import com.mygdx.game.subsystems.RenderSubsystem;

public class Renderable extends java.awt.Component
{
    private final Vector2 position;
    private final ColorType colorType;

    public Renderable(Vector2 position, ColorType colorType)
    {
        this.position = position;
        this.colorType = colorType;
    }

    public Renderable()
    {
        this.position = null;
        this.colorType = null;
    }

    public void render(ShapeRenderer renderer)
    {
        RenderSubsystem.render(this);
    }

    public Vector2 getPosition()
    {
        return position;
    }


    public ColorType getColorType()
    {
        return colorType;
    }
}
