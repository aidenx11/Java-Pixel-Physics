package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;
import com.badlogic.gdx.graphics.Color;

/**
 * Super class of all elements.
 * 
 * @author Aiden Schroeder
 */
public abstract class Element {
	
	/** Whether or not the element is empty */
	private boolean isEmpty;
	
	/** Row location of this element */
	private int row;
	
	/** Column location of this element */
	private int column;
	
	/** Color of this element */
	private CustomColor color;
	
	/** Whether or not the color of this element has been varied */
	private boolean colorHasBeenVaried;
	
	/** Whether or not this element is movable */
	private boolean isMovable;
	
	/** Whether or not element was modified this frame */
	private boolean modified;

	public enum ElementTypes {
		SAND, EMPTY, WOOD
	}

	public abstract ElementTypes getType();
	
	public abstract void updateVelocity();
	
	public abstract int getUpdateCount();
	
	public abstract void resetVelocity();
	
	public abstract void update();

	public Element(int row, int column, CustomColor color, boolean isEmpty) {
		setEmpty(isEmpty);
		setRow(row);
		setColumn(column);
		setColor(color);
	}

	public void varyColor() {
		if (!colorHasBeenVaried && !isEmpty()) {
			color = new CustomColor(color.varyColor());
			colorHasBeenVaried = true;
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

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public boolean isMovable() {
		return isMovable;
	}

	public void setMovable(boolean movable) {
		this.isMovable = movable;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public void randomizeColor() {
		color.randomizeColor();
	}

	
}
