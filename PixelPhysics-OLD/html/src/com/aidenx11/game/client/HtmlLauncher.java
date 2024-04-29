package com.aidenx11.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.aidenx11.game.PixelPhysicsGame;

public class HtmlLauncher extends GwtApplication {

	@Override
	public GwtApplicationConfiguration getConfig() {
		// Resizable application, uses available space in browser
		GwtApplicationConfiguration config = new GwtApplicationConfiguration(false);
		config.padHorizontal = 25;
		config.padVertical = 25;
		return config;
		// Fixed size application:
//                return new GwtApplicationConfiguration(1280, 720);
	}

	@Override
	public ApplicationListener createApplicationListener() {
		return new PixelPhysicsGame();
	}
}