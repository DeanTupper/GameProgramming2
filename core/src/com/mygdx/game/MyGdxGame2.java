package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame2 extends ApplicationAdapter {
	private static float FRUSTUM_WIDHT;
	private static float FRUSTUM_HEIGHT;
	private static float FRUSTUM_THIRD_WIDTH;
	SpriteBatch batcher;
	Texture img;
	private OrthographicCamera cam;
	private OrthographicCamera cam2;
	private OrthographicCamera guiCamFirst;
	private OrthographicCamera guiCamSecond;
	private OrthographicCamera guiCamThird;

	@Override
	public void create() {
		FRUSTUM_WIDHT = Gdx.graphics.getWidth() / 2.5f;
		FRUSTUM_HEIGHT = Gdx.graphics.getHeight();
		FRUSTUM_THIRD_WIDTH = Gdx.graphics.getWidth() - FRUSTUM_WIDHT * 2;

		batcher = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0, 0, 0);

		cam2 = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam2.position.set(0, 0, 0);


//		float w = Gdx.graphics.getWidth();
//		float h = Gdx.graphics.getHeight();
//		cam = new OrthographicCamera(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
//
//		cam.position.set(0, 0, 0);
//		cam.update();
////		cam.rotate(90f);
//
//
//		cam2 = new OrthographicCamera(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() );
//
//		cam2.position.set(Gdx.graphics.getWidth()/2, 0, 0);
//		cam2.update();
//		cam2.rotate(180f);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Bottom left
		Gdx.gl.glViewport(0, 0, 200, Gdx.graphics.getHeight());
		renderCamera(cam);

		// Top left
		Gdx.gl.glViewport(0, 200, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		renderCamera(cam2);
	}

	private void renderCamera(OrthographicCamera camera) {
		Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
		img.bind();
		batcher.begin();
		batcher.draw(img, 100, 100);
		batcher.setProjectionMatrix(camera.combined);
		batcher.end();
	}
}

























//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//		/*Left Half*/
//		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
//		batch.setProjectionMatrix(cam.combined);
//		cam.update();
//
//
//		/*Right Half*/
////		Gdx.gl.glViewport( Gdx.graphics.getWidth()/2,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight());
//		//Set up camera again with other viewport in mind
////		batch.setProjectionMatrix(cam2.combined);
////		cam2.update();
//
//
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
//	}
//}
