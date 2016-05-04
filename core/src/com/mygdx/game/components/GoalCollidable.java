package com.mygdx.game.components;

import com.mygdx.game.entities.Player;
import com.mygdx.game.utils.collision.Collision;

public class GoalCollidable extends Collidable
{
    private final Player player;

    public GoalCollidable(Movable movable, Player player)
    {
        super(movable);
        this.player = player;
    }

    @Override
    public void resolveCollision(Collidable other)
    {
        player.decrementScore();

    }

    @Override
    public Collision checkForCollision(Collidable collidable, long deltaInMillis)
    {
        return null;
    }
}
