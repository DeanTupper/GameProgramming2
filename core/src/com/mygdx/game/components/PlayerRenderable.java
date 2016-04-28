package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class PlayerRenderable extends Renderable
{
    private final float width;
    private final float height;
    private final Color color;

    public PlayerRenderable(Vector2 position, float width, float height, Color color)
    {
        super(position);

        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(this.color);
        renderer.rect(position.x, position.y, width, height);
    }
}
