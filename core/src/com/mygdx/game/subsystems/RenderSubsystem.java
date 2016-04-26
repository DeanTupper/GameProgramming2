package com.mygdx.game.subsystems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Viewports;
import com.mygdx.game.components.Renderable;

import java.util.HashSet;
import java.util.Set;

public class RenderSubsystem implements Subsystem
{
    private static RenderSubsystem instance;

    private final ShapeRenderer renderer = new ShapeRenderer();
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final OrthographicCamera camera = new OrthographicCamera();

    private int bufferWidth = (int) GameWorld.DEFAULT_WORLD_WIDTH;
    private int bufferHeight = (int) GameWorld.DEFAULT_WORLD_HEIGHT;

    private float bufferWidthScale;
    private float bufferHeightScale;

    private FrameBuffer buffer = new FrameBuffer(Pixmap.Format.RGB888, 500, 500, false);
    private Texture tex = new Texture(bufferWidth, bufferHeight, Pixmap.Format.RGB888);
    private TextureRegion texRegion = new TextureRegion(tex);

    private float screenWidth;
    private float screenHeight;

    private float halfScreenWidth;
    private float halfScreenHeight;

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
        camera.viewportHeight = GameWorld.DEFAULT_WORLD_HEIGHT;
        camera.viewportWidth = GameWorld.DEFAULT_WORLD_WIDTH;
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    private final Set<Renderable> renderables = new HashSet<Renderable>();

    @Override
    public void update(long deltaInMillis)
    {
        buffer.begin();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(camera.combined);

        renderer.begin();
        renderer.set(ShapeRenderer.ShapeType.Filled);

        for (Renderable current : renderables)
        {
            current.render(renderer);
        }

        renderer.setColor(Color.RED);
        renderer.line(0f, 0f, 0f, 0f, 100f, 0f);
        renderer.line(0f, 100f, 0f, 100f, 100f, 0f);
        renderer.line(0f, 0f, 0f, 100f, 0f, 0f);
        renderer.line(100f, 0f, 0f, 100f, 100f, 0f);

        renderer.end();

        buffer.end();

        texRegion = new TextureRegion(buffer.getColorBufferTexture());
        texRegion.flip(false, true);
    }

    public void remove(Renderable renderable)
    {
        renderables.remove(renderable);
    }

    public void register(Renderable renderable)
    {
        renderables.add(renderable);
    }

    public void renderWorld()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.combined);

        // Top Left - Player 1
        spriteBatch.draw(texRegion, 0f, 55f, 0f, 0f, 50f, 50f, 0.9f, 0.9f, 0f);

        // Top Right - Player 2
        spriteBatch.draw(texRegion, 50f, 5f, 50f, 50f, 50f, 50f, 0.9f, 0.9f, -90f);

        // Bottom Right - Player 3
        spriteBatch.draw(texRegion, 5f, -50f, 50f, 50f, 50f, 50f, 0.9f, 0.9f, -180f);

        // Bottom Left - Player 4
        spriteBatch.draw(texRegion, -50f, -5f, 50f, 50f, 50f, 50f, 0.9f, 0.9f, -270f);

        spriteBatch.end();
    }

    public void resized(int width, int height)
    {
        screenWidth = (float) width;
        screenHeight = (float) height;

        halfScreenWidth = screenWidth / 2.0f;
        halfScreenHeight = screenHeight / 2.0f;

        bufferWidthScale = (halfScreenWidth / bufferWidth);
        bufferHeightScale = (halfScreenHeight / bufferHeight);

        //tex = new Texture(width / 2, height / 2, Pixmap.Format.RGB888);

        System.err.println("screen: " + screenWidth + "," + screenHeight + "; halfScreen: " + halfScreenWidth + "," + halfScreenHeight + "; bufferScale: " + bufferWidthScale + "," + bufferHeightScale);
    }
}
