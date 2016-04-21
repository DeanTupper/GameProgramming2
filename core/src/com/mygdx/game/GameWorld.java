package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.CornerBumper;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;
import com.mygdx.game.subsystems.RenderSubsystem;
import com.mygdx.game.utils.Rectangle;

public class GameWorld
{
    public static final float DEFAULT_WORLD_WIDTH = 100f;
    public static final float DEFAULT_WORLD_HEIGHT = 100f;

    private static final float THRESHOLD_UPDATE_DELTA = 1000L / 60L;
    private static final int NUM_PLAYERS = 4;

    private final Rectangle worldBounds;

    private long timeOfLastUpdate;

    public GameWorld()
    {
        worldBounds = new Rectangle(0f, 0f, DEFAULT_WORLD_WIDTH, DEFAULT_WORLD_HEIGHT);
        buildWorld();

        //Shouldnt this be just 0 no game time has elapsed
        timeOfLastUpdate = 0;
    }

    private void buildWorld()
    {
        new CornerBumper(new Vector2(0,0),new Vector2(0,10),new Vector2(10,0), Color.WHITE);
        new CornerBumper(new Vector2(100,0),new Vector2(90,0),new Vector2(100,10), Color.WHITE);
        new CornerBumper(new Vector2(0,100),new Vector2(0,90),new Vector2(10,100), Color.WHITE);
        new CornerBumper(new Vector2(100,100),new Vector2(100,90),new Vector2(90,100), Color.WHITE);
    }

    public Rectangle getWorldBounds()
    {
        return worldBounds;
    }

    public void tick(float deltaInMillis)
    {
        float elapsedTime = deltaInMillis - timeOfLastUpdate;
//        updateWorld(elapsedTime);
        render(deltaInMillis);
    }

    private void updateWorld(float deltaInMillis)
    {
        BoardManager.get().update(deltaInMillis);
    }

    private void render(float deltaInMillis)
    {
        RenderSubsystem.get().update(deltaInMillis);
    }
}
