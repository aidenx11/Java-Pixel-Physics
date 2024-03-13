package com.aidenx11.game;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.Sand;
import com.aidenx11.game.color.ColorValues;
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
				innerArr.add(null);
			}
			outerArray.add(innerArr);
		}
		return outerArray;
	}

	/**
	 * Sets the given value at the given row and column of the matrix
	 * 
	 * @param row    row to set value at
	 * @param column column to set value at
	 * @param element  value to set
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
	 * Swaps 2 given indices in the matrix
	 * 
	 * @param row1 x index of first particle to swap
	 * @param col1 y index of first particle to swap
	 * @param row2 x index of second particle to swap
	 * @param col2 y index of second particle to swap
	 */
	public void swap(int row1, int col1, int row2, int col2) {
		Element firstParticleValue = this.getElement(col1, row1);
		this.setElement(this.getElement(col2, row2));
		this.setElement(firstParticleValue);
	}
	
	/**
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Element getElement(int row, int column) {
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
		return this.getElement(row, column) == null;
	}

	/**
	 * Updates the matrix by performing checks for surrounding pixels on all pixels
	 * 
	 * @param valueToSet value to set if conditions are met
	 */
	public void update(int valueToSet) {

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
					thisElement.varyColor();
//					sandColor.setColor(ColorManager.varyColor(ColorValues.SAND_COLOR)[0],
//							ColorManager.varyColor(ColorValues.SAND_COLOR)[1],
//							ColorManager.varyColor(ColorValues.SAND_COLOR)[2]);
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
