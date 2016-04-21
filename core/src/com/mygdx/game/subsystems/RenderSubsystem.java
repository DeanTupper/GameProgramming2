package com.mygdx.game.subsystems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.components.Renderable;

import java.util.HashSet;
import java.util.Set;

public class RenderSubsystem implements Subsystem
{
    private static RenderSubsystem instance;
    private final ShapeRenderer renderer = new ShapeRenderer();
    private final OrthographicCamera camera = new OrthographicCamera();

    public static RenderSubsystem get()
    {
        if (instance == null)
        {
            instance = new RenderSubsystem();
        }
        return instance;
    }

    private RenderSubsystem()
    {
        renderer.setAutoShapeType(true);
        initCamera();
    }

    public void initCamera()
    {
        camera.viewportHeight = 100;
        camera.viewportWidth = 100;
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0f);
        camera.update();
    }

    private final Set<Renderable> renderables = new HashSet<Renderable>();

    @Override
    public void update(float deltaInMillis)
    {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin();
        renderer.set(ShapeRenderer.ShapeType.Filled);
        for (Renderable current : renderables)
        {
            current.render(renderer);
        }

        renderer.end();
    }

    public void remove(Renderable renderable)
    {
        renderables.remove(renderable);
    }

    public void register(Renderable renderable)
    {
        renderables.add(renderable);
    }

    public static void render(Renderable movable)
    {
    }
}
