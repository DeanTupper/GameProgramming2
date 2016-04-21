package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.BallRenderable;
import com.mygdx.game.components.Movable;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;
import com.mygdx.game.subsystems.MovableSubsystem;
import com.mygdx.game.subsystems.RenderSubsystem;

public class Ball extends Entity
{
    private final Movable movable;
    private final BallRenderable renderable;

    public Ball(Vector2 position, Vector2 velocity, ColorType colorType, float radius)
    {
        BoardManager.get().registerBall(this);

        movable = new Movable(position, velocity);
        MovableSubsystem.get().register(movable);

        renderable = new BallRenderable(position,colorType,radius);
        RenderSubsystem.get().register(renderable);
    }

    @Override
    public void destroy()
    {
        MovableSubsystem.get().remove(movable);
        BoardManager.get().removeBall(this);
    }
}
