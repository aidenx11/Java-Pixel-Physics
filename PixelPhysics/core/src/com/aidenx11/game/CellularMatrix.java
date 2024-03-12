package com.aidenx11.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class CellularMatrix {
	public int rows;
	public int columns;
	public int pixelSizeModifier;

	private Array<Array<Integer>> matrix;

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
	private Array<Array<Integer>> generateMatrix() {

		Array<Array<Integer>> outerArray = new Array<>(true, rows);
		for (int y = 0; y < rows; y++) {
			Array<Integer> innerArr = new Array<>(true, columns);
			for (int x = 0; x < columns; x++) {
				innerArr.add(0);
			}
			outerArray.add(innerArr);
		}
		return outerArray;
	}

	/**
	 * Returns the value at the given row and column of this matrix
	 * 
	 * @param row    row of matrix to get value from
	 * @param column column of matrix to get value from
	 * @return value at the requested row and column
	 */
	public int getValue(int row, int column) {
		return matrix.get(row).get(column);
	}

	/**
	 * Sets the given value at the given row and column of the matrix
	 * 
	 * @param row    row to set value at
	 * @param column column to set value at
	 * @param value  value to set
	 */
	public void setValue(int row, int column, int value) {
		Array<Integer> rowArray = matrix.get(row);
		rowArray.set(column, value);
		matrix.set(row, rowArray);
	}

	/**
	 * Returns the given row of the matrix
	 * 
	 * @param row row to return
	 * @return returns the given row
	 */
	public Array<Integer> getRow(int row) {
		return matrix.get(row);
	}

	/**
	 * Returns the current matrix
	 * 
	 * @return the current matrix
	 */
	public Array<Array<Integer>> getMatrix() {
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
		int firstParticleValue = this.getValue(col1, row1);
		this.setValue(col1, row1, this.getValue(col2, row2));
		this.setValue(row2, col2, firstParticleValue);
	}

	/**
	 * Checks if the given row and column's pixel is empty (air)
	 * 
	 * @param row    row of the pixel
	 * @param column column of the pixel
	 * @return whether or not the pixel is empty
	 */
	public boolean isEmpty(int row, int column) {
		return this.getValue(row, column) == 0;
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
				if (this.getValue(y, x) == 1) {
					shapeRenderer.begin(ShapeType.Filled);
					shapeRenderer.setColor(Color.GOLD);
					shapeRenderer.rect(x * pixelSizeModifier, y * pixelSizeModifier, pixelSizeModifier,
							pixelSizeModifier);
					shapeRenderer.end();
				}
			}
		}
	}

}
