package com.aidenx11.game.input;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.elements.Empty;
import com.aidenx11.game.elements.Sand;
import com.aidenx11.game.elements.Wood;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class MouseInput {

	private OrthographicCamera camera;
	private CellularMatrix matrix;
	private static int pixelSizeModifier = pixelPhysicsGame.pixelSizeModifier;
	Vector3 mousePos = new Vector3();
	private static int rows = pixelPhysicsGame.rows;
	private static int columns = pixelPhysicsGame.columns;
	private int uiRows;

	private ElementTypes elementType;

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

	public void setElementType(ElementTypes type) {
		this.elementType = type;
	}

	/**
	 * 
	 * @param element
	 * @param row
	 * @param column
	 * @param radius
	 * @param p
	 */
	public void drawSquare(int row, int column, int width, double p, ElementTypes type) {
		for (int rowCount = row - width / 2; rowCount < row + width / 2; rowCount++) {
			for (int colCount = column - width / 2; colCount < column + width / 2; colCount++) {
				if (rowCount < 0 || rowCount >= rows || colCount < 0 || colCount >= columns) {
					break;
				}

				switch (type) {
				case SAND:
					if (Math.random() < p && matrix.isEmpty(rowCount, colCount)) {
						matrix.setElement(new Sand(rowCount, colCount));
					}
					break;
				case EMPTY:
					matrix.setElement(new Empty(rowCount, colCount));
					break;
				case WOOD:
					matrix.setElement(new Wood(rowCount, colCount));
					break;
				}
			}
		}
	}

	public void drawCircle(int row, int column, int radius, ElementTypes type, double p) {
		// Define bounding box
		int top = (int) Math.ceil(row + radius);
		int bottom = (int) Math.floor(row - radius);
		int left = (int) Math.floor(column - radius);
		int right = (int) Math.ceil(column + radius);

		for (int rowCount = bottom; rowCount <= top; rowCount++) {
			for (int colCount = left; colCount <= right; colCount++) {
				if (insideCircle(row, column, radius, rowCount, colCount)) {
					if (rowCount < 0 || rowCount >= rows || colCount < 0 || colCount >= columns) {
						break;
					}
					switch (type) {
					case SAND:
						if (Math.random() < p && matrix.isEmpty(rowCount, colCount)) {
							matrix.setElement(new Sand(rowCount, colCount));
						}
						break;
					case EMPTY:
						matrix.setElement(new Empty(rowCount, colCount));
						break;
					case WOOD:
						if (matrix.isEmpty(rowCount, colCount)) {
							matrix.setElement(new Wood(rowCount, colCount));
						}
						break;
					}
				}
			}
		}
	}

	private boolean insideCircle(int centerRow, int centerCol, int radius, int cellRow, int cellCol) {
		double dx = centerCol - cellCol;
		double dy = centerRow - cellRow;
		double distanceSquared = dx * dx + dy * dy;
		return distanceSquared <= radius * radius;
	}

	/**
	 * 
	 * @return
	 */
	public Vector3 getMousePos() {
		return mousePos;
	}

	public int getUiRows() {
		return uiRows;
	}

	public void setUiRows(int uiRows) {
		this.uiRows = uiRows;
	}

	/**
	 * 
	 * @param sr
	 * @param radius
	 */
	public void drawCursor(ShapeRenderer sr, int radius) {
		sr.begin();
		sr.setColor(Color.WHITE);
		sr.circle(Gdx.input.getX(), pixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY(), radius, 100);
		sr.end();
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
			if (touchedRow < getUiRows() && touchedRow >= 0 && touchedCol >= 0 && touchedCol < columns) {
				switch (elementType) {
				case SAND:
					// matrix.setElement(new Sand(touchedRow, touchedCol));
					// drawSquare(touchedRow, touchedCol, 5, 0.8, elementType);
					drawCircle(touchedRow, touchedCol, 3, elementType, 0.5);
					break;
				case EMPTY:
					drawCircle(touchedRow, touchedCol, 3, elementType, 1);
					break;
				case WOOD:
					drawCircle(touchedRow, touchedCol, 3, elementType, 1);
					break;
				}

			}
		}
	}

}
