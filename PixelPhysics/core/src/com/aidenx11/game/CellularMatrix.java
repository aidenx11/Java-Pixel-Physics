package com.aidenx11.game;

import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.Empty;
import com.aidenx11.game.elements.Sand;
import com.aidenx11.game.input.MouseInput;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class CellularMatrix {
	public int rows;
	public int columns;
	public int pixelSizeModifier;

	private Array<Array<Element>> matrix;

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
	}

	/**
	 * Generates a matrix based on the rows and columns of this matrix and
	 * initializes all values to zero.
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
	 * Sets the given value at the given row and column of the matrix
	 * 
	 * @param row     row to set value at
	 * @param column  column to set value at
	 * @param element value to set
	 */
	public void setElement(Element element) {
		int row = element.getRow();
		int column = element.getColumn();
		Array<Element> rowArray = matrix.get(row);
		rowArray.set(column, element);
		matrix.set(row, rowArray);
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
	 * Swaps 2 given elements in the matrix
	 * 
	 * 
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
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Element getElement(int row, int column) {

		if (row < 0 || row >= rows || column < 0 || column >= columns) {
			return null;
		}

		return getRow(row).get(column);
	}

	/**
	 * Checks if the given row and column's pixel is empty (air)
	 * 
	 * @param row    row of the pixel
	 * @param column column of the pixel
	 * @return whether or not the pixel is empty
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

		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				if (!this.isEmpty(y, x)) {
					Element thisElement = this.getElement(y, x);
					if (!thisElement.isEmpty()) {
						thisElement.varyColor();
						shapeRenderer.begin(ShapeType.Filled);
						shapeRenderer.setColor(thisElement.getColor());
						shapeRenderer.rect(x * pixelSizeModifier, y * pixelSizeModifier, pixelSizeModifier,
								pixelSizeModifier);
						shapeRenderer.end();
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param sr
	 */
	public void updateFrame(ShapeRenderer sr) {
		
		this.draw(sr);
		
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				Element thisElement = this.getElement(y, x);

				if (thisElement instanceof Sand) {
					Element below = this.getElement(y - 1, x);
					int randDirection = (int) Math.round(Math.random() * -1 + 1);
					Element below1 = this.getElement(y - 1, x - randDirection);
					Element below2 = this.getElement(y - 1, x + randDirection);

					if (below instanceof Empty) {
						this.swap(thisElement, below);
					} else if (below1 instanceof Empty) {
						this.swap(thisElement, below1);
					} else if (below2 instanceof Empty) {
						this.swap(thisElement, below2);
					}
				}
			}
		}
	}

}
