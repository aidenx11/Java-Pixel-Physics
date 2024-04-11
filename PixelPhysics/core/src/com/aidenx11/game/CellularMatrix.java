package com.aidenx11.game;

import java.util.ArrayList;

import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.Empty;
import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.elements.immovable.Fire;
import com.aidenx11.game.elements.immovable.Leaf;
import com.aidenx11.game.elements.immovable.Steel;
import com.aidenx11.game.elements.immovable.Stone;
import com.aidenx11.game.elements.immovable.Wood;
import com.aidenx11.game.elements.movable.gas.Smoke;
import com.aidenx11.game.elements.movable.gas.Steam;
import com.aidenx11.game.elements.movable.liquid.Lava;
import com.aidenx11.game.elements.movable.liquid.Water;
import com.aidenx11.game.elements.movable.movable_solid.Dirt;
import com.aidenx11.game.elements.movable.movable_solid.Obsidian;
import com.aidenx11.game.elements.movable.movable_solid.Rust;
import com.aidenx11.game.elements.movable.movable_solid.Sand;
import com.aidenx11.game.elements.movable.movable_solid.WetDirt;
import com.aidenx11.game.elements.movable.movable_solid.WetSand;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

/**
 * Manages the matrix that contains all elements to be drawn to the screen.
 * Keeps track of its number of rows and columns, the pixelSizeModifier, and the
 * matrix itself which is a 2D array of elements with size corresponding to the
 * number of rows and columns.
 * 
 * @author Aiden Schroeder
 */
public class CellularMatrix {

	/** Number of rows in the matrix */
	public static int rows;

	/** Number of columns in the matrix */
	public static int columns;

	/** Pixel size modifier of the matrix */
	public static int pixelSizeModifier;

	/** The matrix itself. Stores elements */
	private Array<Array<Element>> matrix;

	/** Keeps track of the direction to update each row in updateFrame() */
	private boolean direction = true;

	/**
	 * Generates a matrix with the given rows and columns, with a pixel size
	 * corresponding to pixelSizeModifier.
	 * 
	 * @param rows              number of rows
	 * @param columns           number of columns
	 * @param pixelSizeModifier size of the pixels
	 */
	public CellularMatrix(int rows, int columns, int pixelSizeModifier) {
		CellularMatrix.rows = rows;
		CellularMatrix.columns = columns;
		CellularMatrix.pixelSizeModifier = pixelSizeModifier;
		this.matrix = generateMatrix();
	}

	/**
	 * Generates a matrix based on the rows and columns of this matrix and
	 * initializes all values to empty elements.
	 * 
	 * @return the empty matrix
	 */
	private Array<Array<Element>> generateMatrix() {

		Array<Array<Element>> outerArray = new Array<>(true, rows);
		for (int y = 0; y < rows; y++) {
			Array<Element> innerArr = new Array<>(true, columns);
			for (int x = 0; x < columns; x++) {
				innerArr.add(new Empty(y, x));
			}
			outerArray.add(innerArr);
		}
		return outerArray;
	}

	/**
	 * Returns the given row of the matrix
	 * 
	 * @param row row to return
	 * @return returns the given row
	 */
	public Array<Element> getRow(int row) {
		return matrix.get(row);
	}

	/**
	 * Returns the current matrix
	 * 
	 * @return the current matrix
	 */
	public Array<Array<Element>> getMatrix() {
		return matrix;
	}

	/**
	 * Clears the current matrix
	 */
	public void clear() {
		this.matrix = generateMatrix();
	}

	/**
	 * Sets the given element to empty
	 * 
	 * @param element element to set empty
	 */
	public void clearElement(Element element) {
		this.setElement(new Empty(element.getRow(), element.getColumn()));
	}

	/**
	 * Swaps 2 given elements in the matrix by using their corresponding row and
	 * column coordinates.
	 * 
	 * @param element1 first element to swap
	 * @param element2 second element to swap
	 */
	public void swap(Element element1, Element element2) {
		int[] tempLocation = new int[] { element1.getRow(), element1.getColumn() };
		element1.setRow(element2.getRow());
		element1.setColumn(element2.getColumn());
		element2.setRow(tempLocation[0]);
		element2.setColumn(tempLocation[1]);

		this.setElement(element1);
		this.setElement(element2);
	}

