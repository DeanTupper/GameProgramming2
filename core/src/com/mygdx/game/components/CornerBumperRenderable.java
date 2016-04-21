package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class CornerBumperRenderable extends Renderable
{
    private final Vector2 point1;
    private final Vector2 point2;
    private final Vector2 point3;
    private final Color color;

    public CornerBumperRenderable(Vector2 point1, Vector2 point2, Vector2 point3, Color color)
    {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.color = color;
    }

    @Override
    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(color);
        renderer.triangle(point1.x, point1.y, point2.x, point2.y, point3.x, point3.y);
    }
}
