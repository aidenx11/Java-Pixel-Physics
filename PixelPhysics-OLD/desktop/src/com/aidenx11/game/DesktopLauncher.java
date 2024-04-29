package com.aidenx11.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1280, 720);
		config.setForegroundFPS(60);
		config.setTitle("JavaPixelPhysics");
		config.useVsync(false);
		config.setWindowSizeLimits(130, 590, 9999, 9999);
		new Lwjgl3Application(new PixelPhysicsGame(), config);
	}
}
