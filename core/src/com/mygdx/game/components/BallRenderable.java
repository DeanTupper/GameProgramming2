package com.mygdx.game.components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.ColorType;


public class BallRenderable extends Renderable
{
    public BallRenderable(Vector2 position, ColorType color)
    {
        super(position, color);
    }

    @Override
    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(this.getColorType().getColor());
        renderer.circle(this.getPosition().x ,this.getPosition().y ,5);
    }
}
