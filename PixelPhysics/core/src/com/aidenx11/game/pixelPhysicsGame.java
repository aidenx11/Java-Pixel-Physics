package com.aidenx11.game;

import com.aidenx11.game.input.MouseInput;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class pixelPhysicsGame extends ApplicationAdapter {

	public static int screenWidth = 1024; // MUST equal window size in desktop launcher
	public static int screenHeight = 768; // MUST equal window size in desktop launcher
	public static int pixelSizeModifier = 10;
	public CellularMatrix matrix;

	public static int rows;
	public static int columns;

	public ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	private MouseInput mouse;

	@Override
	public void create() {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth, screenHeight);
		camera.zoom = 1f;

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setAutoShapeType(true);

		rows = (int) Math.ceil(screenHeight / pixelSizeModifier) + 1;
		columns = (int) Math.ceil(screenWidth / pixelSizeModifier) + 1;

		matrix = new CellularMatrix(rows, columns, pixelSizeModifier);

		mouse = new MouseInput(matrix, camera);

	}

	@Override
	public void render() {
		
		// Set blue background
		ScreenUtils.clear(135/255f, 206/255f, 235/255f, 1);

		// Detects mouse input and sets pixel if it is in bounds
		mouse.detectInput();
		
		// Perform sand settling logic
		matrix.updateFrame(shapeRenderer);

	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}

}
