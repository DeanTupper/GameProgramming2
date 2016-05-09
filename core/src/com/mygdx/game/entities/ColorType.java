package com.mygdx.game.entities;


import com.badlogic.gdx.graphics.Color;

import java.util.Random;

public enum ColorType
{
    RED(new Color(0.7333f, 0.0625f, 0.3125f, 1.0f)),
    BLUE(new Color(0.2f, 0.332f, 0.73f, 1.0f)),
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
