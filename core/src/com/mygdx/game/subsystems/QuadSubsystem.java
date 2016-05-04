package com.mygdx.game.subsystems;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.collidables.Collidable;
import com.mygdx.game.utils.Quad;
import com.mygdx.game.utils.QuadMap;
import com.mygdx.game.utils.UpdateDelta;

import java.util.Set;

public class QuadSubsystem implements Subsystem
{
    private static QuadSubsystem instance;
    private final QuadMap quadmap;

    private QuadSubsystem()
    {
        quadmap = new QuadMap();
    }

    public static QuadSubsystem get()
    {
        if (instance == null)
        {
            instance = new QuadSubsystem();
        }

        return instance;
    }

    public Quad getQuad(Vector2 position)
    {
        return quadmap.getQuad(position);
    }

    @Override
    public void update(long deltaInMillis, UpdateDelta updateDelta)
    {
        Set<Collidable> collidablesInWorld = CollidableSubsystem.get().getCollidables();
        quadmap.update(collidablesInWorld);
    }

    public void render(ShapeRenderer renderer)
    {
        quadmap.render(renderer);
    }
}
