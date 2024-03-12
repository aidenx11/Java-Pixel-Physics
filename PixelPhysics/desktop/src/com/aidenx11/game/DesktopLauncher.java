package com.aidenx11.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1024, 768);
		config.setForegroundFPS(30);
		config.setTitle("JavaPixelPhysics");
		config.useVsync(true);
		new Lwjgl3Application(new pixelPhysicsGame(), config);
	}
}
