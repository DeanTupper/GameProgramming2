package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.ColorType;
import com.mygdx.game.entities.CornerBumper;
import com.mygdx.game.entities.HorizontalPlayer;
import com.mygdx.game.entities.VerticalPlayer;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;
import com.mygdx.game.subsystems.CollidableSubsystem;
import com.mygdx.game.subsystems.MovableSubsystem;
import com.mygdx.game.subsystems.QuadSubsystem;
import com.mygdx.game.subsystems.RenderSubsystem;
import com.mygdx.game.utils.Rectangle;

public class GameWorld
{
    public static final float DEFAULT_WORLD_WIDTH = 100f;
    public static final float DEFAULT_WORLD_HEIGHT = 100f;

    private static final float THRESHOLD_UPDATE_DELTA = 1000L / 60L;
    //private static final float THRESHOLD_UPDATE_DELTA = 500L;

    private final Rectangle worldBounds;

    private long timeOfLastUpdate;

    private BoardManager boardManager;
    private MovableSubsystem movableSubsystem;
    private QuadSubsystem quadSubsystem;
    private CollidableSubsystem collidableSubsystem;
    private RenderSubsystem renderSubsystem;

    public GameWorld()
    {
        worldBounds = new Rectangle(0f, 0f, DEFAULT_WORLD_WIDTH, DEFAULT_WORLD_HEIGHT);
        buildWorld();

        timeOfLastUpdate = System.currentTimeMillis();

        boardManager = BoardManager.get();
        movableSubsystem = MovableSubsystem.get();
        quadSubsystem = QuadSubsystem.get();
        collidableSubsystem = CollidableSubsystem.get();
        renderSubsystem = RenderSubsystem.get();
    }

    private void buildWorld()
    {
        CornerBumper bottomLeft = new CornerBumper(new Vector2(3, 3), new Vector2(3, 13), new Vector2(13, 3), Color.WHITE);
        CornerBumper bottomRight = new CornerBumper(new Vector2(97, 3), new Vector2(87, 3), new Vector2(97, 13), Color.WHITE);
        CornerBumper topLeft = new CornerBumper(new Vector2(3, 97), new Vector2(3, 87), new Vector2(13, 97), Color.WHITE);
        CornerBumper topRight = new CornerBumper(new Vector2(97, 97), new Vector2(97, 87), new Vector2(87, 97), Color.WHITE);

        // Bottom - p1
        new HorizontalPlayer(new Vector2(45, 0), new Vector2(0, 0), ColorType.BLUE, 10, 3, 13, 87, Input.Keys.Q, Input.Keys.E, Color.RED);
        // Left - p4
        new VerticalPlayer(new Vector2(0, 45), new Vector2(0, 0), ColorType.BLUE, 3, 10, 87, 13, Input.Keys.I, Input.Keys.P, Color.ORANGE);
        // Top - p3
        new HorizontalPlayer(new Vector2(45, 100 - 3), new Vector2(0, 0), ColorType.BLUE, 10, 3, 13, 87, Input.Keys.Z, Input.Keys.C, Color.YELLOW);
        // Right - p2
        new VerticalPlayer(new Vector2(100 - 3, 45), new Vector2(0, 0), ColorType.BLUE, 3, 10, 87, 13, Input.Keys.N, Input.Keys.M, Color.GREEN);
    }

    public Rectangle getWorldBounds()
    {
        return worldBounds;
    }

    void tick()
    {
        long currentTime = System.currentTimeMillis();

        long elapsedTime = currentTime - timeOfLastUpdate;

        if (elapsedTime > THRESHOLD_UPDATE_DELTA)
        {
            updateWorld(elapsedTime);
            timeOfLastUpdate = currentTime;
        }

        render(elapsedTime);
    }

    private void updateWorld(long deltaInMillis)
    {
        boardManager.update(deltaInMillis);
        movableSubsystem.update(deltaInMillis);
        quadSubsystem.update(deltaInMillis);
        collidableSubsystem.update(deltaInMillis);

        renderSubsystem.update(deltaInMillis);
    }

    private void render(long deltaInMillis)
    {
        renderSubsystem.renderWorld();
    }
}
