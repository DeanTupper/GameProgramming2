package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;

public class Rectangle
{
    public final float width;
    public final float height;

    public final float right;
    public final float top;

    public final float x;
    public final float y;

    public final Vector2 centerPoint;

    public Rectangle(float x, float y, float width, float height)
    {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        right = x + width;
        top = y + height;

        centerPoint = new Vector2((x + width) / 2.0f, (y + height) / 2.0f);
    }

    @Override
    public String toString()
    {
        return "(" + x + "," + y + "),w:" + width + ",h:" + height;
    }
}
