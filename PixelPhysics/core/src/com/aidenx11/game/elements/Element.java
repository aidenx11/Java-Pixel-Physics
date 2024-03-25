package com.aidenx11.game.elements;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.badlogic.gdx.graphics.Color;

/**
 * Super class of all elements.
 * 
 * @author Aiden Schroeder
 */
public abstract class Element {

	public CellularMatrix parentMatrix = pixelPhysicsGame.matrix;

	public static ElementTypes type;

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

	/** Whether or not this element moves down */
	private boolean movesDown;

	/** Whether or not element was modified this frame */
	private boolean modified;

	/** Acceleration of this element */
	private float acceleration;

	/** Max speed of this element */
	private float maxSpeed;

	/** Velocity of this element */
	private float velocity;

	/** Density of this element */
	private float density;

	/** Whether or not this element moves side to side */
	private boolean movesSideways;

	/** Whether or not this element has a limited life (can die) */
	private boolean limitedLife;
	/** Number of frames the element can stay alive if it has limited life */
	private int lifetime;

	private boolean flammable;

	public enum ElementTypes {
		SAND, EMPTY, WOOD, SMOKE, FIRE, WATER, STEAM, WET_SAND
	}

	public abstract ElementTypes getType();

	public abstract void resetVelocity();

	public abstract void setVelocity(float f);

	public abstract float getMaxSpeed();

	public abstract void setMaxSpeed(float maxSpeed);

	public abstract float getAcceleration();

	public abstract void setAcceleration(float acceleration);

	public abstract boolean isFlammable();

	public abstract boolean burnsThings();

	public abstract boolean extinguishesThings();

	public abstract float getChanceToCatch();

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public float getVelocity() {
		return velocity;
	}

	public int getUpdateCount() {
		float abs = Math.abs(getVelocity());
		int floored = (int) Math.floor(abs);
		float mod = abs - floored;

		return floored + (Math.random() < mod ? 1 : 0);
	}

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

	public CustomColor getCustomColor() {
		return color;
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
		if (movable) {
			this.isMovable = movable;
		} else {
			this.isMovable = movable;
			this.setMovesDown(false);
			this.setMovesSideways(false);
			this.setDensity(999);
		}

	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public void setMovesDown(boolean b) {
		this.movesDown = b;
	}

	public boolean movesDown() {
		return this.movesDown;
	}

	public boolean movesSideways() {
		return movesSideways;
	}

	public void setMovesSideways(boolean movesSideways) {
		this.movesSideways = movesSideways;
	}

	public boolean limitedLife() {
		return limitedLife;
	}

	public void setLimitedLife(boolean limitedLife) {
		if (!limitedLife) {
			setLifetime(-1);
		}
		this.limitedLife = limitedLife;
	}

	public int getLifetime() {
		return lifetime;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	public void setFlammable(boolean flammable) {
		if (flammable) {
			this.flammable = flammable;
			this.setLimitedLife(true);
		}
		this.flammable = flammable;
	}

	public void flicker() {

	}

	public int adjacentTo(ElementTypes type) {
		int numberOfAdjacencies = 0;
		Element currentElement = parentMatrix.getElement(this.getRow(), this.getColumn() - 1);
		if (currentElement != null && currentElement.getType() == type) {
			numberOfAdjacencies++;
		}
		currentElement = parentMatrix.getElement(this.getRow() - 1, this.getColumn() - 1);
		if (currentElement != null && currentElement.getType() == type) {
			numberOfAdjacencies++;
		}
		currentElement = parentMatrix.getElement(this.getRow() + 1, this.getColumn() - 1);
		if (currentElement != null && currentElement.getType() == type) {
			numberOfAdjacencies++;
		}
		currentElement = parentMatrix.getElement(this.getRow() + 1, this.getColumn());
		if (currentElement != null && currentElement.getType() == type) {
			numberOfAdjacencies++;
		}
		currentElement = parentMatrix.getElement(this.getRow(), this.getColumn() + 1);
		if (currentElement != null && currentElement.getType() == type) {
			numberOfAdjacencies++;
		}
		currentElement = parentMatrix.getElement(this.getRow() + 1, this.getColumn() + 1);
		if (currentElement != null && currentElement.getType() == type) {
			numberOfAdjacencies++;
		}
		currentElement = parentMatrix.getElement(this.getRow() - 1, this.getColumn());
		if (currentElement != null && currentElement.getType() == type) {
			numberOfAdjacencies++;
		}
		currentElement = parentMatrix.getElement(this.getRow() - 1, this.getColumn() + 1);
		if (currentElement != null && currentElement.getType() == type) {
			numberOfAdjacencies++;
		}

		return numberOfAdjacencies;
	}

	public boolean isWet() {
		return false;
	}

}
