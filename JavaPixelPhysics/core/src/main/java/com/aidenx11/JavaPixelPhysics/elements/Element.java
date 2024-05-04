package com.aidenx11.JavaPixelPhysics.elements;

import com.aidenx11.JavaPixelPhysics.CellularMatrix;
import com.aidenx11.JavaPixelPhysics.PixelPhysicsGame;
import com.aidenx11.JavaPixelPhysics.color.CustomColor;
import com.aidenx11.JavaPixelPhysics.elements.Element.ElementTypes;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Fire;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Steel;
import com.aidenx11.JavaPixelPhysics.elements.movable.gas.Smoke;
import com.aidenx11.JavaPixelPhysics.elements.movable.gas.Steam;
import com.aidenx11.JavaPixelPhysics.elements.movable.liquid.Water;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.wet_movable_solid.WetDirt;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.wet_movable_solid.WetSand;
import com.badlogic.gdx.graphics.Color;

/**
 * Super class of all elements. Contains fields for parent matrix, element type,
 * location, color, density, lifetime, and more.
 * 
 * @author Aiden Schroeder
 */
public abstract class Element {

	/** Type of this element */
	public static ElementTypes type;

	/** Row location of this element */
	private int row;

	/** Column location of this element */
	private int column;

	private int[] color;

	/** Density of this element */
	private int density;

	/** Whether or not this element has a limited life (can die) */
	private boolean limitedLife;

	/** Number of frames the element can stay alive if it has limited life */
	private int lifetime;

	/** Melting point of this element */
	private int meltingPoint;

	/** Whether or not this element is flammable */
	private boolean isFlammable;

	/** Whether or not this element extinguishes other elements */
	private boolean extinguishesThings;

	/** Chance for this element to catch on fire */
	private float chanceToCatch;

	/** Whether or not this element moves down */
	private boolean movesDown;

	/** Whether or not this element is on fire */
	private boolean onFire;

	/** Whether or not this element is falling through air */
	private boolean fallingThroughAir = false;

	/**
	 * Public enumeration that contains all the types of elements in the simulation
	 */
	public enum ElementTypes {
		SAND, EMPTY, WOOD, SMOKE, FIRE, WATER, STEAM, WET_SAND, LEAF, DIRT, WET_DIRT, STONE, LAVA, OBSIDIAN, STEEL,
		RUST, VOID;
	}

	/**
	 * CustomColor array to keep track of the colors an element alternates through
	 * when it is burning
	 */
	public static CustomColor[] fireColors = new CustomColor[] { new CustomColor(253, 207, 88),
			new CustomColor(242, 125, 12), new CustomColor(199, 14, 14), new CustomColor(240, 127, 19) };

	/**
	 * Abstract update method that varies based on the element inheriting it
	 */
	public abstract void update();

	/**
	 * Default constructor for Element. Every subclass of Element calls up to this
	 * constructor.
	 * 
	 * @param type               Type of this element, from ElementTypes enumeration
	 * @param row                row location of this element
	 * @param column             column location of this element
	 * @param color              color of this element
	 * @param canDie             whether or not this element can die/has limited
	 *                           life
	 * @param lifetime           lifetime of this element
	 * @param flammable          whether or not this element is flammable
	 * @param extinguishesThings whether or not this element extinguishes other
	 *                           elements
	 * @param chanceToCatch      chance for this element to catch on fire when in
	 *                           contact with another element that is on fire,
	 *                           ranging from 0-1
	 * @param movesDown          whether or not this element moves downward
	 * @param temperature        temperature of this element
	 */
	public Element(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean movesDown, int temperature) {
		setType(type);
		setRow(row);
		setColumn(column);
		setColor(color);
		setType(type);
		setLifetime(lifetime);
		setFlammable(flammable);
		setExtinguishesThings(extinguishesThings);
		setChanceToCatch(chanceToCatch);
		setLimitedLife(canDie);
		setMovesDown(movesDown);
		setTemperature(temperature);

		if (color != null && !(this instanceof Water)) {
			this.setColor(color.varyColor());
		}

	}

	/**
	 * Updates the lifetime of this element. Also causes the element to flicker if
	 * it is on fire.
	 */
	public void updateElementLife() {
		if (this.limitedLife() && this.getLifetime() < 1) {
			if (this instanceof Smoke || this instanceof Steam) {
				PixelPhysicsGame.matrix.clearElement(this);
			} else if (this instanceof Fire || this.isOnFire()) {
				if (Math.random() < 0.3) {
					PixelPhysicsGame.matrix.setElement(new Smoke(this.getRow(), this.getColumn()));
				} else {
					PixelPhysicsGame.matrix.clearElement(this);
				}
			} else if (this instanceof Steel) {
				PixelPhysicsGame.matrix.setNewElement(this, ElementTypes.RUST);
			}
		}

		if (this.isOnFire() && Math.random() < 0.1) {
			this.flicker();
		}

		this.setLifetime(this.getLifetime() - 1);
	}

