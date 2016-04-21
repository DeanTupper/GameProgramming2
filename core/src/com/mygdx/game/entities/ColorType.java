package com.mygdx.game.entities;


import com.badlogic.gdx.graphics.Color;

public enum ColorType
{
    RED(Color.RED),
    BLUE(Color.BLUE),
    GREEN(Color.GREEN);

    private final Color color;

    ColorType(Color color)
    {
        this.color = color;
    }

    public Color getColor()
    {
        return color;
    }
}
