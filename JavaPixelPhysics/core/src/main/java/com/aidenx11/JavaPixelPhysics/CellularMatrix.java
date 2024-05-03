package com.aidenx11.JavaPixelPhysics;

import com.aidenx11.JavaPixelPhysics.elements.Element;
import com.aidenx11.JavaPixelPhysics.elements.Empty;
import com.aidenx11.JavaPixelPhysics.elements.Void;
import com.aidenx11.JavaPixelPhysics.elements.Element.ElementTypes;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Fire;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Leaf;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Steel;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Stone;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Wood;
import com.aidenx11.JavaPixelPhysics.elements.movable.gas.Smoke;
import com.aidenx11.JavaPixelPhysics.elements.movable.gas.Steam;
import com.aidenx11.JavaPixelPhysics.elements.movable.liquid.Lava;
import com.aidenx11.JavaPixelPhysics.elements.movable.liquid.Water;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.Dirt;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.Obsidian;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.Rust;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.Sand;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.WetDirt;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.WetSand;
import com.badlogic.gdx.graphics.Color;

import space.earlygrey.shapedrawer.ShapeDrawer;

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

	private static Chunk[][] chunkMatrix;

	private static int chunkSize;

	/** The matrix itself. Stores elements */
	private Element[][] matrix;

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
	public CellularMatrix(int rows, int columns, int pixelSizeModifier, int chunkSize) {
		CellularMatrix.rows = rows;
		CellularMatrix.columns = columns;
		CellularMatrix.pixelSizeModifier = pixelSizeModifier;
		CellularMatrix.chunkSize = pixelSizeModifier * chunkSize;
		this.matrix = generateMatrix();

	}

	/**
	 * Generates a matrix based on the rows and columns of this matrix and
	 * initializes all values to empty elements.
	 * 
	 * @return the empty matrix
	 */
	private Element[][] generateMatrix() {

		resetChunks();

		Element[][] array = new Element[rows][columns];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				array[y][x] = new Empty(y, x);
			}
		}

		return array;
	}

	public static void resetChunks() {
		chunkMatrix = new Chunk[PixelPhysicsGame.SCREEN_HEIGHT / chunkSize
				+ chunkSize][(PixelPhysicsGame.SCREEN_WIDTH - PixelPhysicsGame.uiOffset) / chunkSize + chunkSize];

		for (int i = 0; i < chunkMatrix.length; i++) {
			for (int j = 0; j < chunkMatrix[i].length; j++) {
				chunkMatrix[i][j] = new Chunk();
			}
		}

	}

	public static void stepChunks() {
		for (int i = 0; i < chunkMatrix.length; i++) {
			for (int j = 0; j < chunkMatrix[i].length; j++) {
				chunkMatrix[i][j].stepChunk();
			}
		}
	}

	public static void drawChunks(ShapeDrawer sd) {
		for (int i = 0; i < chunkMatrix.length; i++) {
			for (int j = 0; j < chunkMatrix[i].length; j++) {
				if (chunkMatrix[i][j].activeThisFrame) {
					sd.setColor(Color.WHITE);
					sd.rectangle(j * chunkSize, i * chunkSize, chunkSize, chunkSize);
				} else if (chunkMatrix[i][j].activeNextFrame) {
					sd.setColor(Color.RED);
					sd.rectangle(j * chunkSize, i * chunkSize, chunkSize, chunkSize);
				}
			}
		}
	}

	public static void activateChunk(int row, int col) {
		boolean activateChunkLeft = false;
		boolean activateChunkRight = false;
		boolean activateChunkBelow = false;
		boolean activateChunkAbove = false;

//		System.out.println();
//
//		System.out.println("Row: " + row);
//		System.out.println("Col: " + col);

		int chunkRow = CellularMatrix.getChunkLocation(row, col)[0];
		int chunkCol = CellularMatrix.getChunkLocation(row, col)[1];

//		System.out.println("ChunkCol: " + chunkCol);
//
//		System.out.println();

		if (chunkRow != Math.floor((float) (row + 1) / chunkSize * pixelSizeModifier)) {
			activateChunkAbove = true;
		}
		if (chunkRow != Math.floor((float) (row - 1) / chunkSize * pixelSizeModifier)) {
			activateChunkBelow = true;
		}
		if (chunkCol != Math.floor((float) (col - 1) / chunkSize * pixelSizeModifier)) {
			activateChunkLeft = true;
		}
		if (chunkCol != Math.floor((float) (col + 1) / chunkSize * pixelSizeModifier)) {
			activateChunkRight = true;
		}

		chunkMatrix[chunkRow][chunkCol].activeNextFrame = true;
		chunkMatrix[chunkRow][chunkCol].activeInTwoFrames = true;

		if (chunkCol - 1 > 0 && activateChunkLeft) {
			chunkMatrix[chunkRow][chunkCol - 1].activeNextFrame = true;
			chunkMatrix[chunkRow][chunkCol - 1].activeInTwoFrames = true;
		}
		if (chunkCol + 1 < chunkMatrix[0].length && activateChunkRight) {
			chunkMatrix[chunkRow][chunkCol + 1].activeNextFrame = true;
			chunkMatrix[chunkRow][chunkCol + 1].activeInTwoFrames = true;
		}
		if (chunkRow + 1 < chunkMatrix.length && activateChunkAbove) {
			chunkMatrix[chunkRow + 1][chunkCol].activeNextFrame = true;
			chunkMatrix[chunkRow + 1][chunkCol].activeInTwoFrames = true;
		}
		if (chunkRow - 1 > 0 && activateChunkBelow) {
			chunkMatrix[chunkRow - 1][chunkCol].activeNextFrame = true;
			chunkMatrix[chunkRow - 1][chunkCol].activeInTwoFrames = true;
		}

	}

	public static Chunk getChunk(int row, int col) {
		int chunkRow = (int) Math.floor((float) row / chunkSize * pixelSizeModifier);
		int chunkCol = (int) Math.floor((float) col / chunkSize * pixelSizeModifier);
		return chunkMatrix[chunkRow][chunkCol];
	}

	public static int[] getChunkLocation(int row, int col) {
		int chunkRow = (int) Math.floor((float) row / chunkSize * pixelSizeModifier);
		int chunkCol = (int) Math.floor((float) col / chunkSize * pixelSizeModifier);
		return new int[] { chunkRow, chunkCol };
	}

	/**
	 * Returns the given row of the matrix
	 * 
	 * @param row row to return
	 * @return returns the given row
	 */
	public Element[] getRow(int row) {
		return matrix[row];
	}

	/**
	 * Returns the current matrix
	 * 
	 * @return the current matrix
	 */
	public Element[][] getMatrix() {
		return matrix;
	}

	/**
	 * Clears the current matrix
	 */
	public void clear() {
		this.matrix = null;
		this.matrix = generateMatrix();
		System.gc();
	}

	/**
	 * Sets the given element to empty
	 * 
	 * @param element element to set empty
	 */
	public void clearElement(Element element) {
		this.setNewElement(element, ElementTypes.EMPTY);
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

		tempLocation = null;

		this.setElement(element1);
		this.setElement(element2);

		CellularMatrix.activateChunk(element1.getRow(), element1.getColumn());
		CellularMatrix.activateChunk(element2.getRow(), element2.getColumn());
	}

	/**
	 * Retrieves an element from the matrix based on its row and column.
	 * 
	 * @param row    row of the element being retrieved
	 * @param column column of the element being retrieved
	 * @return
	 */
	public Element getElement(int row, int column, boolean checkRow, boolean checkCol) {
		if (checkRow) {
			if (row < 0 || row >= rows) {
				return null;
			}
		}

		if (checkCol) {
			if (column < 0 || column >= columns) {
				return null;
			}
		}

		return matrix[row][column];
	}

	/**
	 * Sets the given element at the element's corresponding row and column.
	 * 
	 * @param element element to set in the matrix
	 */
	public void setElement(Element element) {
		matrix[element.getRow()][element.getColumn()] = element;
		CellularMatrix.activateChunk(element.getRow(), element.getColumn());
	}

	/**
	 * Draws the current matrix to the screen
	 * 
	 * @param shapeDrawer shape renderer that draws to viewport
	 */
	public void draw(ShapeDrawer shapeDrawer) {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				if (!(matrix[y][x] instanceof Empty)) {
					Element thisElement = matrix[y][x];
					if (!(thisElement instanceof Empty)) {
						shapeDrawer.setColor(thisElement.getColor());
						shapeDrawer.filledRectangle(x * pixelSizeModifier, y * pixelSizeModifier, pixelSizeModifier,
								pixelSizeModifier);
					}
				}
			}
		}
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

		CellularMatrix.activateChunk(element.getRow(), element.getColumn());

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
		case VOID:
			this.setElement(new Void(element.getRow(), element.getColumn()));
			break;
		default:
			break;
		}

		int row = element.getRow();
		int col = element.getColumn();
		element = null;
		return this.getElement(row, col, false, false);
	}

	/**
	 * Updates the frame. Updates all positions of all elements in the matrix based
	 * on their type using the element's update() method, and chooses the vertical
	 * direction to update based on which way the element moves. The horizontal
	 * update direction is chosen randomly to create a more realistic simulation.
	 * 
	 * @param shapeDrawer shape renderer that draws to the viewport
	 */
	public void updateFrame(ShapeDrawer shapeDrawer) {

		direction = direction ? false : true;
		Element element;

		for (int y = rows - 1; y >= 0; y--) {
			if (direction) {
				for (int x = 0; x < columns; x++) {
					element = matrix[rows - 1 - y][x];
					if (element.movesDown()) {
						element.update();
					}
				}
			} else {
				for (int x = columns - 1; x >= 0; x--) {
					element = matrix[rows - 1 - y][x];
					if (element.movesDown()) {
						element.update();
					}
				}
			}
		}
		for (int y = 0; y < rows; y++) {
			if (direction) {
				for (int x = 0; x < columns; x++) {
					element = matrix[rows - 1 - y][x];
					if (!element.movesDown()) {
						element.update();
					}
				}
			} else {
				for (int x = columns - 1; x >= 0; x--) {
					element = matrix[rows - 1 - y][x];
					if (!element.movesDown()) {
						element.update();
					}
				}
			}
		}
	}

	/**
	 * Traverses the matrix between the two given points. The points are given as x
	 * and y coordinates in pixel dimensions, where x == 0 and y == 0 at the bottom
	 * left of the window. Calculates the distance and slope between the points, and
	 * then iterates through the matrix between the two points. As it iterates, it
	 * stores the cells it passes in a 2D integer array, where each row is a point
	 * in the form [y, x] or [row, column].
	 * 
	 * @param x1 x location of the first point
	 * @param y1 y location of the first point
	 * @param x2 x location of the second point
	 * @param y2 y location of the second point
	 * @return a 2D integer array containing the row and column of a line of cells
	 *         between the given points
	 */
	public static int[][] traverseMatrix(float x1, float y1, float x2, float y2) {
		int col1 = (int) Math.round(x1 / pixelSizeModifier);
		int row1 = (int) Math.round(y1 / pixelSizeModifier);
		int col2 = (int) Math.round(x2 / pixelSizeModifier);
		int row2 = (int) Math.round(y2 / pixelSizeModifier);

		int xDifference = col2 - col1;
		int yDifference = row2 - row1;
		boolean xDifferenceLarger = Math.abs(xDifference) > Math.abs(yDifference);

		int xModifier = xDifference < 0 ? -1 : 1;
		int yModifier = yDifference < 0 ? -1 : 1;

		int upperBound = Math.max(Math.abs(xDifference), Math.abs(yDifference));
		int min = Math.min(Math.abs(xDifference), Math.abs(yDifference));
		float slope = (min == 0 || upperBound == 0) ? 0 : ((float) (min + 1) / (upperBound + 1));

		int[][] points = new int[upperBound][2];

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
			points[i - 1][0] = currentY;
			points[i - 1][1] = currentX;

		}

		if (points.length == 0) {
			points = new int[1][2];
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
		int row = element.getRow();
		int col = element.getColumn();

		if (col + 1 < columns) {
			if (row + 1 < rows) {
				adjacentElements[2] = matrix[row + 1][col + 1];
			}
			adjacentElements[4] = matrix[row][col + 1];
			if (row - 1 > 0) {
				adjacentElements[7] = matrix[row - 1][col + 1];
			}
		}

		if (col - 1 >= 0) {
			adjacentElements[3] = matrix[row][col - 1];
			if (row + 1 < rows) {
				adjacentElements[0] = matrix[row + 1][col - 1];
			}
			if (row - 1 > 0) {
				adjacentElements[5] = matrix[row - 1][col - 1];
			}
		}

		if (row - 1 >= 0) {
			adjacentElements[6] = matrix[row - 1][col];
		}
		if (row + 1 < rows) {
			adjacentElements[1] = matrix[row + 1][col];
		}

		return adjacentElements;
	}

}
