package com.aidenx11.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class pixelPhysicsGame extends ApplicationAdapter {

	public static int screenWidth = 1024;
	public static int screenHeight = 768;
	public static int pixelSizeModifier = 20;
	public CellularMatrix matrix;

	Vector3 mousePos = new Vector3();

	int rows, columns;

	ShapeRenderer shapeRenderer;
	OrthographicCamera camera;
	Viewport viewport;

	@Override
	public void create() {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth, screenHeight);
		camera.zoom = 1f;

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setAutoShapeType(true);

		int fittedHeight = screenHeight - (screenHeight % pixelSizeModifier);
		int fittedWidth = screenWidth - (screenWidth % pixelSizeModifier);

		rows = fittedHeight / pixelSizeModifier;
		columns = fittedWidth / pixelSizeModifier;

		matrix = new CellularMatrix(rows, columns, pixelSizeModifier);
		matrix.setValue(rows - 2, 20, 1);
	}

	@Override
	public void render() {

		// Draws the matrix
		matrix.draw(shapeRenderer);

		// Detects mouse input and sets pixel if it is in bounds
		if (Gdx.input.isTouched()) {
			mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(mousePos);
			int touchedRow = (int) Math.floor(mousePos.y / pixelSizeModifier);
			int touchedCol = (int) Math.floor(mousePos.x / pixelSizeModifier);
			if (touchedRow >= 0 && touchedRow < rows && touchedCol >= 0 && touchedCol < columns) {
				matrix.setValue(touchedRow, touchedCol, 1);
			}
		}

	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}
	
	
}
