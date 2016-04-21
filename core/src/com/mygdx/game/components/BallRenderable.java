package com.mygdx.game.components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.ColorType;


public class BallRenderable extends Renderable
{
    private final float radius;

    public BallRenderable(Vector2 position, ColorType color, float radius)
    {
        super(position, color);
        this.radius = radius;
    }

    @Override
    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(this.getColorType().getColor());
        renderer.circle(this.getPosition().x ,this.getPosition().y ,radius);
    }
}
