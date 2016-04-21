package com.mygdx.game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class VerticalPlayerMovable extends Movable
{
    private final float topBound;
    private final float bottomBound;
    private final int upKey;
    private final int downKey;

    public VerticalPlayerMovable(Vector2 position, Vector2 velocity, float topBound, float bottomBound, int upKey, int downKey)
    {
        super(position, velocity);
        this.topBound = topBound;
        this.bottomBound = bottomBound;
        this.upKey = upKey;
        this.downKey = downKey;
    }

    @Override
    public void move()
    {
        if (Gdx.input.isKeyPressed(upKey))

        {
            this.setPosition(this.getPosition().add(new Vector2(0, -1)));
            if (this.getPosition().y < bottomBound)
            {
                this.setPosition(new Vector2(this.getPosition().x, bottomBound));
            }
        }

        else if (Gdx.input.isKeyPressed(downKey))

        {
            this.setPosition(this.getPosition().add(new Vector2(0, 1)));
            if (this.getPosition().y > topBound - 10)
            {
                this.setPosition(new Vector2(this.getPosition().x, topBound-10));
            }
        }
    }
}
