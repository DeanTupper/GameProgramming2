package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Viewports;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Viewports.DEFAULT_APP_HEIGHT;
		config.width  = Viewports.DEFAULT_APP_WIDTH;
		config.title = "Crash Bash";
		new LwjglApplication(new Viewports(), config);
	}
}
