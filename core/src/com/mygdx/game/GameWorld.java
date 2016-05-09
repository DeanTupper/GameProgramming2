package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.AiPlayer;
import com.mygdx.game.entities.Barrier;
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
    private static final float THRESHOLD_UPDATE_DELTA = 1000L / 60L;

    public static AiPlayer player1;
    public static AiPlayer player2;
    public static AiPlayer player3;
    public static AiPlayer player4;


    public static final UpdateDelta INIT_UPDATE_DELTA = UpdateDelta.FAST;
    public static boolean debugMode = true;
    private UpdateDelta updateDelta = INIT_UPDATE_DELTA;
    public static long updateThreshold = INIT_UPDATE_DELTA.threshold;

    private static final boolean PAUSING_CHANGES_DEBUG_MODE = !debugMode;

    private boolean paused = false;

    private final Rectangle worldBounds;

    private long timeOfLastUpdate;
    private long elapsedTime;
    private long totalElapsedTime;

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

        boardManager = BoardManager.get();
        movableSubsystem = MovableSubsystem.get();
        quadSubsystem = QuadSubsystem.get();
        collidableSubsystem = CollidableSubsystem.get();
        collidableSubsystem.setGameWorld(this);
        renderSubsystem = RenderSubsystem.get();
        pylonSubsystem = PylonSubSystem.get();

        Gdx.input.setInputProcessor(this);

        timeOfLastUpdate = System.currentTimeMillis();
    }

    private void buildWorld()
    {
        createCornerBumpers();
        createPlayers();
    }

    private void createCornerBumpers()
    {
        CornerBumper bottomLeft = new CornerBumper(new Vector2(3, 3), new Vector2(3, 13), new Vector2(13, 3), Color.WHITE);
        new Barrier(new Vector2(0, 0), 13, 3);
        new Barrier(new Vector2(0, 0), 3, 13);
        CornerBumper bottomRight = new CornerBumper(new Vector2(97, 3), new Vector2(87, 3), new Vector2(97, 13), Color.WHITE);
        new Barrier(new Vector2(87, 0), 13, 3);
        new Barrier(new Vector2(97, 0), 3, 13);
        CornerBumper topLeft = new CornerBumper(new Vector2(3, 97), new Vector2(3, 87), new Vector2(13, 97), Color.WHITE);
        new Barrier(new Vector2(0, 87), 3, 13);
        new Barrier(new Vector2(0, 97), 13, 3);
        CornerBumper topRight = new CornerBumper(new Vector2(97, 97), new Vector2(97, 87), new Vector2(87, 97), Color.WHITE);
        new Barrier(new Vector2(87, 97), 13, 3);
        new Barrier(new Vector2(97, 87), 3, 13);
//        new Barrier(new Vector2(13,0),74,3);
    }

    private void createPlayers()
    {
        // Bottom - p1
//        player1 = new Player(Player.POS_X_MID, Player.POS_Y_BOT, Player.VELOCITY_DELTA_HORIZONTAL, Input.Keys.Q, Input.Keys.E, Player.COLOR_P1);
        player1 = new AiPlayer(Player.POS_X_MID, Player.POS_Y_BOT, Player.VELOCITY_DELTA_HORIZONTAL,Player.COLOR_P1,1);

        // Right - p2
//        player2 = new Player(Player.POS_X_RIGHT, Player.POS_Y_MID, Player.VELOCITY_DELTA_VERTICAL, Input.Keys.N, Input.Keys.M, Player.COLOR_P2);
        player2 = new AiPlayer(Player.POS_X_RIGHT, Player.POS_Y_MID, Player.VELOCITY_DELTA_VERTICAL, Player.COLOR_P2,2);

        // Top - p3
//        player3 = new Player(Player.POS_X_MID, Player.POS_Y_TOP, Player.VELOCITY_DELTA_HORIZONTAL, Input.Keys.C, Input.Keys.Z, Player.COLOR_P3);
        player3 = new AiPlayer(Player.POS_X_MID, Player.POS_Y_TOP, Player.VELOCITY_DELTA_HORIZONTAL, Player.COLOR_P3,3);

        // Left - p4
//        player4 = new Player(Player.POS_X_LEFT, Player.POS_Y_MID, Player.VELOCITY_DELTA_VERTICAL, Input.Keys.I, Input.Keys.P, Player.COLOR_P4);
        player4 = new AiPlayer(Player.POS_X_LEFT, Player.POS_Y_MID, Player.VELOCITY_DELTA_VERTICAL, Player.COLOR_P4,4);
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

            totalElapsedTime += elapsedTime;

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

        AiSubsystem.get().update(deltaInMillis, updateDelta);
        if (player1.getScore() == 0)
        {
            player1.createBarrier();
        }
        if (player2.getScore() == 0)
        {
            player2.createBarrier();
        }
        if (player3.getScore() == 0)
        {
            player3.createBarrier();
        }
        if (player4.getScore() == 0)
        {
            player4.createBarrier();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            boardManager.spawnBall();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT))
        {
            boardManager.spawnPylon();
        }

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


        if (paused)
        {
            if (prevUpdateThreshold != updateDelta.threshold)
            {
                double ratio = (double) updateDelta.threshold / prevUpdateThreshold;
                elapsedTime *= ratio;
            }
        }
    }

    public void pause()
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
