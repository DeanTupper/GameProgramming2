package com.mygdx.game.subsystems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameWorld;
import com.mygdx.game.components.Renderable;
import com.mygdx.game.entities.Player;

import java.util.HashSet;
import java.util.Set;

public class RenderSubsystem implements Subsystem
{
    private static RenderSubsystem instance;

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final OrthographicCamera camera = new OrthographicCamera();

    private static final int WIDTH_BUFFER = (int) GameWorld.DEFAULT_WORLD_WIDTH;
    private static final int HEIGHT_BUFFER = (int) GameWorld.DEFAULT_WORLD_HEIGHT;

    private static final float WIDTH_HALF_WORLD = GameWorld.DEFAULT_WORLD_WIDTH / 2.0f;
    private static final float HEIGHT_HALF_WORLD = GameWorld.DEFAULT_WORLD_HEIGHT / 2.0f;

    private static final float SIZE_PLAYER_VIEW = 45.0f;

    private FrameBuffer buffer = new FrameBuffer(Pixmap.Format.RGB888, 500, 500, false);
    private Texture tex = new Texture(WIDTH_BUFFER, HEIGHT_BUFFER, Pixmap.Format.RGB888);
    private TextureRegion texRegion = new TextureRegion(tex);

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
        shapeRenderer.setAutoShapeType(true);
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

        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);

        for (Renderable current : renderables)
        {
            current.render(shapeRenderer);
        }

        shapeRenderer.end();

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

        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(Player.COLOR_P1);
        shapeRenderer.rect(0.1f, WIDTH_HALF_WORLD + 5.0f, SIZE_PLAYER_VIEW, SIZE_PLAYER_VIEW - 0.1f);

        shapeRenderer.setColor(Player.COLOR_P2);
        shapeRenderer.rect(WIDTH_HALF_WORLD + 5.0f, HEIGHT_HALF_WORLD + 5.0f, SIZE_PLAYER_VIEW, SIZE_PLAYER_VIEW - 0.1f);

        shapeRenderer.setColor(Player.COLOR_P3);
        shapeRenderer.rect(WIDTH_HALF_WORLD + 5.0f, 0f, SIZE_PLAYER_VIEW, SIZE_PLAYER_VIEW);

        shapeRenderer.setColor(Player.COLOR_P4);
        shapeRenderer.rect(0.1f, 0f, SIZE_PLAYER_VIEW, SIZE_PLAYER_VIEW);

        shapeRenderer.end();
    }
}
