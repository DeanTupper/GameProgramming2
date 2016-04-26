package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.HorizontalPlayerMovable;
import com.mygdx.game.components.Movable;
import com.mygdx.game.components.PlayerRenderable;
import com.mygdx.game.subsystems.MovableSubsystem;
import com.mygdx.game.subsystems.RenderSubsystem;

public class HorizontalPlayer extends Entity
{
    private final Movable movable;
    private final PlayerRenderable renderable;
    protected final Color color;

    public HorizontalPlayer(Vector2 position, Vector2 velocity, ColorType colorType, float width, float height, float leftBound, float rightBound,int leftKey, int rightKey, Color color)
    {
        movable = new HorizontalPlayerMovable(position, velocity,leftBound,rightBound, leftKey, rightKey);
        MovableSubsystem.get().register(movable);

        renderable = new PlayerRenderable(position,colorType,width,height, color);
        RenderSubsystem.get().register(renderable);

        this.color = color;
    }
    @Override
    public void destroy()
    {

    }
}