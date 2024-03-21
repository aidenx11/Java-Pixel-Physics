package com.aidenx11.game.input;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.ColorManager;
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

	/** Camera to base the mouse on */
	private OrthographicCamera camera;
	/** Matrix to be modified by the mouse */
	private CellularMatrix matrix;
	/** Pixel size modifier of this game */
	private static int pixelSizeModifier = pixelPhysicsGame.pixelSizeModifier;

	/** Position of the mouse in 3D space. Z is always zero */
	Vector3 mousePos = new Vector3();

	/** Number of rows in the matrix */
	private static int rows = pixelPhysicsGame.rows;
	/** Number of columns in the matrix */
	private static int columns = pixelPhysicsGame.columns;
	/** Size of brush (radius of circle) */
	private int brushSize;
	/** Size of cursor */
	private int cursorSize;
	/** Number of rows the ui takes up, starting from the top of the screen */
	private int uiRows;

	/** Element type being drawn by the mouse */
	private ElementTypes elementType;

	/** Whether or not the color of the sand should be random */
	private boolean randomizeColor = false;

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
	 * Sets the element type the mouse is drawing to the matrix
	 * 
	 * @param type type of element to be drawn
	 */
	public void setElementType(ElementTypes type) {
		this.elementType = type;
	}

	/**
	 * Draws a square of the given element type to the matrix.
	 * 
	 * @param row    row of the center of the square
	 * @param column column of the center of the square
	 * @param width  width of the square
	 * @param p      probability of each pixel in the square being drawn
	 * @param type   element type to be drawn
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

	/**
	 * Draws a circle to the matrix, similar to drawSquare.
	 * 
	 * @param row    row of the center of the circle
	 * @param column column of the center of the circle
	 * @param radius radius of the circle
	 * @param type   element type to be drawn
	 * @param p      probability of each pixel in the square being drawn
	 */
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
							if (!isRandomizeColor()) {
								matrix.setElement(new Sand(rowCount, colCount));
							} else {
								matrix.setElement(new Sand(rowCount, colCount, false, true));
							}
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

	// Private method to calculate if a matrix index is within the circle
	private boolean insideCircle(int centerRow, int centerCol, int radius, int cellRow, int cellCol) {
		double dx = centerCol - cellCol;
		double dy = centerRow - cellRow;
		double distanceSquared = dx * dx + dy * dy;
		return distanceSquared <= radius * radius;
	}

	/**
	 * Returns the current mouse position as a Vector3
	 * 
	 * @return the current mouse position
	 */
	public Vector3 getMousePos() {
		return mousePos;
	}

	/**
	 * Returns the ui rows of this mouse
	 * 
	 * @return the ui rows of this mouse
	 */
	public int getUiRows() {
		return uiRows;
	}

	/**
	 * Set the ui rows of this mouse
	 * 
	 * @param uiRows uiRows to set
	 */
	public void setUiRows(int uiRows) {
		this.uiRows = uiRows;
	}

	/**
	 * Returns whether or not the color should be randomized for sand particles
	 * 
	 * @return whether or not the color should be randomized for sand particles
	 */
	public boolean isRandomizeColor() {
		return randomizeColor;
	}

	/**
	 * Sets whether or not the color should be randomized
	 * 
	 * @param randomizeColor whether or not to randomize
	 */
	public void setRandomizeColor(boolean randomizeColor) {
		this.randomizeColor = randomizeColor;
	}

	/**
	 * Draws the cursor around the mouse on the screen
	 * 
	 * @param sr     shapeRenderer of the viewport
	 * @param radius radius of the circle
	 */
	public void drawCursor(ShapeRenderer sr) {
		sr.begin();
		sr.setColor(Color.WHITE);
		sr.circle(Gdx.input.getX(), pixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY(),
				getCursorSize() * pixelSizeModifier, 100);
		sr.end();
	}

	/**
	 * Detects the input of the mouse and sets the matrix values corresponding to
	 * the mouse location and elementType.
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
					drawCircle(touchedRow, touchedCol, getBrushSize(), elementType, 0.5);
					break;
				case EMPTY:
					drawCircle(touchedRow, touchedCol, getBrushSize(), elementType, 1);
					break;
				case WOOD:
					drawCircle(touchedRow, touchedCol, getBrushSize(), elementType, 1);
					break;
				}

			}
		}
	}

	public int getBrushSize() {
		return brushSize;
	}

	public void setBrushSize(int brushSize) {
		this.brushSize = brushSize;
	}

	public int getCursorSize() {
		return cursorSize;
	}

	public void setCursorSize(int cursorSize) {
		this.cursorSize = cursorSize;
	}

}
