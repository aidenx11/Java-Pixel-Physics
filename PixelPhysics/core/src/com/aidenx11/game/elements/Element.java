package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;
import com.badlogic.gdx.graphics.Color;

public abstract class Element {
	private int row;
	private int column;
	private CustomColor color;
	private int varyCount;

	public Element(int row, int column, CustomColor color) {
		setRow(row);
		setColumn(column);
		setColor(color);
	}

	public void varyColor() {
		if (varyCount == 0) {
			color = new CustomColor(color.varyColor());
			varyCount++;
		}
	}

	public void setColor(CustomColor color) {
		this.color = color;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Color getColor() {
		return new Color(color.getR() / 255f, color.getG() / 255f, color.getB() / 255f, 1f);
	}
}
