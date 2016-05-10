package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.*;
import com.mygdx.game.subsystems.*;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;
import com.mygdx.game.utils.Direction;
import com.mygdx.game.utils.UpdateDelta;
import com.mygdx.game.utils.shapes.Rectangle;

import java.util.HashSet;
import java.util.Set;

public class GameWorld implements InputProcessor
{
    public static final float DEFAULT_WORLD_WIDTH = 100f;
    public static final float DEFAULT_WORLD_HEIGHT = 100f;

    public static OverallPlayer player1;
    public static OverallPlayer player2;
    public static OverallPlayer player3;
    public static OverallPlayer player4;

    private boolean player1Alive = true;
    private boolean player2Alive = true;
    private boolean player3Alive = true;
    private boolean player4Alive = true;

    public static final UpdateDelta INIT_UPDATE_DELTA = UpdateDelta.FAST;
    public static boolean debugMode = true;
    private UpdateDelta updateDelta = INIT_UPDATE_DELTA;
    public static long updateThreshold = INIT_UPDATE_DELTA.threshold;

    private static final boolean PAUSING_CHANGES_DEBUG_MODE = !debugMode;

    private boolean paused = false;

    private final Rectangle worldBounds;

    private long timeOfLastUpdate;
    private long elapsedTime;

    private Goal player1Goal;
    private Goal player2Goal;
    private Goal player3Goal;
    private Goal player4Goal;

    private BoardManager boardManager;
    private QuadSubsystem quadSubsystem;
    private CollidableSubsystem collidableSubsystem;
    private RenderSubsystem renderSubsystem;
    private final PylonSubSystem pylonSubsystem;

    public GameWorld()
    {
        quadSubsystem = QuadSubsystem.get();
        worldBounds = new Rectangle(0f, 0f, DEFAULT_WORLD_WIDTH, DEFAULT_WORLD_HEIGHT);
        buildWorld();


        boardManager = BoardManager.get();
        boardManager.setGameWorld(this);
        collidableSubsystem = CollidableSubsystem.get();
        collidableSubsystem.setGameWorld(this);
        renderSubsystem = RenderSubsystem.get();
        pylonSubsystem = PylonSubSystem.get();

        Gdx.input.setInputProcessor(this);

        timeOfLastUpdate = System.currentTimeMillis();
    }

    private void buildWorld()
    {
        createPlayers();
        createCornerBumpers();
        createGoals();
    }

    private void createGoals()
    {
        System.err.println("BoardManager::createGoals - player1: " + player1 + "; player2: " + player2);
        QuadSubsystem quadSubsystem = QuadSubsystem.get();
        // Bottom
        player1Goal = new Goal(quadSubsystem.getGoalQuads(0, 0, 10, 2), new Rectangle(0f, 0f, 100f, 3f), Direction.NORTH, player1);
        // Right
        player2Goal = new Goal(quadSubsystem.getGoalQuads(8, 0, 10, 10), new Rectangle(97f, 0f, 3f, 100f), Direction.WEST, GameWorld.player2);
        //Top
        player3Goal = new Goal(quadSubsystem.getGoalQuads(0, 8, 10, 10), new Rectangle(0f, 97f, 100f, 3f), Direction.SOUTH, GameWorld.player3);
        // Left
        player4Goal = new Goal(quadSubsystem.getGoalQuads(0, 0, 2, 10), new Rectangle(0f, 0f, 3f, 100f), Direction.EAST, GameWorld.player4);
    }

