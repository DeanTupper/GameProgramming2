package com.mygdx.game.components.collidables;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.movables.Movable;
import com.mygdx.game.subsystems.QuadSubsystem;
import com.mygdx.game.utils.Direction;
import com.mygdx.game.utils.Quad;
import com.mygdx.game.utils.collision.Collision;
import com.mygdx.game.utils.shapes.LineSegment;
import com.mygdx.game.utils.shapes.Rectangle;

import java.util.Set;

public class RectangleCollidable extends Collidable
{
    private final Rectangle bounds;
    private final LineSegment collidableEdge;
    private final Direction collidableEdgeDir;
    private final Set<Quad> rectangleQuads;

    public RectangleCollidable(Rectangle bounds, Direction collidableEdgeDir, Set<Quad> rectangleQuads)
    {
        super(new Movable());

        this.collidableEdgeDir = collidableEdgeDir;
        this.bounds = bounds;
        this.rectangleQuads = rectangleQuads;

        switch (collidableEdgeDir)
        {
            case NORTH:
                collidableEdge = new LineSegment(new Vector2(bounds.x, bounds.top), new Vector2(bounds.right, bounds.top));
                break;
            case SOUTH:
                collidableEdge = new LineSegment(new Vector2(bounds.x, bounds.y), new Vector2(bounds.right, bounds.y));
                break;
            case EAST:
                collidableEdge = new LineSegment(new Vector2(bounds.right, bounds.y), new Vector2(bounds.right, bounds.top));
                break;
            case WEST:
                collidableEdge = new LineSegment(new Vector2(bounds.x, bounds.y), new Vector2(bounds.x, bounds.top));
                break;
            default:
                throw new AssertionError("WHY ARE YOU PASSING ILLEGAL DIRECTIONS");
        }
    }

    @Override
    public void resolveCollision(Collidable other, Collision collision)
    {

    }

    public boolean isCollidableInRectangle(Collidable collidable)
    {
        return rectangleQuads.contains(QuadSubsystem.get().getQuad(collidable.getPosition()));
    }

    @Override
    public String toString()
    {
        return "RectangleCollidable " + super.toString();
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public LineSegment getCollidableEdge()
    {
        return collidableEdge;
    }

    public Direction getCollidableEdgeDir()
    {
        return collidableEdgeDir;
    }
}
