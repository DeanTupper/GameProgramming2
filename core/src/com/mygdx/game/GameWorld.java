package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.utils.Rectangle;

public class GameWorld
{
    public static final float DEFAULT_WORLD_WIDTH = 100f;
    public static final float DEFAULT_WORLD_HEIGHT = 100f;

    private static final float THRESHOLD_UPDATE_DELTA = 1000L / 60L;
    private static final int NUM_PLAYERS = 4;

    private final Rectangle worldBounds;
    private final ShapeRenderer renderer;

    private final OrthographicCamera[] cameras = new OrthographicCamera[NUM_PLAYERS];

    private long timeOfLastUpdate;

    public GameWorld()
    {
        worldBounds = new Rectangle(0f, 0f, DEFAULT_WORLD_WIDTH, DEFAULT_WORLD_HEIGHT);

        for (int i = 0; i < cameras.length; i++)
        {
            cameras[i] = new OrthographicCamera();
            cameras[i].rotate(i * 90f);
        }

        renderer = new ShapeRenderer();
        renderer.set(ShapeRenderer.ShapeType.Filled);

        timeOfLastUpdate = System.currentTimeMillis();
    }

    public Rectangle getWorldBounds()
    {
        return worldBounds;
    }

    public void tick()
    {
        render();

        long currentTime = System.currentTimeMillis();

        long delta = currentTime - timeOfLastUpdate;

        if (delta > THRESHOLD_UPDATE_DELTA)
        {
            updateWorld(delta);
        }
    }

    private void updateWorld(long deltaInMillis)
    {

    }

    private void render()
    {
        for (OrthographicCamera camera : cameras)
        {
            renderer.setProjectionMatrix(camera.combined);
            renderer.begin();


            renderer.end();
        }
    }
}