    private void createCornerBumpers()
    {
        CornerBumper bottomLeft = new CornerBumper(new Vector2(3, 3), new Vector2(3, 13), new Vector2(13, 3), Color.WHITE);
        new VisualBarrier(new Vector2(0, 0), 13, 3);
        new VisualBarrier(new Vector2(0, 0), 3, 13);
        CornerBumper bottomRight = new CornerBumper(new Vector2(97, 3), new Vector2(87, 3), new Vector2(97, 13), Color.WHITE);
        new VisualBarrier(new Vector2(87, 0), 13, 3);
        new VisualBarrier(new Vector2(97, 0), 3, 13);
        CornerBumper topLeft = new CornerBumper(new Vector2(3, 97), new Vector2(3, 87), new Vector2(13, 97), Color.WHITE);
        new VisualBarrier(new Vector2(0, 87), 3, 13);
        new VisualBarrier(new Vector2(0, 97), 13, 3);
        CornerBumper topRight = new CornerBumper(new Vector2(97, 97), new Vector2(97, 87), new Vector2(87, 97), Color.WHITE);
        new VisualBarrier(new Vector2(87, 97), 13, 3);
        new VisualBarrier(new Vector2(97, 87), 3, 13);
//        new Barrier(new Vector2(13,0),74,3, Direction.NORTH);
    }

    private void createPlayers()
    {
        // Bottom - p1
        player1 = new Player(Player.POS_X_MID, Player.POS_Y_BOT, Player.VELOCITY_DELTA_HORIZONTAL, Input.Keys.Q, Input.Keys.E, Player.COLOR_P1,quadSubsystem.getGoalQuads(0, 0, 10, 2));
//        player1 = new AiPlayer(Player.POS_X_MID, Player.POS_Y_BOT, Player.VELOCITY_DELTA_HORIZONTAL,Player.COLOR_P1,1);

        // Right - p2
        player2 = new Player(Player.POS_X_RIGHT, Player.POS_Y_MID, Player.VELOCITY_DELTA_VERTICAL, Input.Keys.N, Input.Keys.M, Player.COLOR_P2,quadSubsystem.getGoalQuads(8, 0, 10, 10));
//        player2 = new AiPlayer(Player.POS_X_RIGHT, Player.POS_Y_MID, Player.VELOCITY_DELTA_VERTICAL, Player.COLOR_P2,2);

        // Top - p3
        player3 = new Player(Player.POS_X_MID, Player.POS_Y_TOP, Player.VELOCITY_DELTA_HORIZONTAL, Input.Keys.C, Input.Keys.Z, Player.COLOR_P3,quadSubsystem.getGoalQuads(0, 8, 10, 10));
//        player3 = new AiPlayer(Player.POS_X_MID, Player.POS_Y_TOP, Player.VELOCITY_DELTA_HORIZONTAL, Player.COLOR_P3,3);

        // Left - p4
        player4 = new Player(Player.POS_X_LEFT, Player.POS_Y_MID, Player.VELOCITY_DELTA_VERTICAL, Input.Keys.I, Input.Keys.P, Player.COLOR_P4,quadSubsystem.getGoalQuads(0, 0, 2, 10));
        //player4 = new AiPlayer(Player.POS_X_LEFT, Player.POS_Y_MID, Player.VELOCITY_DELTA_VERTICAL, Player.COLOR_P4,4);
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

        AiSubsystem.get().update(deltaInMillis, updateDelta);
        if (player1.getScore() == 0 && player1Alive)
        {
            player1.createBarrier();
            player1Alive = false;
            renderSubsystem.remove(player1.getRenderable());
            player1Goal.shouldCheckForCollisions(false);
        }
        if (player2.getScore() == 0 && player2Alive)
        {
            player2Alive = false;
            player2.createBarrier();
            renderSubsystem.remove(player2.getRenderable());
            player2Goal.shouldCheckForCollisions(false);
        }
        if (player3.getScore() == 0 && player3Alive)
        {
            player3Alive = false;
            player3.createBarrier();
            renderSubsystem.remove(player3.getRenderable());
            player3Goal.shouldCheckForCollisions(false);
        }
        if (player4.getScore() == 0 && player4Alive)
        {
            player4Alive = false;
            player4.createBarrier();
            renderSubsystem.remove(player4.getRenderable());
            player4Goal.shouldCheckForCollisions(false);
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
