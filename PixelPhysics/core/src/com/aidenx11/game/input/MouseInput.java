package com.aidenx11.game.input;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.elements.Sand;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class MouseInput {

	private OrthographicCamera camera;
	private CellularMatrix matrix;
	private static int pixelSizeModifier = pixelPhysicsGame.pixelSizeModifier;
	Vector3 mousePos = new Vector3();
	private static int rows = pixelPhysicsGame.rows;
	private static int columns = pixelPhysicsGame.columns;

	/**
	 * Constructs the MouseInput with given matrix and camera
	 * 
	 * @param matrix matrix input is inside
	 * @param camera camera input should be in
	 */
	public MouseInput(CellularMatrix matrix, OrthographicCamera camera) {
		this.camera = camera;
		this.matrix = matrix;
	}

	/**
	 * Detects the input of the mouse and sets the matrix values corresponding to
	 * the mouse location.
	 */
	public void detectInput() {

		if (Gdx.input.isTouched()) {
			mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(mousePos);
			int touchedRow = (int) Math.floor(mousePos.y / pixelSizeModifier);
			int touchedCol = (int) Math.floor(mousePos.x / pixelSizeModifier);
			if (touchedRow >= 0 && touchedRow < rows && touchedCol >= 0 && touchedCol < columns) {
				if (matrix.isEmpty(touchedRow, touchedCol)) {
					matrix.setElement(new Sand(touchedRow, touchedCol));
				}
			}
		}
	}

}
