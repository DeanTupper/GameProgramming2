package com.mygdx.game.components.renderables;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.ColorType;

public class BallRenderable extends Renderable
{

    private final float radius;
    private final ColorType colorType;

    public BallRenderable(Vector2 position, ColorType colorType, float radius)
    {
        super(position);

        this.radius = radius;
        this.colorType = colorType;
    }

    @Override
    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(this.getColorType().getColor());
        renderer.circle(this.getPosition().x, this.getPosition().y, radius, 15);
    }

    public float getRadius()
    {
        return radius;
    }

    public ColorType getColorType()
    {
        return colorType;
    }
}
