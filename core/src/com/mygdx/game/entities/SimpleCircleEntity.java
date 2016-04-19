package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class SimpleCircleEntity
{
    private final float radius;
    private final Color color;

    private boolean drawBorder;

    SimpleCircleEntity(float radius, Color color, boolean drawBorder)
    {
        this.radius = radius;
        this.color = color;
        this.drawBorder = drawBorder;
    }

    SimpleCircleEntity(float radius, Color color)
    {
        this(radius, color, true);
    }

    void draw(ShapeRenderer renderer, Vector2 position)
    {
        if (drawBorder)
        {
            renderer.setColor(Color.BLACK);
            renderer.circle(position.x, position.y, radius + 0.5f, 20);
        }

        renderer.setColor(color);
        renderer.circle(position.x, position.y, radius, 20);
    }
}