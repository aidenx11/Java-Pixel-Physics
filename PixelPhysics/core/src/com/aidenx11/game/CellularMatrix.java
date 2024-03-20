package com.aidenx11.game;

import com.aidenx11.game.elements.Element;
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

		if (getFramesSinceLastModifiedElement() < 5) {
			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < columns; x++) {
					Element thisElement = this.getElement(y, x);
					thisElement.update();
					for (int v = 0; v < thisElement.getUpdateCount(); v++) {
						updateElement(thisElement);
					}
				}
			}
		}
	}

	/**
	 * Updates the given element
	 * 
	 * @param thisElement element to update
	 */
	public void updateElement(Element thisElement) {
		if (thisElement.isMovable()) {
			Element below = this.getElement(thisElement.getRow() - 1, thisElement.getColumn());
			int randDirection = Math.random() > 0.5 ? 1 : -1;
			Element below1 = this.getElement(thisElement.getRow() - 1, thisElement.getColumn() - randDirection);
			Element below2 = this.getElement(thisElement.getRow() - 1, thisElement.getColumn() + randDirection);

			if (below instanceof Empty) {
				this.swap(thisElement, below);
			} else if (below1 instanceof Empty) {
				this.swap(thisElement, below1);
			} else if (below2 instanceof Empty) {
				this.swap(thisElement, below2);
			} else {
				thisElement.setVelocity(0);
			}

		}
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

}
