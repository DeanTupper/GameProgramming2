package com.mygdx.game.utils.shapes;

import com.badlogic.gdx.math.Vector2;

public class LineSegment
{
    private final Vector2 a;
    private final Vector2 b;

    private final Vector2 bToA;
    private final Vector2 midPoint;

    public LineSegment(Vector2 a, Vector2 b)
    {
        this.a = a;
        this.b = b;

        bToA = a.cpy().sub(b);

        midPoint = new Vector2((a.x + b.x) / 2.0f, (a.y + b.y) / 2.0f);
    }

    public Vector2 pointA()
    {
        return a.cpy();
    }

    public Vector2 pointB()
    {
        return b.cpy();
    }

    public Vector2 bToA()
    {
        return bToA.cpy();
    }

    public Vector2 midPoint()
    {
        return midPoint.cpy();
    }
}
