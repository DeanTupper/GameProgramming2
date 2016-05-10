package com.mygdx.game.utils.shapes;

import static com.badlogic.gdx.graphics.g2d.ParticleEmitter.SpawnShape.point;
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

    public boolean pointIsOnLine(Vector2 point)
    {
        float dxc = point.x - a.x;
        float dyc = point.y - a.y;

        float dx1 = b.x - a.x;
        float dy1 = b.y - a.y;

        float crossProduct = dxc * dy1 - dyc * dx1;
        System.err.println("LineSegment::pointIsOnLine - crossProduct: " + crossProduct);

        if (Math.abs(crossProduct) > 0.001)
        {
            return false;
        }

        if (Math.abs(dx1) >= Math.abs(dy1))
        {
            System.err.println("LineSegment::pointIsOnLine - dx1 >= dy1");
            if (dx1 > 0f)
            {
                return a.x <= point.x && point.x <= b.x;
            }
            else
            {
                return b.x <= point.x && point.x <= a.x;
            }
        }
        else
        {
            System.err.println("LineSegment::pointIsOnLine - dx1 < dy1");
            if (dy1 > 0f)
            {
                return a.y <= point.y && point.y <= b.y;
            }
            else
            {
                return b.y <= point.y && point.y <= a.y;
            }
        }
    }
}
