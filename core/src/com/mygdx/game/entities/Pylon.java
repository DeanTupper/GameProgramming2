package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Movable;
import com.mygdx.game.components.PushPullInfluencer;
import com.mygdx.game.components.PylonRenderable;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;
import com.mygdx.game.subsystems.PylonSubSystem;
import com.mygdx.game.subsystems.RenderSubsystem;

public class Pylon extends Entity
{
    private final Vector2 position;
    private final ColorType type;
    private final PylonRenderable renderable;


    private final PushPullInfluencer influencer;
    private final Movable movable;

    public Pylon(Vector2 position, ColorType type)
    {
        this.position = position;
        this.type = type;

        BoardManager.get().registerPylon(this);

        PylonSubSystem.get().registerPylon(this);

        movable = new Movable(position, new Vector2(0, 0));

        renderable = new PylonRenderable(position, type, 1, 10);
        RenderSubsystem.get().register(renderable);

        influencer = new PushPullInfluencer(position, type);
    }

    @Override
    public void destroy()
    {
        BoardManager.get().removePylon(this);
    }

    public PushPullInfluencer getInfluencer()
    {
        return influencer;
    }

    public Vector2 getPosition()
    {
        return movable.getPosition();
    }

    public float getRadius()
    {
        return renderable.getRadius();
    }
}