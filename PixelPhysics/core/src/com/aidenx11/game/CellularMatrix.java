package com.aidenx11.game;

import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.ElementUpdater;
import com.aidenx11.game.elements.Empty;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class CellularMatrix {

	/** Number of rows in the matrix */
	public int rows;

	/** Number of columns in the matrix */
	public int columns;

	/** Pixel size modifier of the matrix */
	public int pixelSizeModifier;

	/** The matrix itself. Stores elements */
	private Array<Array<Element>> matrix;

	/** Keeps track of whether or not elements were modified last frame */
	private boolean modifiedElements;

	/** Keeps track of number of frames since elements were modified */
	private int framesSinceLastModifiedElement;

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
		this.rows = rows;
		this.columns = columns;
		this.pixelSizeModifier = pixelSizeModifier;
		this.matrix = generateMatrix();
		setModifiedElements(false);
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

	public boolean isModifiedElements() {
		return modifiedElements;
	}

	public void setModifiedElements(boolean modifiedElements) {
		this.modifiedElements = modifiedElements;
	}

	public int getFramesSinceLastModifiedElement() {
		return framesSinceLastModifiedElement;
	}

	public void setFramesSinceLastModifiedElement(int framesSinceLastModifiedElement) {
		this.framesSinceLastModifiedElement = framesSinceLastModifiedElement;
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

		if (row < 0 || row >= rows || column < 0 || column >= columns) {
			return null;
		}

		return getRow(row).get(column);
	}

	/**
	 * Checks if the given row and column's element is empty.
	 * 
	 * @param row    row of the element
	 * @param column column of the element
	 * @return whether or not the element is empty
	 */
	public boolean isEmpty(int row, int column) {
		return this.getElement(row, column).isEmpty();
	}

	/**
	 * Sets the given element at the element's corresponding row and column.
	 * 
	 * @param element element to set in the matrix
	 */
	public void setElement(Element element) {
		int row = element.getRow();
		int column = element.getColumn();
		Array<Element> rowArray = matrix.get(row);
		rowArray.set(column, element);
		matrix.set(row, rowArray);
		if (framesSinceLastModifiedElement != 0) {
			setFramesSinceLastModifiedElement(0);
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
				if (!this.isEmpty(y, x)) {
					Element thisElement = this.getElement(y, x);
					if (!thisElement.isEmpty()) {
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
	 * Updates the frame. Updates all positions of all elements in the matrix based
	 * on their type. If the element is movable, moves it.
	 * 
	 * @param sr shape renderer that draws to the viewport
	 */
	public void updateFrame(ShapeRenderer sr) {

		framesSinceLastModifiedElement++;
		direction = direction ? false : true;

		if (getFramesSinceLastModifiedElement() < 5) {
			for (int y = rows - 1; y >= 0; y--) {
				if (direction) {
					for (int x = 0; x < columns; x++) {
						ElementUpdater.update(this.getElement(rows - 1 - y, x), this);
					}
				} else {
					for (int x = columns - 1; x >= 0; x--) {
						ElementUpdater.update(this.getElement(rows - 1 - y, x), this);
					}
				}
			}
		}
	}

}
