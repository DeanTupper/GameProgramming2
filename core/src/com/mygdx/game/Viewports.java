package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * @author John Knight
 * Copyright http://www.jksgames.com
 *
 */
public class Viewports extends ApplicationAdapter {
	
	private static final int NUM_VIEWPORTS = 4;
	private static final float WORLD_WIDTH = 1024;
	private static final float WORLD_HEIGHT = 768;
	private static final float MAX_ZOOM_IN = 0.02f;
	private static final float MAX_ZOOM_OUT = 4f;
	private static final float SPEED_ZOOM_SECONDS = 4f;
	private static final float SPEED_ROTATE_SECONDS = 1.5f;
	private static final float SPEED_TRANSLATE_SECONDS = 400f;
	private static float MAX_SHAKE_X = 10;
	private static float MAX_SHAKE_Y = 10;
	private static float MAX_SHAKE_ROTATION = 4;
	private static final float MAX_SHAKE_TIME = 0.5f;
	
	private static final int STATUS_TEXT_X = 10;
	private static final int STATUS_TEXT_Y = 470;

	private  int viewportWidth;
	private  int viewportHeight;
	
	private final Color colCornflowerBlue = new Color(100f/255f, 149f/255f, 237f/255f, 1);
	private SpriteBatch spriteBatch;
	private BitmapFont font;
	private ShaderProgram shader;
	private Mesh mesh;
	private Texture texture;
		
	private OrthographicCamera[] cameras = new OrthographicCamera[NUM_VIEWPORTS];
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private Body guardBody;
    private Body player1Body;

    private void createTexture () {
		texture = new Texture("badlogic.jpg"); // 1024 x 768
	}
	
	private void initCameras(int width, int height){
		
		for(int i = 0; i < NUM_VIEWPORTS; i++){
			cameras[i] = new OrthographicCamera(width/2, width/2);
            cameras[i].zoom = 3f;
//            cameras[i].zoom = 3f;
//			cameras[i].zoom = (MAX_ZOOM_OUT - MAX_ZOOM_IN) / 2;
//			cameras[i].initZoom(MAX_ZOOM_IN, MAX_ZOOM_OUT, SPEED_ZOOM_SECONDS, (MAX_ZOOM_OUT - MAX_ZOOM_IN) / 2);
		}

//        cameras[1].rotate(180f);
//        cameras[2].rotate(180f);
//        cameras[3].rotate(180f);
//		// zoom in a bit for panning
//		cameras[2].initZoom(MAX_ZOOM_IN, MAX_ZOOM_OUT, SPEED_ZOOM_SECONDS, (MAX_ZOOM_OUT - MAX_ZOOM_IN) / 4);
//
//		// zoom in a bit for shaking
//		cameras[3].initZoom(MAX_ZOOM_IN, MAX_ZOOM_OUT, SPEED_ZOOM_SECONDS, (MAX_ZOOM_OUT - MAX_ZOOM_IN) / 4);
//
//		//init camera for zooming
//		cameras[0].initZoom(MAX_ZOOM_IN, MAX_ZOOM_OUT, SPEED_ZOOM_SECONDS, 1f);
//
//		// init camera for rotation
//		cameras[1].initRotate(SPEED_ROTATE_SECONDS);
//
//		// init camera for translation
//		cameras[2].initTranslate(WORLD_WIDTH, WORLD_HEIGHT, SPEED_TRANSLATE_SECONDS);
//
//		// init camera for shaking
//		cameras[3].initShake(MAX_SHAKE_X, MAX_SHAKE_Y, MAX_SHAKE_ROTATION, MAX_SHAKE_TIME);
	}

    private World buildWorld() {
        World world = new World(new Vector2(0, 0), true);

        buildGoal(0, 249, 320, 0,world);
        buildGoal(0, -249, 320, 0,world);
        buildGoal(-249, 0, 0, 320,world);
        buildGoal(249, 0, 0, 320,world);

        return world;
    }

    private void buildGoal(int x, int y, float width, float height, World world) {

        // Create our playerBody definition
        BodyDef groundBodyDef2 = new BodyDef();
        // Set its world position
        groundBodyDef2.position.set(new Vector2(x, y));

        // Create a playerBody from the defintion and add it to the world
        Body groundBody2 = world.createBody(groundBodyDef2);

        // Create a polygon shape
        PolygonShape groundBox2 = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox2.setAsBox(width, height);
        // Create a fixture from our polygon shape and add it to our ground playerBody
        groundBody2.createFixture(groundBox2, 0.0f);
        // Clean up after ourselves
//
//        BodyDef goalBodyDef = new BodyDef();
//        // Set its world position
//        goalBodyDef.position.set(new Vector2(x, y));
//
//        // Create a playerBody from the defintion and add it to the world
//        Body goalBody = world.createBody(goalBodyDef);
//
//        // Create a polygon shape
//        PolygonShape goalBox = new PolygonShape();
//        // Set the polygon shape as a box which is twice the size of our view port and 20 high
//        // (setAsBox takes half-width and half-height as arguments)
//        goalBox.setAsBox(width,height);
//        // Create a fixture from our polygon shape and add it to our ground playerBody
//        goalBody.createFixture(goalBox, 0.0f);
//        // Clean up after ourselves
    }
	
