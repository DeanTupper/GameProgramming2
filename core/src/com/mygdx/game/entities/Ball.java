package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.movables.BallMovable;
import com.mygdx.game.subsystems.*;
import com.mygdx.game.components.collidables.BallCollidable;
import com.mygdx.game.components.renderables.BallRenderable;
import com.mygdx.game.components.movables.Movable;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;

public class Ball extends Entity
{
    private final Movable movable;
    private final BallRenderable renderable;
    private final BallCollidable collidable;

    private final ColorType type;

    public Ball(Vector2 position, Vector2 velocity, ColorType colorType, float radius)
    {
        this.type = colorType;
        BoardManager.get().register(this);
        PylonSubSystem.get().registerBall(this);

        movable = new BallMovable(position, velocity,this);
        MovableSubsystem.get().register(movable);

        renderable = new BallRenderable(position, colorType, radius);
        RenderSubsystem.get().register(renderable);

        collidable = new BallCollidable(this, movable, radius);
        CollidableSubsystem.get().register(collidable);
    }

    @Override
    public void destroy()
    {
        MovableSubsystem.get().remove(movable);
        BoardManager.get().removeBall(this);
        RenderSubsystem.get().remove(renderable);
        CollidableSubsystem.get().remove(collidable);
        PylonSubSystem.get().removeBall(this);
    }

    public Vector2 getPosition()
    {
        return movable.getPosition();
    }

    public Vector2 getVelocity()
    {
        return movable.getVelocity();
    }

    public ColorType getType()
    {
        return type;
    }

    public float getRadius()
    {
        return renderable.getRadius();
    }

    public BallCollidable getCollidable()
    {
        return collidable;
    }

    @Override
    public String toString()
    {
        return "Ball at " + movable.getPosition() + " with velocity " + movable.getVelocity() + " with color " + type.name();
    }
}
