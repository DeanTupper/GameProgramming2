package com.mygdx.game.entities;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.BarrierRenderable;
import com.mygdx.game.subsystems.RenderSubsystem;

public class Barrier extends Entity
{

    private final Vector2 position;
    private final BarrierRenderable renderable;

    public Barrier(Vector2 position, float width, float height)
    {
        this.position = position;
        this.renderable = new BarrierRenderable(position,width,height, Color.WHITE);

        RenderSubsystem.get().register(renderable);
    }
    @Override
    public void destroy()
    {

    }
}
