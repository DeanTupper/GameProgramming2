package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.utils.shapes.Triangle;

public class CornerBumperRenderable extends Renderable
{
    private final Color color;
    private final Triangle triangle;

    public CornerBumperRenderable(Triangle triangle, Color color)
    {
        this.triangle = triangle;
        this.color = color;
    }

    @Override
    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(color);
        renderer.triangle(triangle.origin.x, triangle.origin.y,
                triangle.edge1.x, triangle.edge1.y,
                triangle.edge2.x, triangle.edge2.y);

        renderer.setColor(Color.RED);
        renderer.point(triangle.origin.x, triangle.origin.y, 0f);
    }
}
