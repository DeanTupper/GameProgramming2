package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Ball extends Entity
{
    private static final Color DEFAULT_COLOR = Color.RED;

    private final float RADIUS_CIRCLE = 3.0f;
    private final Color color;
    private final SimpleCircleEntity simpleCircleEntity;

    public Ball()
    {
        this(new Vector2(), new Vector2());
    }

    public Ball(Vector2 position, Vector2 velocity)
    {
        this(position, velocity, DEFAULT_COLOR);
    }

    public Ball(Vector2 position, Vector2 velocity, Color color)
    {
        this.position = position;
        this.velocity = velocity;
        this.color = color;

        this.simpleCircleEntity = new SimpleCircleEntity(RADIUS_CIRCLE, color, false);
    }

    @Override
    public void draw(ShapeRenderer renderer)
    {
        simpleCircleEntity.draw(renderer, position);
    }

    @Override
    public void update(long deltaInMillis)
    {
        position.add(velocity.x, velocity.y);
    }
}
