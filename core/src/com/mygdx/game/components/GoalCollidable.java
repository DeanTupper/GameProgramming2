package com.mygdx.game.components;

import com.mygdx.game.entities.Player;

public class GoalCollidable
{
    private final Player player;

    public GoalCollidable(Movable movable, Player player)
    {
        this.player = player;
    }

    public boolean collidesWith(Collidable other)
    {
        return false;
    }

    public void resolveCollision(Collidable other)
    {
        player.decrementScore();

    }
}
