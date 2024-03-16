package com.aidenx11.game;

import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.input.MouseInput;
import com.aidenx11.game.ui.ButtonStage;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class pixelPhysicsGame extends ApplicationAdapter {

	public static final int SCREEN_WIDTH = 1024; // MUST equal window size in desktop launcher
	public static final int SCREEN_HEIGHT = 768; // MUST equal window size in desktop launcher
	public static int pixelSizeModifier = 6;
	public CellularMatrix matrix;

	public static int rows;
	public static int columns;

	public ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	private Viewport viewport;
	private MouseInput mouse;
	private ButtonStage buttonStage;

	@Override
	public void create() {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		camera.update();

		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setAutoShapeType(true);

		rows = (int) Math.ceil(SCREEN_HEIGHT / pixelSizeModifier) + 1;
		columns = (int) Math.ceil(SCREEN_WIDTH / pixelSizeModifier) + 1;

		matrix = new CellularMatrix(rows, columns, pixelSizeModifier);

		mouse = new MouseInput(matrix, camera);
		mouse.setElementType(ElementTypes.SAND);

		buttonStage = new ButtonStage(viewport, mouse, matrix);

		Gdx.input.setInputProcessor(buttonStage.getStage());

	}

	@Override
	public void render() {

		
			// Set blue background
			ScreenUtils.clear(135 / 255f, 206 / 255f, 235 / 255f, 1);

			// Draw the buttons to the screen
			buttonStage.getStage().draw();

			// Detects mouse input and sets pixel if it is in bounds
			mouse.detectInput();

			// Perform sand settling logic
			matrix.draw(shapeRenderer);
			matrix.updateFrame(shapeRenderer);

			mouse.drawCursor(shapeRenderer, 10);
		

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.setToOrtho(false, width, height);
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}

}
