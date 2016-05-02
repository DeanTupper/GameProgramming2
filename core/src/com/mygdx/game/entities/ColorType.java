package com.mygdx.game.entities;


import com.badlogic.gdx.graphics.Color;

import java.util.Random;

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

    public static int size()
    {
       return ColorType.values().length;
    }
    public static ColorType getRandomColorType(Random rand)
    {
        return ColorType.values()[rand.nextInt(ColorType.size())];
    }

}
