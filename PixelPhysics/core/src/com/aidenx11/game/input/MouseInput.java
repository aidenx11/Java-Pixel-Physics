package com.aidenx11.game.input;

import java.util.ArrayList;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.elements.Dirt;
import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.elements.Empty;
import com.aidenx11.game.elements.Fire;
import com.aidenx11.game.elements.Leaf;
import com.aidenx11.game.elements.Sand;
import com.aidenx11.game.elements.Smoke;
import com.aidenx11.game.elements.Water;
import com.aidenx11.game.elements.Wood;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

/**
 * Class to handle detection of mouse input and drawing of elements in the
 * matrix
 * 
 * @author Aiden Schroeder
 */
public class MouseInput {

	/** Camera to base the mouse on */
	private OrthographicCamera camera;
	/** Matrix to be modified by the mouse */
	private CellularMatrix matrix;
	/** Pixel size modifier of this game */
	private static int pixelSizeModifier = pixelPhysicsGame.pixelSizeModifier;

	/** Position of the mouse in 3D space. Z is always zero */
	public Vector3 mousePos = new Vector3();

	public Vector3 lastMousePos = new Vector3(1, 1, 0);

	/** Number of rows in the matrix */
	private static int rows = pixelPhysicsGame.rows;
	/** Number of columns in the matrix */
	private static int columns = pixelPhysicsGame.columns;
	/** Size of brush (radius of circle) */
	private int brushSize;
	/** Size of cursor */
	private int cursorSize;

	/** Element type being drawn by the mouse */
	private ElementTypes elementType;

	/** Whether or not the color of the sand should be random */
	private boolean randomizeColor = false;

	private BrushTypes brushType;

	public enum BrushTypes {
		CIRCLE, SQUARE
	}

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
	 * Returns the current mouse position as a Vector3
	 * 
	 * @return the current mouse position
	 */
	public Vector3 getMousePos() {
		return mousePos;
	}

	/**
	 * Returns the current brush size
	 * 
	 * @return the current brush size
	 */
	public int getBrushSize() {
		return brushSize;
	}

	/**
	 * Sets the brush size
	 * 
	 * @param brushSize size to set
	 */
	public void setBrushSize(int brushSize) {
		this.brushSize = brushSize;
	}

	/**
	 * Returns the cursor size
	 * 
	 * @return the cursor size
	 */
	public int getCursorSize() {
		return cursorSize;
	}

	/**
	 * Sets the cursor size
	 * 
	 * @param cursorSize size to set
	 */
	public void setCursorSize(int cursorSize) {
		this.cursorSize = cursorSize;
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
		if (getBrushType() == BrushTypes.CIRCLE) {
			sr.circle(Gdx.input.getX(), pixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY(),
					getCursorSize() * pixelSizeModifier, 100);
		} else if (getBrushType() == BrushTypes.SQUARE) {
			sr.rect(Gdx.input.getX() - getCursorSize() * 2 + getCursorSize() / 2,
					pixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY() - getCursorSize() * 2 + getCursorSize() / 2 - 5,
					getCursorSize() * pixelSizeModifier, getCursorSize() * pixelSizeModifier);
		}
		sr.end();
	}

	/**
	 * Detects the input of the mouse and sets the matrix values corresponding to
	 * the mouse location and elementType.
	 */
	public void detectInput() {

		if (Gdx.input.isTouched()) {
			lastMousePos.set(mousePos);
			mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(mousePos);

			ArrayList<int[]> points = matrix.traverseMatrix(lastMousePos.x, lastMousePos.y, mousePos.x, mousePos.y);
			if (points.isEmpty()) {
				points.add(
						new int[] { (int) (mousePos.y / pixelSizeModifier), (int) (mousePos.x / pixelSizeModifier) });
			}
			for (int[] point : points) {
				if (matrix.isWithinBounds(point[0], point[1])) {
					float probability;
					switch (elementType) {
					case SAND:
					case EMPTY:
					case WOOD:
					case DIRT:
						probability = 1;
						break;
					case LEAF:
						probability = 0.2f;
						break;
					case WATER:
					case FIRE:
						probability = 0.4f;
						break;
					default:
						probability = 1;
						break;
					}

					if (getBrushType() == BrushTypes.CIRCLE) {
						drawCircle(point[0], point[1], getBrushSize(), elementType, probability);
					} else if (getBrushType() == BrushTypes.SQUARE) {
						drawSquare(point[0], point[1], getBrushSize(), probability);
					}

				}

			}
		}
	}

