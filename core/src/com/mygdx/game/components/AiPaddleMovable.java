package com.mygdx.game.components;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.shapes.Rectangle;


public class AiPaddleMovable extends Movable
{
    private final Rectangle bounds;

    private final Vector2 deltaVelocity;

    private Boolean positive;
    private Boolean negative;
    ;

    public AiPaddleMovable(Vector2 position, Vector2 deltaVelocity, Rectangle bounds, Boolean positive, Boolean negative)
    {
        super(position, new Vector2());

        this.deltaVelocity = deltaVelocity;
        this.bounds = bounds;
        this.positive = positive;
        this.negative = negative;
    }

    @Override
    public void move(long deltaInMillis)
    {
        Vector2 dVel = new Vector2();
        if (positive)
        {
            dVel.add(deltaVelocity);
        }

        if (negative)
        {
            dVel.sub(deltaVelocity);
        }

        Vector2 pos = getPosition().add(dVel);

        pos.x = MathUtils.clamp(pos.x, bounds.x, bounds.right);
        pos.y = MathUtils.clamp(pos.y, bounds.y, bounds.top);
    }

    public void setPositive(Boolean positive)
    {
        this.positive = positive;
    }

    public void setNegative(Boolean negative)
    {
        this.negative = negative;
    }

//    public void setPositive(Boolean positive)
//    {
//        this.positive = positive;
//    }
}
