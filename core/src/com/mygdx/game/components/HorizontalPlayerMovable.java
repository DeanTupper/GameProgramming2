package com.mygdx.game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class HorizontalPlayerMovable extends Movable
{
    private final float leftBound;
    private final float rightBound;
    private final int leftKey;
    private final int rightKey;

    public HorizontalPlayerMovable(Vector2 position, Vector2 velocity, float leftBound, float rightBound, int leftKey, int rightKey)
    {
        super(position,velocity);
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
    }

    @Override
    public void move()
    {
        if(Gdx.input.isKeyPressed(leftKey)){
            this.setPosition(this.getPosition().add(new Vector2(-1,0)));
            if(this.getPosition().x < leftBound)
            {
                this.setPosition(new Vector2(leftBound,this.getPosition().y));
            }
        }
        else if(Gdx.input.isKeyPressed(rightKey)){
            this.setPosition(this.getPosition().add(new Vector2(1,0)));
            if(this.getPosition().x > rightBound - 10)
            {
                this.setPosition(new Vector2(rightBound-10,this.getPosition().y));
            }
        }
    }
}
