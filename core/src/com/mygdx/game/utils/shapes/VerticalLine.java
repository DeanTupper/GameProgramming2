package com.mygdx.game.utils.shapes;

import com.badlogic.gdx.math.Vector2;

public class VerticalLine extends Line
{
    private final float x;

    public VerticalLine(float x)
    {
        this.x = x;
    }

    @Override
    public Vector2 findIntersectionPointWith(Line other)
    {
        if (other instanceof VerticalLine)
        {
            System.err.println("VerticalLine::findIntersectionPointWith - finding intersection of two vertical lines with VerticalLine to Line");
            return findIntersectionPointWith((VerticalLine) other);
        }

        return new Vector2(x, (other.slope() * x) + other.yIntercept());
    }

    @Override
    public Vector2 findIntersectionPointWith(VerticalLine other)
    {
        if (other.x == this.x)
        {
            return new Vector2(x, Float.POSITIVE_INFINITY);
        }
        else
        {
            return null;
        }
    }
}