	/**
	 * When called by an element, causes elements that can rust to turn into rust
	 * around the element.
	 */
	public void causeRust(Element nextElement) {

		if (nextElement instanceof Steel && !nextElement.limitedLife()
				&& Math.random() < ((Steel) nextElement).getChanceToRust()) {
			nextElement.setLimitedLife(true);
		}

	}

	/**
	 * Updates the logic concerning fire turning water into steam and drying wet
	 * elements
	 * 
	 * @param elements array of elements to check
	 * @return whether or not this element was extinguished
	 */
	private boolean updateDryingLogic(Element[] elements) {
		boolean extinguished = false;
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] != null && elements[i].extinguishesThings() && this.isOnFire()) {
				if (elements[i] instanceof Water && Math.random() < 0.4) {
					PixelPhysicsGame.matrix.setNewElement(elements[i], ElementTypes.STEAM);
					extinguished = true;
				} else if (elements[i] instanceof WetSand) {
					PixelPhysicsGame.matrix.setNewElement(this, ElementTypes.STEAM);
					PixelPhysicsGame.matrix.setNewElement(elements[i], ElementTypes.SAND);
					extinguished = true;
				} else if (elements[i] instanceof WetDirt) {
					PixelPhysicsGame.matrix.setNewElement(this, ElementTypes.STEAM);
					PixelPhysicsGame.matrix.setNewElement(elements[i], ElementTypes.DIRT);
					extinguished = true;
				}
			}
		}
		return extinguished;
	}

	/**
	 * Returns the number of elements in the given element array that are on fire
	 * 
	 * @param elements elements to check
	 * @return number of elements in the given element array that are on fire
	 */
	private int updateNumberOfAdjacentFire(Element[] elements) {
		int numberOfFire = 0;
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] != null && (elements[i].isOnFire() || elements[i] instanceof Fire)) {
				numberOfFire++;
			}
		}
		return numberOfFire;
	}

	/**
	 * Updates logic concerning elements being burned. If the element was on fire
	 * and gets extinguished, turns into smoke.
	 */
	public void updateBurningLogic() {

		Element[] adjacentElements = PixelPhysicsGame.matrix.getAdjacentElements(this, true, true, true);
		boolean extinguished = updateDryingLogic(adjacentElements);
		int numberOfFire = updateNumberOfAdjacentFire(adjacentElements);

		float chanceToCatch = this.getChanceToCatch() * numberOfFire;
		if (Math.random() < chanceToCatch && !this.isOnFire()) {
			this.setOnFire(true);
		}

		if (extinguished && this.isOnFire()) {
			PixelPhysicsGame.matrix.setNewElement(this, ElementTypes.SMOKE);

		}

	}
	
	/**
	 * Checks elements above this fire, and to the left and right. If one of the
	 * elements extinguishes elements, extinguishes the fire.
	 */
	public void checkForExtinguishingElements() {
		Element[] elementsAbove = new Element[] { PixelPhysicsGame.matrix.getElement(getRow() + 1, getColumn(), true, false),
				PixelPhysicsGame.matrix.getElement(getRow() + 1, getColumn() - 1, true, true),
				PixelPhysicsGame.matrix.getElement(getRow() + 1, getColumn() + 1, true, true),
				PixelPhysicsGame.matrix.getElement(getRow(), getColumn() - 1, false, true),
				PixelPhysicsGame.matrix.getElement(getRow(), getColumn() + 1, false, true) };

		float chanceToExtinguish = 0;

		for (int i = 0; i < elementsAbove.length; i++) {
			if (elementsAbove[i] != null && elementsAbove[i].extinguishesThings()) {

				ElementTypes type = elementsAbove[i].getType();

				switch (type) {
				case WATER:
					chanceToExtinguish = 1;
					if (Math.random() < 0.3) {
						PixelPhysicsGame.matrix.setNewElement(elementsAbove[i], ElementTypes.STEAM);
					}
					break;
				case SAND:
				case DIRT:
					chanceToExtinguish = 0.3f;
					break;
				default:
					chanceToExtinguish = 1;
				}

				if (Math.random() < chanceToExtinguish) {
					if (Math.random() < 0.5f) {
						PixelPhysicsGame.matrix.setNewElement(this, ElementTypes.SMOKE);
						return;
					} else {
						PixelPhysicsGame.matrix.clearElement(this);
						return;
					}
				}
			}
		}
	}

	/**
	 * Causes this element's color to flicker between the colors in the fireColors
	 * array
	 */
	public void flicker() {
		int idx = (int) Math.round(Math.random() * 3);
		this.setColor(fireColors[idx]);
	}

	/**
	 * @return the type of this element
	 */
	public ElementTypes getType() {
		return type;
	}

	/**
	 * Sets the type of this element
	 * 
	 * @param type type to set
	 */
	public void setType(ElementTypes type) {
		Element.type = type;
	}

	/**
	 * @return the density of this element
	 */
	public float getDensity() {
		return density;
	}

	/**
	 * Sets the density of this element
	 * 
	 * @param density density to set
	 */
	public void setDensity(int density) {
		this.density = density;
	}

	/**
	 * Sets the color of this element to the given CustomColor
	 * 
	 * @param color color to set
	 */
	public void setColor(CustomColor color) {
		if (color != null) {
			this.color = new int[] { color.getR(), color.getG(), color.getB() };
		}
	}

	/**
	 * Sets the color of this element to the given rgb values
	 * 
	 * @param rgb integer array of rgb values
	 */
	public void setColor(int[] rgb) {
		this.color = rgb;
	}

	/**
	 * Sets the row location of this element
	 * 
	 * @param row row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Sets the column location of this element
	 * 
	 * @param column column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @return the row location of this element
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column location of this element
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @return the color of this element as a LibGDX color object
	 */
	public Color getColor() {
		return new Color(color[0] / 255f, color[1] / 255f, color[2] / 255f, 1f);
	}

	/**
	 * @return whether or not this element has a limited lifetime
	 */
	public boolean limitedLife() {
		return limitedLife;
	}

	/**
	 * Sets whether or not this element has limited lifetime
	 * 
	 * @param limitedLife whether or not this element has limited lifetime
	 */
	public void setLimitedLife(boolean limitedLife) {
		this.limitedLife = limitedLife;
	}

	/**
	 * @return the lifetime of this element
	 */
	public int getLifetime() {
		return lifetime;
	}

	/**
	 * Sets the lifetime of this element
	 * 
	 * @param lifetime lifetime to set
	 */
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	/**
	 * Sets whether or not this element is flammable. If set to true, also sets this
	 * element to have limited lifetime
	 * 
	 * @param flammable whether or not this element is flammable
	 */
	public void setFlammable(boolean flammable) {
		if (flammable) {
			this.setLimitedLife(true);
		}
	}

	/**
	 * @return whether or not this element is flammable
	 */
	public boolean isFlammable() {
		return isFlammable;
	}

	/**
	 * @return whether or not this element extinguishes other elements
	 */
	public boolean extinguishesThings() {
		return extinguishesThings;
	}

	/**
	 * @return the odds of this element catching on fire when adjacent to burning
	 *         elements
	 */
	public float getChanceToCatch() {
		return chanceToCatch;
	}

	/**
	 * Sets the chance for this element to catch fire when it is adjacent to other
	 * burning elements
	 * 
	 * @param chanceToCatch chance for this element to catch fire when it is
	 *                      adjacent to other burning elements
	 */
	private void setChanceToCatch(float chanceToCatch) {
		this.chanceToCatch = chanceToCatch;
	}

	/**
	 * Sets whether or not this element extinguishes other elements
	 * 
	 * @param extinguishesThings whether or not this element extinguishes other
	 *                           elements
	 */
	private void setExtinguishesThings(boolean extinguishesThings) {
		this.extinguishesThings = extinguishesThings;
	}

	/**
	 * @return whether or not this element moves down
	 */
	public boolean movesDown() {
		return movesDown;
	}

	/**
	 * Sets whether or not this element moves down
	 * 
	 * @param movesDown whether or not this element moves down
	 */
	public void setMovesDown(boolean movesDown) {
		this.movesDown = movesDown;
	}

	/**
	 * @return true if this element is on fire, false if it is not
	 */
	public boolean isOnFire() {
		return onFire;
	}

	/**
	 * Set whether or not this element is lit on fire. If it is, set limited life to
	 * true
	 * 
	 * @param onFire whether or not this element is lit on fire
	 */
	public void setOnFire(boolean onFire) {
		this.onFire = onFire;
		if (onFire) {
			this.setLimitedLife(true);
		}
	}

	/**
	 * @return the current temperature of this element
	 */
	public int getTemperature() {
		return meltingPoint;
	}

	/**
	 * Sets the current temperature of this element
	 * 
	 * @param temperature temperature to set
	 */
	public void setTemperature(int temperature) {
		this.meltingPoint = temperature;
	}

	/**
	 * @return whether or not this element is falling through air
	 */
	public boolean isFallingThroughAir() {
		return fallingThroughAir;
	}

	/**
	 * Set whether or not this element is falling through air
	 * 
	 * @param fallingThroughAir whether or not this element is falling through air
	 */
	public void setFallingThroughAir(boolean fallingThroughAir) {
		this.fallingThroughAir = fallingThroughAir;
	}

}
