package com.mygdx.game.entities;

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

    public HorizontalPlayer(Vector2 position, Vector2 velocity, ColorType colorType, float width, float height, float leftBound, float rightBound,int leftKey, int rightKey)
    {
        movable = new HorizontalPlayerMovable(position, velocity,leftBound,rightBound, leftKey, rightKey);
        MovableSubsystem.get().register(movable);

        renderable = new PlayerRenderable(position,colorType,width,height);
        RenderSubsystem.get().register(renderable);
    }
    @Override
    public void destroy()
    {

    }
}