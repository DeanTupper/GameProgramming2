package com.mygdx.game.entities;

import com.mygdx.game.components.collidables.Collidable;
import com.mygdx.game.components.collidables.GoalCollidable;
import com.mygdx.game.components.collidables.RectangleCollidable;
import com.mygdx.game.subsystems.CollidableSubsystem;
import com.mygdx.game.subsystems.QuadSubsystem;
import com.mygdx.game.utils.Direction;
import com.mygdx.game.utils.Quad;
import com.mygdx.game.utils.shapes.Rectangle;

import java.util.Set;

public class Goal
{
    private final Set<Quad> goalQuads;
    private final GoalCollidable collidable;
    private final OverallPlayer player;

    public Goal(Set<Quad> goalQuads, Rectangle rectangle, Direction collidableEdgeDir, OverallPlayer player)
    {
        this.goalQuads = goalQuads;
        this.player = player;

        collidable = new GoalCollidable(rectangle, collidableEdgeDir, player, goalQuads);

        CollidableSubsystem.get().register(collidable);
    }

    public boolean isCollidableInGoal(Collidable collidable)
    {
        Quad quad = QuadSubsystem.get().getQuad(collidable.getPosition());

        return goalQuads.contains(quad);
    }

    public RectangleCollidable getCollidable()
    {
        return collidable;
    }

    public void shouldCheckForCollisions(boolean b)
    {
        collidable.shouldCheckForCollisions(b);
    }
}
