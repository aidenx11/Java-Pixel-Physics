package com.aidenx11.game.elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

	/** Type of this element */
	public static ElementTypes type;

	/** Row location of this element */
	private int row;

	/** Column location of this element */
	private int column;

	/** Color of this element */
	private CustomColor color;

	/** Whether or not element was modified this frame */
	private boolean modified;

	/** Density of this element */
	private float density;

	/** Whether or not this element has a limited life (can die) */
	private boolean limitedLife;

	/** Number of frames the element can stay alive if it has limited life */
	private int lifetime;

	private int meltingPoint;

	private boolean isFlammable;

	private boolean extinguishesThings;

	private float chanceToCatch;

	private boolean burnsThings;

	private boolean movesDown;

	private boolean onFire;
	
	private boolean fallingThroughAir = false;

	public enum ElementTypes {
		SAND, EMPTY, WOOD, SMOKE, FIRE, WATER, STEAM, WET_SAND, LEAF, DIRT, WET_DIRT, STONE, LAVA, OBSIDIAN, STEEL, RUST;
	}

	public static CustomColor[] fireColors = new CustomColor[] { new CustomColor(253, 207, 88),
			new CustomColor(242, 125, 12), new CustomColor(199, 14, 14), new CustomColor(240, 127, 19) };

	public abstract void update();

	public Element(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean burnsThings, boolean movesDown,
			int temperature) {
		setRow(row);
		setColumn(column);
		setColor(color);
		setType(type);
		setLifetime(lifetime);
		setFlammable(flammable);
		setExtinguishesThings(extinguishesThings);
		setChanceToCatch(chanceToCatch);
		setBurnsThings(burnsThings);
		setLimitedLife(canDie);
		setMovesDown(movesDown);
		setTemperature(temperature);
	}

	public void updateElementLife() {
		if (this.limitedLife() && this.getLifetime() < 1) {
			if (this instanceof Smoke || this instanceof Steam) {
				parentMatrix.clearElement(this);
			} else if (this instanceof Fire || this.isOnFire()) {
				if (Math.random() < 0.3) {
					parentMatrix.setElement(new Smoke(this.getRow(), this.getColumn()));
				} else {
					parentMatrix.clearElement(this);
				}
			} else if (this instanceof Steel) {
				parentMatrix.setNewElement(this, ElementTypes.RUST);
			}
		}

		if (this.isOnFire() && Math.random() < 0.1) {
			this.flicker();
		}

		this.setLifetime(this.getLifetime() - 1);
	}
	
	public void causeRust() {
		Element[] adjacentElements = parentMatrix.getAdjacentElements(this);
		List<Element> shuffledElements = Arrays.asList(adjacentElements);
		Collections.shuffle(shuffledElements);
		Element nextElement;
		for (int i = 0; i < shuffledElements.size(); i++) {
			nextElement = shuffledElements.get(i);
			if (nextElement instanceof Steel && !nextElement.limitedLife()
					&& Math.random() < ((Steel) nextElement).getChanceToRust()) {
				nextElement.setLimitedLife(true);
			}
		}
	}

	private boolean updateDryingLogic(Element[] elements) {
		boolean extinguished = false;
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] != null && elements[i].extinguishesThings() && this.isOnFire()) {
				if (elements[i] instanceof Water) {
					parentMatrix.setNewElement(elements[i], ElementTypes.STEAM);
					extinguished = true;
				} else if (elements[i] instanceof WetSand) {
					parentMatrix.setNewElement(this, ElementTypes.STEAM);
					parentMatrix.setNewElement(elements[i], ElementTypes.SAND);
					extinguished = true;
				} else if (elements[i] instanceof WetDirt) {
					parentMatrix.setNewElement(this, ElementTypes.STEAM);
					parentMatrix.setNewElement(elements[i], ElementTypes.DIRT);
					extinguished = true;
				}
			}
		}
		return extinguished;
	}

	private int updateNumberOfAdjacentFire(Element[] elements) {
		int numberOfFire = 0;
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] != null && elements[i].isOnFire()) {
				numberOfFire++;
			}
		}
		return numberOfFire;
	}

	public void updateBurningLogic() {

		Element[] adjacentElements = parentMatrix.getAdjacentElements(this);
		boolean extinguished = updateDryingLogic(adjacentElements);
		int numberOfFire = updateNumberOfAdjacentFire(adjacentElements);

		float chanceToCatch = this.getChanceToCatch() * numberOfFire;
		if (Math.random() < chanceToCatch && !this.isOnFire()) {
			this.setOnFire(true);
		}

		if (extinguished) {
			if (this.isOnFire()) {
				parentMatrix.setNewElement(this, ElementTypes.SMOKE);
			}
		}

	}

	public ElementTypes getType() {
		return type;
	}

	public void setType(ElementTypes type) {
		Element.type = type;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public void setColor(CustomColor color) {
		this.color = color;
	}

	public void setColor(int[] rgb) {
		this.color = new CustomColor(rgb);
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

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean limitedLife() {
		return limitedLife;
	}

	public void setLimitedLife(boolean limitedLife) {
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
			this.setLimitedLife(true);
		}
	}

	public boolean isFlammable() {
		return isFlammable;
	}

	public void flicker() {
		int idx = (int) (Math.round(Math.random() * 3));
		this.setColor(fireColors[idx]);
	}

	public boolean burnsThings() {
		return burnsThings;
	}

	public boolean extinguishesThings() {
		return extinguishesThings;
	}

	public float getChanceToCatch() {
		return chanceToCatch;
	}

	private void setBurnsThings(boolean burnsThings) {
		this.burnsThings = burnsThings;
	}

	private void setChanceToCatch(float chanceToCatch) {
		this.chanceToCatch = chanceToCatch;
	}

	private void setExtinguishesThings(boolean extinguishesThings) {
		this.extinguishesThings = extinguishesThings;
	}

	public boolean movesDown() {
		return movesDown;
	}

	public void setMovesDown(boolean movesDown) {
		this.movesDown = movesDown;
	}

	public boolean isOnFire() {
		return onFire;
	}

	public void setOnFire(boolean onFire) {
		this.onFire = onFire;
		this.setLimitedLife(true);
	}

	public int getTemperature() {
		return meltingPoint;
	}

	public void setTemperature(int temperature) {
		this.meltingPoint = temperature;
	}

	public boolean isFallingThroughAir() {
		return fallingThroughAir;
	}

	public void setFallingThroughAir(boolean fallingThroughAir) {
		this.fallingThroughAir = fallingThroughAir;
	}

}
