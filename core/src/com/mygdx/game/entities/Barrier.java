package com.mygdx.game.entities;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.BarrierRenderable;
import com.mygdx.game.components.collidables.RectangleCollidable;
import com.mygdx.game.subsystems.CollidableSubsystem;
import com.mygdx.game.subsystems.QuadSubsystem;
import com.mygdx.game.subsystems.RenderSubsystem;
import com.mygdx.game.utils.Direction;
import com.mygdx.game.utils.Quad;
import com.mygdx.game.utils.shapes.Rectangle;

import java.util.Set;

public class Barrier extends Entity
{
    private final Vector2 position;
    private final BarrierRenderable renderable;
    private final RectangleCollidable collidable;
    private final Rectangle rect;

    public Barrier(Vector2 position, float width, float height, Direction collidableEdgeDir)
    {
        this.position = position;
        this.renderable = new BarrierRenderable(position,width,height, Color.WHITE);

        RenderSubsystem.get().register(renderable);
        rect =new Rectangle(position.x, position.y, width, height);
        collidable = new RectangleCollidable(rect, collidableEdgeDir,getQuadSet());

        CollidableSubsystem.get().register(collidable);
    }

    @Override
    public void destroy()
    {
        RenderSubsystem.get().remove(renderable);
        CollidableSubsystem.get().remove(collidable);
    }

    public Set<Quad> getQuadSet()
    {
        return QuadSubsystem.get().getGoalQuads((int) (position.x / 10f), (int) (position.y / 10f), (int) ((rect.right + 10f) / 10f), (int) (rect.top / 10f));
    }
}
