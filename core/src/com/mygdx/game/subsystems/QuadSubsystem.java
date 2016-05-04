package com.mygdx.game.subsystems;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Collidable;
import com.mygdx.game.components.Movable;
import com.mygdx.game.utils.Quad;
import com.mygdx.game.utils.QuadMap;

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
    public void update(long deltaInMillis)
    {
        Set<Collidable> collidablesInWorld = CollidableSubsystem.get().getCollidables();
        quadmap.update(collidablesInWorld);
    }

    public void render(ShapeRenderer renderer)
    {
        quadmap.render(renderer);
    }
}
