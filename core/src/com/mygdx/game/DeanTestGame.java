package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;

public class DeanTestGame extends ApplicationAdapter
{
    public static final int DEFAULT_APP_HEIGHT = 1300;
    public static final int DEFAULT_APP_WIDTH = 1300;
    private GameWorld gameWorld;

    @Override
    public void create()
    {
        gameWorld = new GameWorld();
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void render()
    {
        gameWorld.tick();
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void dispose()
    {
    }
}
