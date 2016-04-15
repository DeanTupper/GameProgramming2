package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyGdxGame extends ApplicationAdapter {
    private static final double DEGTORAD = Math.PI / 180;
    SpriteBatch batch;
    Texture img;
    private Stage stage;
    private Mesh mesh;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private BodyDef playerBodyDef;
    private Body playerBody;
    private BodyDef guardBodyDef;
    private Body guardBody;
    private Body postBody;
    private Fixture guardFix;
    private Fixture postFix;
    private Fixture visionFix;
    private Fixture playerFix;
    private int chase = -1;
    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
    String s = "";
    boolean leftPost = false;


    @Override
    public void create() {
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(200.0f, 200.0f);
//        world.setAutoClearForces(true);
		buildWorld();
        createPlayer();
        createGuard();
        createPost();
    }

    private void buildWorld() {
        // Create our playerBody definition
		world = new World(new Vector2(0, 0), true);

		buildGoal(0,99,camera.viewportWidth,0);
		buildGoal(0,-99,camera.viewportWidth,0);
		buildGoal(99,0,0,camera.viewportHeight);
		buildGoal(-99,0,0,camera.viewportHeight);
//        BodyDef groundBodyDef = new BodyDef();
//        // Set its world position
//        groundBodyDef.position.set(new Vector2(0, 99.0f));
//
//        // Create a playerBody from the defintion and add it to the world
//        Body groundBody = world.createBody(groundBodyDef);
//
//        // Create a polygon shape
//        PolygonShape groundBox = new PolygonShape();
//        // Set the polygon shape as a box which is twice the size of our view port and 20 high
//        // (setAsBox takes half-width and half-height as arguments)
//        groundBox.setAsBox(camera.viewportWidth, 0f);
//        // Create a fixture from our polygon shape and add it to our ground playerBody
//        groundBody.createFixture(groundBox, 0.0f);
//        // Clean up after ourselves
//
//        // Create our playerBody definition
//        BodyDef groundBodyDef2 = new BodyDef();
//        // Set its world position
//        groundBodyDef2.position.set(new Vector2(0, -99.6f));
//
//        // Create a playerBody from the defintion and add it to the world
//        Body groundBody2 = world.createBody(groundBodyDef2);
//
//        // Create a polygon shape
//        PolygonShape groundBox2 = new PolygonShape();
//        // Set the polygon shape as a box which is twice the size of our view port and 20 high
//        // (setAsBox takes half-width and half-height as arguments)
//        groundBox2.setAsBox(camera.viewportWidth, 0f);
//        // Create a fixture from our polygon shape and add it to our ground playerBody
//        groundBody2.createFixture(groundBox2, 0.0f);
//        // Clean up after ourselves
//
//        // Create our playerBody definition
//        BodyDef groundBodyDef3 = new BodyDef();
//        // Set its world position
//        groundBodyDef3.position.set(new Vector2(-99.6f, 0));
//
//        // Create a playerBody from the defintion and add it to the world
//        Body groundBody3 = world.createBody(groundBodyDef3);
//
//        // Create a polygon shape
//        PolygonShape groundBox3 = new PolygonShape();
//        // Set the polygon shape as a box which is twice the size of our view port and 30 high
//        // (setAsBox takes half-width and half-height as arguments)
//        groundBox3.setAsBox(0, camera.viewportHeight);
//        // Create a fixture from our polygon shape and add it to our ground playerBody
//        groundBody3.createFixture(groundBox3, 0.0f);
//        // Clean up after ourselves
//
//        // Create our playerBody definition
//        BodyDef groundBodyDef4 = new BodyDef();
//        // Set its world position
//        groundBodyDef4.position.set(new Vector2(+99.6f, 0));
//
//        // Create a playerBody from the defintion and add it to the world
//        Body groundBody4 = world.createBody(groundBodyDef4);
//
//        // Create a polygon shape
//        PolygonShape groundBox4 = new PolygonShape();
//        // Set the polygon shape as a box which is twice the size of our view port and 40 high
//        // (setAsBox takes half-width and half-height as arguments)
//        groundBox4.setAsBox(0, camera.viewportHeight);
//        // Create a fixture from our polygon shape and add it to our ground playerBody
//        groundBody4.createFixture(groundBox4, 0.0f);
//        // Clean up after ourselves
//
//        // Create our playerBody definition
//        BodyDef groundBodyDef5 = new BodyDef();
//        // Set its world position
//        groundBodyDef5.position.set(new Vector2(0, 0));
//
//        // Create a playerBody from the defintion and add it to the world
//        Body groundBody5 = world.createBody(groundBodyDef5);
//
//        // Create a polygon shape
//        PolygonShape groundBox5 = new PolygonShape();
//        // Set the polygon shape as a box which is twice the size of our view port and 50 high
//        // (setAsBox takes half-width and half-height as arguments)
//        groundBox5.setAsBox(60, 20);
//        // Create a fixture from our polygon shape and add it to our ground playerBody
//        groundBody5.createFixture(groundBox5, 0.0f);
//        // Clean up after ourselves
    }

	private void buildGoal(int x, int y, float width, float height) {
		BodyDef goalBodyDef = new BodyDef();
		// Set its world position
		goalBodyDef.position.set(new Vector2(x, y));

		// Create a playerBody from the defintion and add it to the world
		Body goalBody = world.createBody(goalBodyDef);

		// Create a polygon shape
		PolygonShape goalBox = new PolygonShape();
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		goalBox.setAsBox(width,height);
		// Create a fixture from our polygon shape and add it to our ground playerBody
		goalBody.createFixture(goalBox, 0.0f);
		// Clean up after ourselves
	}

	private void createGuard() {
        // First we create a playerBody definition
        guardBodyDef = new BodyDef();
        // We set our playerBody to dynamic, for something like ground which doesn't move we would set it to StaticBody
        guardBodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our playerBody's starting position in the world
        guardBodyDef.position.set(0, 50);

        // Create our playerBody in the world using our playerBody definition
        guardBody = world.createBody(guardBodyDef);
        guardBody.setLinearDamping(3f);


        // Create a circle shape and set its radius to 6
        CircleShape playerShape = new CircleShape();
//        playerShape.setAsBox(2, 2);
        playerShape.setRadius(3f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerShape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 10.0f; // Make it bounce a little bit

        // Create our fixture and attach it to the playerBody
        guardFix = guardBody.createFixture(fixtureDef);


        PolygonShape visionShape = new PolygonShape();

        Vector2[] vertices = new Vector2[8];
//        vertices[0] = new Vector2(0, 3);
//        vertices[1] = new Vector2(-25, 20);
//        vertices[2] = new Vector2(25, 20);
        vertices[0] = new Vector2(0,0);
        for (int i = 0; i < 7; i++) {
            float angle = (float) (i / 6.0 * 120 * DEGTORAD);
            float x = (float) (25f * Math.cos(angle));
            float y = (float) (25f * Math.sin(angle));
            vertices[i+1] = new Vector2(x,y);
        }
        visionShape.set(vertices);
//        visionShape.setRadius(3);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = visionShape;
        fixtureDef2.density = 0f;
        fixtureDef2.friction = 0f;
        fixtureDef2.isSensor = true;
        visionFix = guardBody.createFixture(fixtureDef2);


    }

    private void createPlayer() {
        // First we create a playerBody definition
        playerBodyDef = new BodyDef();
        // We set our playerBody to dynamic, for something like ground which doesn't move we would set it to StaticBody
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.fixedRotation = true;
        // Set our playerBody's starting position in the world
        playerBodyDef.position.set(0, -50);


        // Create our playerBody in the world using our playerBody definition
        playerBody = world.createBody(playerBodyDef);
        // Create a circle shape and set its radius to 6
        CircleShape playerShape = new CircleShape();
//        playerShape.setAsBox(2, 2);
        playerShape.setRadius(3f);
		playerBody.setLinearVelocity(1000.0f, -1000.0f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerShape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.7f; // Make it bounce a little bit

        // Create our fixture and attach it to the playerBody
        playerFix = playerBody.createFixture(fixtureDef);
    }

    private void createPost() {
        // First we create a playerBody definition
        BodyDef postBodyDef = new BodyDef();
        // We set our playerBody to dynamic, for something like ground which doesn't move we would set it to StaticBody
        postBodyDef.type = BodyDef.BodyType.StaticBody;
        postBodyDef.fixedRotation = true;
        // Set our playerBody's starting position in the world
        postBodyDef.position.set(70, -50);

        // Create our playerBody in the world using our playerBody definition
        postBody = world.createBody(postBodyDef);
        postBody.setLinearDamping(3f);
        // Create a circle shape and set its radius to 6
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(.01f, .01f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerShape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f; // Make it bounce a little bit
        fixtureDef.isSensor = true;

        // Create our fixture and attach it to the playerBody
        postFix = postBody.createFixture(fixtureDef);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        Gdx.gl.glClearColor(1, 0, 0, 1);

        debugRenderer.render(world, camera.combined);

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerBody.applyLinearImpulse(-2.5f, 0, playerBody.getPosition().x, playerBody.getPosition().y, true);
        }

        // apply right impulse, but only if max velocity is not reached yet
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerBody.applyLinearImpulse(2.5f, 0, playerBody.getPosition().x, playerBody.getPosition().y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerBody.applyLinearImpulse(0, 2.5f, playerBody.getPosition().x, playerBody.getPosition().y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerBody.applyLinearImpulse(0, -2.5f, playerBody.getPosition().x, playerBody.getPosition().y, true);
        }
		playerBody.getLinearVelocity().crs(playerBody.getLinearVelocity());
        world.step(1 / 60f, 6, 2);
    }
}