	/**
	 * Draws a circle to the matrix of the given element, p% of the time
	 * 
	 * @param row    row of the center of the circle
	 * @param column column of the center of the circle
	 * @param radius radius of the circle
	 * @param type   element type to be drawn
	 * @param p      probability of each pixel in the square being drawn
	 */
	public void drawCircle(int row, int column, int radius, ElementTypes type, double p) {

		if (radius == 1) {
			drawSquare(row, column, 1, p);
			return;
		}

		// Define bounding box
		int top = (int) (Math.ceil(row + radius) < 0 ? 0 : Math.ceil(row + radius));
		int bottom = (int) (Math.floor(row - radius) < 0 ? 0 : Math.floor(row - radius));
		int left = (int) (Math.floor(column - radius) < 0 ? 0 : Math.floor(column - radius));
		int right = (int) (Math.ceil(column + radius) < 0 ? 0 : Math.ceil(column + radius));

		for (int rowCount = bottom; rowCount <= top; rowCount++) {
			for (int colCount = left; colCount <= right; colCount++) {
				if (insideCircle(row, column, radius, rowCount, colCount)) {
					if (!matrix.isWithinBounds(rowCount, colCount)) {
						continue;
					}
					switch (type) {
					case SAND:
						if (Math.random() < p && !(matrix.getElement(rowCount, colCount) instanceof Sand)) {
							matrix.setElement(new Sand(rowCount, colCount));
						}
						break;
					case EMPTY:
						matrix.setElement(new Empty(rowCount, colCount));
						break;
					case WOOD:
						if (!(matrix.getElement(rowCount, colCount) instanceof Wood) && Math.random() < p) {
							matrix.setElement(new Wood(rowCount, colCount));
						}
						break;
					case SMOKE:
						if (!(matrix.getElement(rowCount, colCount) instanceof Smoke) && Math.random() < p) {
							matrix.setElement(new Smoke(rowCount, colCount));
						}
						break;
					case FIRE:
						if (!(matrix.getElement(rowCount, colCount) instanceof Fire) && Math.random() < p) {
							matrix.setElement(new Fire(rowCount, colCount));
						}
						break;
					case WATER:
						if (!(matrix.getElement(rowCount, colCount) instanceof Water) && Math.random() < p) {
							matrix.setElement(new Water(rowCount, colCount));
						}
						break;
					case LEAF:
						if (!(matrix.getElement(rowCount, colCount) instanceof Leaf) && Math.random() < p) {
							matrix.setElement(new Leaf(rowCount, colCount));
						}
						break;
					case DIRT:
						if (!(matrix.getElement(rowCount, colCount) instanceof Dirt) && Math.random() < p) {
							matrix.setElement(new Dirt(rowCount, colCount));
						}
					default:
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
	 * Draws a square of the given element type to the matrix.
	 * 
	 * @param row    row of the center of the square
	 * @param column column of the center of the square
	 * @param width  width of the square
	 * @param p      probability of each pixel in the square being drawn
	 * @param type   element type to be drawn
	 */
	public void drawSquare(int row, int column, int width, double p) {
		
		int difference = width / 2 < 1 ? 1 : width / 2;

		for (int rowCount = row - difference; rowCount < row + difference; rowCount++) {
			for (int colCount = column - difference; colCount < column + difference; colCount++) {

				if (!matrix.isWithinBounds(rowCount, colCount)) {
					continue;
				}

				switch (this.elementType) {
				case SAND:
					if (!(matrix.getElement(rowCount, colCount) instanceof Sand)) {
						matrix.setElement(new Sand(rowCount, colCount));
					}
					break;
				case EMPTY:
					if (!(matrix.getElement(rowCount, colCount) instanceof Empty)) {
						matrix.setElement(new Empty(rowCount, colCount));
					}
					break;
				case WOOD:
					if (!(matrix.getElement(rowCount, colCount) instanceof Wood)) {
						matrix.setElement(new Wood(rowCount, colCount));
					}
					break;
				case FIRE:
					if (!(matrix.getElement(rowCount, colCount) instanceof Fire)) {
						matrix.setElement(new Fire(rowCount, colCount));
					}
					break;
				case LEAF:
					if (!(matrix.getElement(rowCount, colCount) instanceof Leaf)) {
						matrix.setElement(new Leaf(rowCount, colCount));
					}
					break;
				case WATER:
					if (!(matrix.getElement(rowCount, colCount) instanceof Water)) {
						matrix.setElement(new Water(rowCount, colCount));
					}
					break;
				case DIRT:
					if (!(matrix.getElement(rowCount, colCount) instanceof Dirt)) {
						matrix.setElement(new Dirt(rowCount, colCount));
					}
				case SMOKE:
					break;
				case STEAM:
					break;
				case WET_SAND:
					break;
				default:
					break;
				}
			}
		}
	}

	public BrushTypes getBrushType() {
		return brushType;
	}

	public void setBrushType(BrushTypes brushType) {
		this.brushType = brushType;
	}

}