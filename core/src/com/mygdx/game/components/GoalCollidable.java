package com.mygdx.game.components;

import com.mygdx.game.entities.Player;

public class GoalCollidable extends Collidable
{
    private final Player player;

    public GoalCollidable(Movable movable, Player player)
    {
        super(movable);
        this.player = player;
    }

    @Override
    public boolean collidesWith(Collidable other)
    {
        return false;
    }

    @Override
    public void resolveCollision(Collidable other)
    {
        player.decrementScore();

    }
}
