package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameWorld;
import com.mygdx.game.components.collidables.RectangleCollidable;
import com.mygdx.game.components.movables.Movable;
import com.mygdx.game.components.movables.PaddleMovable;
import com.mygdx.game.components.renderables.PlayerRenderable;
import com.mygdx.game.subsystems.CollidableSubsystem;
import com.mygdx.game.subsystems.MovableSubsystem;
import com.mygdx.game.subsystems.RenderSubsystem;
import com.mygdx.game.utils.shapes.Rectangle;

public class Player extends Entity
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

    private final Movable movable;
    private final PlayerRenderable renderable;
    private final Rectangle movementBounds;

    private Integer score = 500;
    private final RectangleCollidable collidable;

    public Player(float x, float y, Vector2 deltaVelocity, int negativeDirKeyCode, int positiveDirKeyCode, Color color)
    {
        this(new Vector2(x, y), deltaVelocity, positiveDirKeyCode, negativeDirKeyCode, color);
    }

    public Player(Vector2 position, Vector2 deltaVelocity, int negativeDirKeyCode, int positiveDirKeyCode, Color color)
    {
        float width = DIMEN_MINOR;
        float height = DIMEN_MINOR;

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

        collidable = new RectangleCollidable(movable, movementBounds, width, height);
        CollidableSubsystem.get().register(collidable);
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
        if(tempWidth == 0){
            tempWidth =3;
            tempHeight = 74;
        }
        if(tempHeight == 0){
            tempHeight = 3;
            tempWidth = 74;
        }
        new Barrier(new Vector2(movementBounds.x,movementBounds.y),tempWidth,tempHeight);
    }


    public Integer getScore()
    {
       return score;
    }

    @Override
    public void destroy()
    {
        MovableSubsystem.get().remove(movable);
        RenderSubsystem.get().remove(renderable);
    }
}
