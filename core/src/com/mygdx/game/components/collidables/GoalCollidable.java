package com.mygdx.game.components.collidables;

import com.mygdx.game.entities.OverallPlayer;
import com.mygdx.game.utils.Direction;
import com.mygdx.game.utils.Quad;
import com.mygdx.game.utils.shapes.Rectangle;

import java.util.Set;

public class GoalCollidable extends RectangleCollidable
{
    private final OverallPlayer player;

    public GoalCollidable(Rectangle bounds, Direction collidableEdgeDir, OverallPlayer player, Set<Quad> rectQuads)
    {
        super(bounds, collidableEdgeDir, rectQuads);

        this.player = player;
    }

    public Rectangle getPlayerPaddleBounds()
    {
        return player.getPaddleBounds();
    }

    @Override
    public String toString()
    {
        return "GOAL COLLIDABLE: " + super.toString();
    }

    public void shouldCheckForCollisions(boolean check)
    {
        shouldCheckForCollisions = check;
    }
}
