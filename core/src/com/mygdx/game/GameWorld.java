package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.CornerBumper;
import com.mygdx.game.entities.Player;
import com.mygdx.game.subsystems.*;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;
import com.mygdx.game.utils.UpdateDelta;
import com.mygdx.game.utils.shapes.Rectangle;

public class GameWorld implements InputProcessor
{
    public static final float DEFAULT_WORLD_WIDTH = 100f;
    public static final float DEFAULT_WORLD_HEIGHT = 100f;

    public static boolean debugMode = true;
    private UpdateDelta updateDelta = UpdateDelta.MEDIUM;
    public static long updateThreshold = UpdateDelta.MEDIUM.threshold;

    private static final boolean PAUSING_CHANGES_DEBUG_MODE = !debugMode;

    private boolean paused = false;

    private final Rectangle worldBounds;

    private long timeOfLastUpdate;
    private long elapsedTime;

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

        Gdx.input.setInputProcessor(this);
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
        if (!paused)
        {
            long currentTime = System.currentTimeMillis();

            elapsedTime = currentTime - timeOfLastUpdate;

            if (elapsedTime > updateDelta.threshold)
            {
                updateWorld(elapsedTime);
                timeOfLastUpdate = currentTime;
            }
        }

        render();
    }

    private void updateWorld(long deltaInMillis)
    {
        quadSubsystem.update(deltaInMillis, updateDelta);
        collidableSubsystem.update(deltaInMillis, updateDelta);

        boardManager.update(deltaInMillis, updateDelta);
        pylonSubsystem.update(deltaInMillis, updateDelta);

        renderSubsystem.update(deltaInMillis, updateDelta);
    }

    private void render()
    {
        renderSubsystem.renderWorld();
    }

    @Override
    public boolean keyDown(int keycode)
    {
        boolean handled = true;

        switch (keycode)
        {
            case Input.Keys.ENTER:
                pause();
                break;
            case Input.Keys.LEFT_BRACKET:
                changeUpdateDelta(false);
                break;
            case Input.Keys.RIGHT_BRACKET:
                changeUpdateDelta(true);
                break;
            default:
                handled = false;
        }

        return handled;
    }

    private void changeUpdateDelta(boolean faster)
    {
        long prevUpdateThreshold = updateDelta.threshold;

        if (faster)
        {
            updateDelta = updateDelta.prev();
        }
        else
        {
            updateDelta = updateDelta.next();
        }

        updateThreshold = updateDelta.threshold;

        System.err.println("GameWorld::changeUpdateDelta - faster:[" + faster + "], updateDelta.threshold: " + updateDelta.threshold);

        if (paused)
        {
            if (prevUpdateThreshold != updateDelta.threshold)
            {
                double ratio = (double) updateDelta.threshold / prevUpdateThreshold;
                elapsedTime *= ratio;
            }
        }
    }

    private void pause()
    {
        paused = !paused;

        if (PAUSING_CHANGES_DEBUG_MODE)
        {
            debugMode = !debugMode;
        }

        if (!paused)
        {
            timeOfLastUpdate = System.currentTimeMillis();
            timeOfLastUpdate -= elapsedTime;
        }
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
