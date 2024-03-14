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
	 * 
	 * @param element
	 * @param row
	 * @param column
	 * @param radius
	 * @param p
	 */
	public void drawSquare(int row, int column, int width, double p) {
		for (int rowCount = row - width / 2; rowCount < row + width / 2; rowCount++) {
			for (int colCount = column - width / 2; colCount < column + width / 2; colCount++) {
				if (rowCount < 0 || rowCount >= rows || colCount < 0 || colCount >= columns) {
					break;
				}
				if (Math.random() < p) {
					matrix.setElement(new Sand(rowCount, colCount));
				}
			}
		}
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
					// matrix.setElement(new Sand(touchedRow, touchedCol));
					drawSquare(touchedRow, touchedCol, 5, 0.8);
				}
			}
		}
	}

}
