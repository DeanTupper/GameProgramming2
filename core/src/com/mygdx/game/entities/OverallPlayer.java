package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameWorld;
import com.mygdx.game.components.movables.Movable;
import com.mygdx.game.components.renderables.PlayerRenderable;
import com.mygdx.game.subsystems.QuadSubsystem;
import com.mygdx.game.utils.Direction;
import com.mygdx.game.utils.Quad;
import com.mygdx.game.utils.shapes.Rectangle;

import java.util.Set;

public abstract class OverallPlayer extends Entity
{
    // Default colors
    public static final Color COLOR_P1 = Color.RED;
    public static final Color COLOR_P2 = Color.GREEN;
    public static final Color COLOR_P3 = Color.YELLOW;
    public static final Color COLOR_P4 = Color.ORANGE;

    // Size of major and minor dimensions (horizontal player -> major dimension is width)
    public static final float DIMEN_MAJOR = 10f;
    public static final float DIMEN_MINOR = 3f;

    public static final float WIDTH_HORIZONTAL_BOUNDS = GameWorld.DEFAULT_WORLD_WIDTH - (2.0f * CornerBumper.SIZE);
    public static final float HEIGHT_VERTICAL_BOUNDS = GameWorld.DEFAULT_WORLD_HEIGHT - (2.0f * CornerBumper.SIZE);

    // Initial Positions
    public static final float POS_X_LEFT = 0f;
    public static final float POS_X_MID = (GameWorld.DEFAULT_WORLD_WIDTH - DIMEN_MAJOR) / 2.0f;
    public static final float POS_X_RIGHT = GameWorld.DEFAULT_WORLD_WIDTH - DIMEN_MINOR;

    public static final float POS_Y_BOT = 0f;
    public static final float POS_Y_MID = (GameWorld.DEFAULT_WORLD_HEIGHT - DIMEN_MAJOR) / 2.0f;
    public static final float POS_Y_TOP = GameWorld.DEFAULT_WORLD_HEIGHT - DIMEN_MINOR;

    // Min and max x and y positions of the player based on the size of the corner bumpers
    public static final float POS_X_HORIZONTAL_MIN = CornerBumper.SIZE;
    public static final float POS_X_HORIZONTAL_MAX = GameWorld.DEFAULT_WORLD_WIDTH - (CornerBumper.SIZE * 2.0f) - DIMEN_MAJOR;

    public static final float POS_Y_VERITCAL_MIN = CornerBumper.SIZE;
    public static final float POS_Y_VERITCAL_MAX = GameWorld.DEFAULT_WORLD_HEIGHT - (CornerBumper.SIZE * 2.0f) - DIMEN_MAJOR;

    // The circleVelocity deltas used to move players on input
    public static final Vector2 VELOCITY_DELTA_HORIZONTAL = new Vector2(2f, 0f);
    public static final Vector2 VELOCITY_DELTA_VERTICAL = new Vector2(0f, 2f);

    protected PlayerRenderable renderable;
    protected Rectangle movementBounds;
    protected Movable movable;

    protected float width;
    protected float height;

    private Integer score = 1;

    public Integer getScore()
    {
        return score;
    }

    public void decrementScore()
    {
        score--;
    }

    public void createBarrier()
    {
        System.out.println("barrier");
        System.out.println("movementBounds.x = " + movementBounds.x);
        System.out.println("movementBounds.y = " + movementBounds.y);
        System.out.println("movementBounds.width = " + movementBounds.width);
        System.out.println("movementBounds = " + movementBounds);
        float tempWidth = movementBounds.width;
        float tempHeight = movementBounds.height;
        if (tempWidth == 0)
        {
            tempWidth = 3;
            tempHeight = 74;
        }
        if (tempHeight == 0)
        {
            tempHeight = 3;
            tempWidth = 74;
        }

        Direction edgeDir = getCollidableEdgeDir();

        new Barrier(new Vector2(movementBounds.x, movementBounds.y), tempWidth, tempHeight, edgeDir);
    }

    public Rectangle getPaddleBounds()
    {
        return new Rectangle(movable.getPosition().x, movable.getPosition().y, width, height);
    }

    public Direction getCollidableEdgeDir()
    {
        if (movementBounds.y == 0f)
        {
            return Direction.NORTH;
        }
        else if (movementBounds.y == 97f)
        {
            return Direction.SOUTH;
        }
        else if (movementBounds.x == 0f)
        {
            return Direction.EAST;
        }
        else if (movementBounds.x == 97f)
        {
            return Direction.WEST;
        }
        else
        {
            System.err.println("OverallPlayer::getCollidableEdgeDir- movementBounds: " + movementBounds);
            throw new AssertionError("wat");
        }
    }
}
