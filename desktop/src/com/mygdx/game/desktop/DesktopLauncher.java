package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.DeanTestGame;
import com.mygdx.game.GameWorld;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Viewports;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = DeanTestGame.DEFAULT_APP_HEIGHT;
		config.width  = DeanTestGame.DEFAULT_APP_WIDTH;
		config.title = "Crash Bash";
		new LwjglApplication(new DeanTestGame(), config);
	}
}
