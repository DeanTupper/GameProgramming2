package com.mygdx.game.components.movables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.shapes.Rectangle;

public class PaddleMovable extends Movable
{
    private final Rectangle bounds;
    private final Vector2 deltaVelocity;

    private final int positiveDirKeyCode;
    private final int negativeDirKeyCode;

    public PaddleMovable(Vector2 position, Vector2 deltaVelocity, Rectangle bounds, int negativeDirKeyCode, int positiveDirKeyCode)
    {
        super(position, new Vector2());

        this.deltaVelocity = deltaVelocity;
        this.bounds = bounds;
        this.positiveDirKeyCode = positiveDirKeyCode;
        this.negativeDirKeyCode = negativeDirKeyCode;
    }

    @Override
    public void move(float worldTimeStep)
    {
        Vector2 dVel = new Vector2();

        if (Gdx.input.isKeyPressed(positiveDirKeyCode))
        {
            dVel.add(deltaVelocity);
        }

        if (Gdx.input.isKeyPressed(negativeDirKeyCode))
        {
            dVel.sub(deltaVelocity);
        }

        dVel.x *= worldTimeStep;
        dVel.y *= worldTimeStep;

        Vector2 pos = getPosition().add(dVel);

        pos.x = MathUtils.clamp(pos.x, bounds.x, bounds.right);
        pos.y = MathUtils.clamp(pos.y, bounds.y, bounds.top);
    }
}
