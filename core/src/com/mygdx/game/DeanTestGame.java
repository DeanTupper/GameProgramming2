package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.subsystems.RenderSubsystem;

public class DeanTestGame extends ApplicationAdapter
{
    private GameWorld gameWorld;

    @Override
    public void create()
    {
        gameWorld = new GameWorld();
    }

    @Override
    public void resize(int width, int height)
    {
        System.out.println("resize");
        RenderSubsystem.get().initCamera();
    }

    @Override
    public void render()
    {
        gameWorld.tick(Gdx.graphics.getDeltaTime());
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
