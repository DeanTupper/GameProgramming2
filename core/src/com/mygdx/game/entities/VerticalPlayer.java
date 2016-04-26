package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Movable;
import com.mygdx.game.components.PlayerRenderable;
import com.mygdx.game.components.VerticalPlayerMovable;
import com.mygdx.game.subsystems.MovableSubsystem;
import com.mygdx.game.subsystems.RenderSubsystem;

public class VerticalPlayer extends Entity
{
    private final Movable movable;
    private final PlayerRenderable renderable;

    public VerticalPlayer(Vector2 position, Vector2 velocity, ColorType colorType, float width, float height, float topBound, float bottomBound, int upKey, int downKey, Color color)
    {
        movable = new VerticalPlayerMovable(position, velocity,topBound,bottomBound,upKey,downKey);
        MovableSubsystem.get().register(movable);

        renderable = new PlayerRenderable(position,colorType,width,height, color);
        RenderSubsystem.get().register(renderable);
    }
    @Override
    public void destroy()
    {

    }
}