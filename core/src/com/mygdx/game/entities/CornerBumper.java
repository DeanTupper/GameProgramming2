package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.renderables.CornerBumperRenderable;
import com.mygdx.game.components.movables.Movable;
import com.mygdx.game.components.collidables.TriangleCollidable;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;
import com.mygdx.game.subsystems.CollidableSubsystem;
import com.mygdx.game.subsystems.RenderSubsystem;
import com.mygdx.game.utils.shapes.Triangle;

public class CornerBumper extends Entity
{
    public static final float SIZE = 13f;

    private final CornerBumperRenderable renderable;
    private final TriangleCollidable triangleCollidable;

    public CornerBumper(Vector2 origin, Vector2 edge1, Vector2 edge3, Color color)
    {
        Triangle triangle = new Triangle(origin, edge1, edge3);

        renderable = new CornerBumperRenderable(triangle, color);
        RenderSubsystem.get().register(renderable);

        Movable triangleMovable = new Movable(triangle.origin, new Vector2());

        triangleCollidable = new TriangleCollidable(triangleMovable, triangle);
        CollidableSubsystem.get().register(triangleCollidable);

        BoardManager.get().register(this);
    }

    public Vector2 getPosition()
    {
        return triangleCollidable.getPosition();
    }

    @Override
    public void destroy()
    {
        RenderSubsystem.get().remove(renderable);
        CollidableSubsystem.get().remove(triangleCollidable);
        BoardManager.get().remove(this);
    }
}
