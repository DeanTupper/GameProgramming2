package com.mygdx.game.components.collidables;

import com.mygdx.game.components.movables.Movable;
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
    public void resolveCollision(Collidable other, Collision collision)
    {
        player.decrementScore();
    }
}
