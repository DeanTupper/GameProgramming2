package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Movable;
import com.mygdx.game.components.PushPullInfluencer;
import com.mygdx.game.components.PylonRenderable;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;
import com.mygdx.game.subsystems.PylonSubSystem;
import com.mygdx.game.subsystems.RenderSubsystem;

import java.util.Random;

public class Pylon extends Entity
{
    private static final Vector2 NONMOVING = new Vector2(0, 0);
    private static final int PYLON_SIZE = 1;
    private final Vector2 position;
    private final ColorType type;
    private final PylonRenderable renderable;


    private final PushPullInfluencer influencer;
    private final Movable movable;

    public Pylon(Vector2 position, ColorType type, float effectRadius)
    {
        this.position = position;
        this.type = type;

        BoardManager.get().registerPylon(this);

        PylonSubSystem.get().registerPylon(this);

        movable = new Movable(position,NONMOVING);

        renderable = new PylonRenderable(position, type, PYLON_SIZE, effectRadius);
        RenderSubsystem.get().register(renderable);

        influencer = new PushPullInfluencer(position, type);
    }

    @Override
    public void destroy()
    {
        RenderSubsystem.get().remove(renderable);
        PylonSubSystem.get().removePylon(this);
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

    public static void generateRandomPylon(Random rand)
    {
        Vector2 position = BoardManager.getRandomPosition(rand);
        ColorType type = ColorType.getRandomColorType(rand);
        float effectRadius = rand.nextFloat()*10;
        new Pylon(position,type,effectRadius);
    }
}