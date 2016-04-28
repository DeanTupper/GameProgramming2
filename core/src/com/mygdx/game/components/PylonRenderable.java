package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.ColorType;


public class PylonRenderable extends Renderable
{

    private final float pylonRadius;
    private final float effectRadius;
    private final Color pylonColor;
    private final Color effectColor;
    private final ColorType colorType;

    public PylonRenderable(Vector2 position, ColorType colorType, float pylonRadius, float effectRadius)
    {
        super(position);
        this.pylonRadius = pylonRadius;
        this.effectRadius = effectRadius;
        this.colorType = colorType;
        this.pylonColor = colorType.getColor();
        this.effectColor = new Color(pylonColor.r,pylonColor.g,pylonColor.b,.15f);
    }

    @Override
    public void render(ShapeRenderer renderer)
    {
        renderer.setColor(pylonColor);
        renderer.circle(this.getPosition().x ,this.getPosition().y ,pylonRadius);

        renderer.setColor(effectColor);
        renderer.circle(this.getPosition().x ,this.getPosition().y ,effectRadius);
    }

    public float getRadius()
    {
        return effectRadius;
    }
}