	/**
	 * Retrieves an element from the matrix based on its row and column.
	 * 
	 * @param row    row of the element being retrieved
	 * @param column column of the element being retrieved
	 * @return
	 */
	public Element getElement(int row, int column) {

		if (!isWithinBounds(row, column)) {
			return null;
		}

		return getRow(row).get(column);
	}

	/**
	 * Sets the given element at the element's corresponding row and column.
	 * 
	 * @param element element to set in the matrix
	 */
	public void setElement(Element element) {
		int row = element.getRow();
		int column = element.getColumn();

		if (isWithinBounds(row, column)) {
			Array<Element> rowArray = matrix.get(row);
			rowArray.set(column, element);
			matrix.set(row, rowArray);
		}
	}

	/**
	 * Draws the current matrix to the screen
	 * 
	 * @param shapeRenderer shape renderer that draws to viewport
	 */
	public void draw(ShapeRenderer shapeRenderer) {

		shapeRenderer.begin(ShapeType.Filled);
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				if (!(this.getElement(y, x) instanceof Empty) && isWithinBounds(y, x)) {
					Element thisElement = this.getElement(y, x);
					if (!(thisElement instanceof Empty)) {
						shapeRenderer.setColor(thisElement.getColor());
						shapeRenderer.rect(x * pixelSizeModifier, y * pixelSizeModifier, pixelSizeModifier,
								pixelSizeModifier);
					}
				}
			}
		}
		shapeRenderer.end();
	}

	/**
	 * Sets the given element to a new ElementType, based on the given type. Returns
	 * the newly created element.
	 * 
	 * @param element    element to change type of
	 * @param newElement element type to change the element to
	 * @return the newly changed element
	 */
	public Element setNewElement(Element element, ElementTypes newElement) {
		switch (newElement) {
		case EMPTY:
			this.setElement(new Empty(element.getRow(), element.getColumn()));
			break;
		case FIRE:
			this.setElement(new Fire(element.getRow(), element.getColumn()));
			break;
		case SAND:
			this.setElement(new Sand(element.getRow(), element.getColumn()));
			break;
		case SMOKE:
			this.setElement(new Smoke(element.getRow(), element.getColumn()));
			break;
		case WOOD:
			this.setElement(new Wood(element.getRow(), element.getColumn()));
			break;
		case WATER:
			this.setElement(new Water(element.getRow(), element.getColumn()));
			break;
		case STEAM:
			this.setElement(new Steam(element.getRow(), element.getColumn()));
			break;
		case WET_SAND:
			this.setElement(new WetSand(element.getRow(), element.getColumn()));
			break;
		case LEAF:
			this.setElement(new Leaf(element.getRow(), element.getColumn()));
			break;
		case DIRT:
			this.setElement(new Dirt(element.getRow(), element.getColumn()));
			break;
		case WET_DIRT:
			this.setElement(new WetDirt(element.getRow(), element.getColumn()));
			break;
		case STONE:
			this.setElement(new Stone(element.getRow(), element.getColumn()));
			break;
		case LAVA:
			this.setElement(new Lava(element.getRow(), element.getColumn()));
			break;
		case OBSIDIAN:
			this.setElement(new Obsidian(element.getRow(), element.getColumn()));
			break;
		case STEEL:
			this.setElement(new Steel(element.getRow(), element.getColumn()));
			break;
		case RUST:
			this.setElement(new Rust(element.getRow(), element.getColumn()));
			break;
		default:
			break;
		}
		return this.getElement(element.getRow(), element.getColumn());
	}

	/**
	 * Updates the frame. Updates all positions of all elements in the matrix based
	 * on their type using the element's update() method, and chooses the vertical
	 * direction to update based on which way the element moves. The horizontal
	 * update direction is chosen randomly to create a more realistic simulation.
	 * 
	 * @param sr shape renderer that draws to the viewport
	 */
	public void updateFrame(ShapeRenderer sr) {

		direction = direction ? false : true;
		Element element;

		for (int y = rows - 1; y >= 0; y--) {
			if (direction) {
				for (int x = 0; x < columns; x++) {
					element = this.getElement(rows - 1 - y, x);
					if (element.movesDown()) {
						element.update();
					}
				}
			} else {
				for (int x = columns - 1; x >= 0; x--) {
					element = this.getElement(rows - 1 - y, x);
					if (element.movesDown()) {
						element.update();
					}
				}
			}
		}
		for (int y = 0; y < rows; y++) {
			if (direction) {
				for (int x = 0; x < columns; x++) {
					element = this.getElement(rows - 1 - y, x);
					if (!element.movesDown()) {
						element.update();
					}
				}
			} else {
				for (int x = columns - 1; x >= 0; x--) {
					element = this.getElement(rows - 1 - y, x);
					if (!element.movesDown()) {
						element.update();
					}
				}
			}
		}
	}

	public static boolean isWithinBounds(int row, int col) {
		return row >= 0 && row < rows && col >= 0 && col < columns;
	}

	public static ArrayList<int[]> traverseMatrix(float x1, float y1, float x2, float y2) {
		int col1 = (int) Math.floor(x1 / pixelSizeModifier);
		int row1 = (int) Math.floor(y1 / pixelSizeModifier);
		int col2 = (int) Math.floor(x2 / pixelSizeModifier);
		int row2 = (int) Math.floor(y2 / pixelSizeModifier);

		int xDifference = col2 - col1;
		int yDifference = row2 - row1;
		boolean xDifferenceLarger = Math.abs(xDifference) > Math.abs(yDifference);

		int xModifier = xDifference < 0 ? -1 : 1;
		int yModifier = yDifference < 0 ? -1 : 1;

		int upperBound = Math.max(Math.abs(xDifference), Math.abs(yDifference));
		int min = Math.min(Math.abs(xDifference), Math.abs(yDifference));
		float slope = (min == 0 || upperBound == 0) ? 0 : ((float) (min + 1) / (upperBound + 1));

		ArrayList<int[]> points = new ArrayList<int[]>();

		int smallerCount;
		for (int i = 1; i <= upperBound; i++) {
			smallerCount = (int) Math.floor(i * slope);
			int yIncrease, xIncrease;
			if (xDifferenceLarger) {
				xIncrease = i;
				yIncrease = smallerCount;
			} else {
				yIncrease = i;
				xIncrease = smallerCount;
			}
			int currentY = row1 + (yIncrease * yModifier);
			int currentX = col1 + (xIncrease * xModifier);
			if (isWithinBounds(currentY, currentX)) {
				points.add(new int[] { currentY, currentX });
			}
		}

		return points;
	}

	/**
	 * Gets the elements adjacent to the element and returns them in an array in the
	 * order [top left, top, top right, left, right, bottom left, bottom, bottom
	 * right]
	 * 
	 * @param element element to get the elements adjacent to
	 * @return the elements adjacent to the given element in an array of format [top
	 *         left, top, top right, left, right, bottom left, bottom, bottom right]
	 */
	public Element[] getAdjacentElements(Element element) {
		Element[] adjacentElements = new Element[8];

		adjacentElements[0] = this.getElement(element.getRow() + 1, element.getColumn() - 1);
		adjacentElements[1] = this.getElement(element.getRow() + 1, element.getColumn());
		adjacentElements[2] = this.getElement(element.getRow() + 1, element.getColumn() + 1);
		adjacentElements[3] = this.getElement(element.getRow(), element.getColumn() - 1);
		adjacentElements[4] = this.getElement(element.getRow(), element.getColumn() + 1);
		adjacentElements[5] = this.getElement(element.getRow() - 1, element.getColumn() - 1);
		adjacentElements[6] = this.getElement(element.getRow() - 1, element.getColumn());
		adjacentElements[7] = this.getElement(element.getRow() - 1, element.getColumn() + 1);

		return adjacentElements;
	}

}
