package com.mygdx.game.subsystems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.DeanTestGame;
import com.mygdx.game.GameWorld;
import com.mygdx.game.components.renderables.Renderable;
import com.mygdx.game.entities.Ball;
import com.mygdx.game.entities.CornerBumper;
import com.mygdx.game.entities.Player;
import com.mygdx.game.subsystems.BoardManagerSubSystem.BoardManager;
import com.mygdx.game.utils.Quad;
import com.mygdx.game.utils.UpdateDelta;

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

    private FrameBuffer buffer = new FrameBuffer(Pixmap.Format.RGB888, DeanTestGame.DEFAULT_APP_WIDTH, DeanTestGame.DEFAULT_APP_HEIGHT, false);
    private Texture tex = new Texture(WIDTH_BUFFER, HEIGHT_BUFFER, Pixmap.Format.RGB888);
    private TextureRegion texRegion = new TextureRegion(tex);
    BitmapFont font = new BitmapFont();

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
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("prstartk.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 7;
        parameter.color = Color.WHITE;

        font = generator.generateFont(parameter);
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
    public void update(long deltaInMillis, UpdateDelta updateDelta)
    {
        buffer.begin();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

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
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void remove(Renderable renderable)
    {
        renderables.remove(renderable);
    }

    public void register(Renderable renderable)
    {
        renderables.add(renderable);
    }

//    public void renderWorld()
//    {
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        Gdx.gl.glEnable(GL20.GL_BLEND);
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//
//
//        spriteBatch.begin();
//        spriteBatch.setProjectionMatrix(camera.combined);
//
//        // Top Left - Player 1
//        spriteBatch.draw(texRegion, 0f, 55f, 0f, 0f, 50f, 50f, 0.9f, 0.9f, 0f);
//
//        // Top Right - Player 2
//        spriteBatch.draw(texRegion, 50f, 5f, 50f, 50f, 50f, 50f, 0.9f, 0.9f, -90f);
//
//        // Bottom Right - Player 3
//        spriteBatch.draw(texRegion, 5f, -50f, 50f, 50f, 50f, 50f, 0.9f, 0.9f, -180f);
//
//        // Bottom Left - Player 4
//        spriteBatch.draw(texRegion, -50f, -5f, 50f, 50f, 50f, 50f, 0.9f, 0.9f, -270f);
//
//        renderScores();
//        spriteBatch.end();
//
//        shapeRenderer.begin();
//        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
//
//        for (Renderable current : renderables)
//        {
//            current.render(shapeRenderer);
//        }
//
//        shapeRenderer.end();
//    }

    private void renderScores()
    {
        renderScore(Player.COLOR_P1, spriteBatch, GameWorld.player1.getScore() + "", 0);

        renderScore(Player.COLOR_P2, spriteBatch, GameWorld.player2.getScore() + "", WIDTH_HALF_WORLD + 5f);

        renderScore(Player.COLOR_P3, spriteBatch, GameWorld.player3.getScore() + "", WIDTH_HALF_WORLD + 30f);

        renderScore(Player.COLOR_P4, spriteBatch, GameWorld.player4.getScore() + "", 25);
    }

    private void renderScore(Color color, SpriteBatch spriteBatch, String str, float x)
    {
        font.setColor(color);
        font.draw(spriteBatch, str, x, HEIGHT_HALF_WORLD + 3f);
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

        renderScores();
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

        if (GameWorld.debugMode)
        {
            debugRendering();
        }

        shapeRenderer.end();
    }

    private void debugRendering()
    {
        drawQuadGrid();
        drawBallQuads();
        drawCornerBumperQuads();
    }

    private void drawQuadGrid()
    {
        QuadSubsystem.get().render(shapeRenderer);
    }

    private void drawBallQuads()
    {
        shapeRenderer.setColor(Color.BLUE);

        for (Ball ball : BoardManager.get().getBalls())
        {
            Quad ballQuad = QuadSubsystem.get().getQuad(ball.getPosition());
            renderQuadAndNeighbors(ballQuad);
        }
    }

    private void drawCornerBumperQuads()
    {
        shapeRenderer.setColor(Color.WHITE);

        for (CornerBumper cornerBumper : BoardManager.get().getCornerBumpers())
        {
            Quad quad = QuadSubsystem.get().getQuad(cornerBumper.getPosition());
            renderQuadAndNeighbors(quad);
        }
    }

    private void renderQuadAndNeighbors(Quad quad)
    {
        shapeRenderer.rect(quad.getCol() * 4.5f, 55f + quad.getRow() * 4.5f, 4.5f, 4.5f);

        Set<Quad> neighbors = quad.getNeighbors();

        for (Quad neighbor : neighbors)
        {
            shapeRenderer.rect(neighbor.getCol() * 4.5f, 55f + neighbor.getRow() * 4.5f, 4.5f, 4.5f);
        }
    }
}
