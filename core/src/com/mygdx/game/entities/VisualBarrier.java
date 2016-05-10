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

public class VisualBarrier extends Entity
{
    private final Vector2 position;
    private final BarrierRenderable renderable;
    private final Rectangle rect;

    public VisualBarrier(Vector2 position, float width, float height)
    {
        this.position = position;
        this.renderable = new BarrierRenderable(position,width,height, Color.WHITE);

        RenderSubsystem.get().register(renderable);
        rect =new Rectangle(position.x, position.y, width, height);
    }

    @Override
    public void destroy()
    {
        RenderSubsystem.get().remove(renderable);
    }
}
