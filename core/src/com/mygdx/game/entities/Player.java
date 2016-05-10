package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.movables.PaddleMovable;
import com.mygdx.game.components.renderables.PlayerRenderable;
import com.mygdx.game.subsystems.MovableSubsystem;
import com.mygdx.game.subsystems.RenderSubsystem;
import com.mygdx.game.utils.shapes.Rectangle;

public class Player extends OverallPlayer
{
    public Player(float x, float y, Vector2 deltaVelocity, int negativeDirKeyCode, int positiveDirKeyCode, Color color)
    {
        this(new Vector2(x, y), deltaVelocity, positiveDirKeyCode, negativeDirKeyCode, color);
    }

    public Player(Vector2 position, Vector2 deltaVelocity, int negativeDirKeyCode, int positiveDirKeyCode, Color color)
    {
        width = DIMEN_MINOR;
        height = DIMEN_MINOR;

        if (deltaVelocity.x != 0f)
        {
            width = DIMEN_MAJOR;
            System.out.println("POS_X_HORIZONTAL_MAX = " + POS_X_HORIZONTAL_MAX);
            movementBounds = new Rectangle(POS_X_HORIZONTAL_MIN, position.y, POS_X_HORIZONTAL_MAX, 0f);
        }
        else
        {
            height = DIMEN_MAJOR;

            System.out.println("POS_Y_VERITCAL_MAX = " + POS_Y_VERITCAL_MAX);
            movementBounds = new Rectangle(position.x, POS_Y_VERITCAL_MIN, 0f, POS_Y_VERITCAL_MAX);
        }

        movable = new PaddleMovable(position, deltaVelocity, movementBounds, positiveDirKeyCode, negativeDirKeyCode);
        MovableSubsystem.get().register(movable);

        renderable = new PlayerRenderable(position, width, height, color);
        RenderSubsystem.get().register(renderable);
    }

    @Override
    public void destroy()
    {
        MovableSubsystem.get().remove(movable);
        RenderSubsystem.get().remove(renderable);
    }

    public Rectangle getBounds()
    {
        Vector2 pos = movable.getPosition();

        return new Rectangle(pos.x, pos.y, width, height);
    }
}