	@Override
	public void create () {

        Box2D.init();
        debugRenderer = new Box2DDebugRenderer();
        world = buildWorld();

        // First we create a playerBody definition
        BodyDef guardBodyDef = new BodyDef();
        // We set our playerBody to dynamic, for something like ground which doesn't move we would set it to StaticBody
        guardBodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our playerBody's starting position in the world
        guardBodyDef.position.set(0, 50);

        // Create our playerBody in the world using our playerBody definition
        guardBody = world.createBody(guardBodyDef);
        guardBody.setLinearVelocity(1,10000);


        // Create a circle shape and set its radius to 6
        CircleShape playerShape = new CircleShape();
//        playerShape.setAsBox(2, 2);
        playerShape.setRadius(5f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerShape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.9f; // Make it bounce a little bit

        // Create our fixture and attach it to the playerBody
        Fixture guardFix = guardBody.createFixture(fixtureDef);


        // Create our playerBody definition
        BodyDef player1Def = new BodyDef();
        player1Def.type =BodyDef.BodyType.DynamicBody;
        // Set its world position
        player1Def.position.set(new Vector2(0-10, -243));

        // Create a playerBody from the defintion and add it to the world
        player1Body = world.createBody(player1Def);

        // Create a polygon shape
        PolygonShape groundBox2 = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox2.setAsBox(20, 1);
        // Create a fixture from our polygon shape and add it to our ground playerBody
        player1Body.createFixture(groundBox2, 0.0f);


//		float halfWidth = WORLD_WIDTH / 2;
//		float halfHeight = WORLD_HEIGHT / 2;
//
//		mesh = new Mesh(true, 4, 0,
//				new VertexAttribute(Usage.Position, 2, "a_position"),
//				new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoord")
//		);
//
//		float[] vertices = {
//				-halfWidth, -halfHeight,	// quad bottom left
//				0.0f, 1.0f, 				// texture bottom left
//				halfWidth, -halfHeight, 	// quad bottom right
//				1f, 1.0f, 	    			// texture bottom right
//				-halfWidth, halfHeight,		// quad top left
//				0.0f, 0.0f, 				// texture top left
//				halfWidth, halfHeight,		// quad top right
//				1.0f, 0.0f 					// texture top-right
//
//		};
//
//		mesh.setVertices(vertices);
//		createTexture();
//		createMeshShader();
//		font = new BitmapFont();
//		spriteBatch = new SpriteBatch();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewportWidth = width / 2;
		viewportHeight = height / 2;
		initCameras(width, height);
	}

	
	private void renderCamera(OrthographicCamera camera){
		Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
//		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		texture.bind();
//
//		shader.begin();
//		shader.setUniformi("u_texture", 0);
//		shader.setUniformMatrix("u_projTrans", camera.combined);
//		mesh.render(shader, GL20.GL_TRIANGLE_STRIP);
        debugRenderer.render(world, camera.combined);
//		shader.end();
	}
	
	private void updateCameras(){
		for(OrthographicCamera camera: cameras){
			camera.update();
		}
	}
	
	private void update(){
		updateCameras();
//		updateCameraZoom(delta, cameras[0]);
//		updateCameraRotate(delta, cameras[1]);
//		updateCameraTranslate(delta, cameras[2]);
//		updateCameraShake(delta, cameras[3]);
	}
	
	@Override
	public void render () {
        update();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		

		// Bottom left
		Gdx.gl.glViewport(0, 0, viewportWidth, viewportHeight);
        System.out.println("0");
        renderCamera(cameras[0]);
				
		// Top left
		Gdx.gl.glViewport(0, viewportHeight, viewportWidth, viewportHeight);
        System.out.println("2");
		renderCamera(cameras[1]);		
		
		// Top right
		Gdx.gl.glViewport(viewportWidth,  viewportHeight, viewportWidth, viewportHeight);
        System.out.println("3");
		renderCamera(cameras[2]);		
		
		// Bottom Right
		Gdx.gl.glViewport(viewportWidth, 0, viewportWidth, viewportHeight);
        System.out.println("4");
		renderCamera(cameras[3]);

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player1Body.applyLinearImpulse(-1000f, 0, player1Body.getPosition().x, player1Body.getPosition().y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player1Body.applyForce(1000f, 0, player1Body.getPosition().x, player1Body.getPosition().y, true);
            //guardBody.applyLinearImpulse(1000f, 0, guardBody.getPosition().x, guardBody.getPosition().y, true);
        }
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            player1Body.applyLinearImpulse(0, 1000f, player1Body.getPosition().x, player1Body.getPosition().y, true);
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            guardBody.applyLinearImpulse(0, -1000f, guardBody.getPosition().x, guardBody.getPosition().y, true);
//        }


        world.step(1 / 60f, 6, 2);
	}
}