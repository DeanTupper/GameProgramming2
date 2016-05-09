package com.mygdx.game.utils.shapes;

import com.badlogic.gdx.math.Vector2;

public class Line
{
    private final float slope;
    private final float yIntercept;

    public Line()
    {
        slope = Float.NaN;
        yIntercept = Float.NaN;
    }

    public Line(float slope, Vector2 point)
    {
        this(slope, point.x, point.y);
    }

    public Line(float slope, float x, float y)
    {
        this.slope = slope;
        yIntercept = y - (slope * x);
    }

    public Line(Vector2 pointA, Vector2 pointB)
    {
        Vector2 aToB = pointB.cpy().sub(pointA.cpy());
        slope = aToB.y / aToB.x;
        yIntercept = pointA.y - (slope * pointA.x);
    }

    public float slope()
    {
        return slope;
    }

    public float yIntercept()
    {
        return yIntercept;
    }

    public static Line getLineFromPointAndVelocity(Vector2 origin, Vector2 velocity)
    {
        Vector2 velNor = velocity.cpy().nor();
        System.err.println("Line::getLineFromPointAndVelocity - origin:[" + origin + "], velocity:[" + velocity + "]; velNor: " + velNor);
        float slope;

        // Vertical Line - has to be handled separately
        if (velNor.x == 0f)
        {
            return new VerticalLine(origin.x);
        }

        // Horizontal Line
        if (velNor.y == 0f)
        {
            slope = 0;
        }
        else
        {
            slope = velNor.y / velNor.x;
        }

        return new Line(slope, origin);
    }

    public Vector2 findIntersectionPointWith(Line other)
    {
        System.err.println("Line::findIntersectionPointWith - other:[" + other + "]; this: " + this);
        if (this.slope == other.slope)
        {
            if (this.yIntercept != other.yIntercept)
            {
                return null;
            }
            else
            {
                System.err.println("Line::findIntersectionPointWith - this.m != other.m && this.b == other.b; " +
                        "this.m: " + slope + ", other.m: " + other.slope + ", this.b: " + yIntercept + ", other.b: " + other.yIntercept);
            }
        }

        float x = (other.yIntercept - this.yIntercept) / (this.slope - other.slope);
        float y = slope * x + yIntercept;
        return new Vector2(x, y);
    }

    public Vector2 findIntersectionPointWith(VerticalLine other)
    {
        return other.findIntersectionPointWith(this);
    }

    public String toString()
    {
        return "y=" + slope + "x + " + yIntercept;
    }
}
