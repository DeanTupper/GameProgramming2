package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.CornerBumper;
import com.mygdx.game.entities.Player;
import com.mygdx.game.subsystems.*;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;
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
    private final PylonSubSystem pylonSubsystem;

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
        pylonSubsystem = PylonSubSystem.get();
    }

    private void buildWorld()
    {
        createCornerBumpers();
        createPlayers();
    }

    private void createCornerBumpers()
    {
        CornerBumper bottomLeft = new CornerBumper(new Vector2(3, 3), new Vector2(3, 13), new Vector2(13, 3), Color.WHITE);
        CornerBumper bottomRight = new CornerBumper(new Vector2(97, 3), new Vector2(87, 3), new Vector2(97, 13), Color.WHITE);
        CornerBumper topLeft = new CornerBumper(new Vector2(3, 97), new Vector2(3, 87), new Vector2(13, 97), Color.WHITE);
        CornerBumper topRight = new CornerBumper(new Vector2(97, 97), new Vector2(97, 87), new Vector2(87, 97), Color.WHITE);
    }

    private void createPlayers()
    {
        // Bottom - p1
        Player bottom = new Player(Player.POS_X_MID, Player.POS_Y_BOT, Player.VELOCITY_DELTA_HORIZONTAL, Input.Keys.Q, Input.Keys.E, Player.COLOR_P1);

        // Right - p2
        Player right = new Player(Player.POS_X_RIGHT, Player.POS_Y_MID, Player.VELOCITY_DELTA_VERTICAL, Input.Keys.N, Input.Keys.M, Player.COLOR_P2);

        // Top - p3
        Player top = new Player(Player.POS_X_MID, Player.POS_Y_TOP, Player.VELOCITY_DELTA_HORIZONTAL, Input.Keys.Z, Input.Keys.C, Player.COLOR_P3);

        // Left - p4
        Player left = new Player(Player.POS_X_LEFT, Player.POS_Y_MID, Player.VELOCITY_DELTA_VERTICAL, Input.Keys.I, Input.Keys.P, Player.COLOR_P4);
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
        pylonSubsystem.update(deltaInMillis);
    }

    private void render(long deltaInMillis)
    {
        renderSubsystem.renderWorld();
    }
}
