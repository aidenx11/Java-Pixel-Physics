package com.aidenx11.game.elements;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;
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

	private boolean isFlammable;

	private boolean extinguishesThings;

	private float chanceToCatch;

	private boolean burnsThings;

	private boolean movesDown;

	private boolean onFire;

	public enum ElementTypes {
		SAND, EMPTY, WOOD, SMOKE, FIRE, WATER, STEAM, WET_SAND, LEAF;
	}

	public static CustomColor[] fireColors = new CustomColor[] { new CustomColor(253, 207, 88),
			new CustomColor(242, 125, 12), new CustomColor(199, 14, 14), new CustomColor(240, 127, 19) };

	public abstract void update();

	public Element(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean burnsThings,
			boolean movesDown) {
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
	}

	public void updateElementLife() {
		if (this.limitedLife() && this.getLifetime() < 1) {
			switch (this.getType()) {
			case SMOKE:
				parentMatrix.setNewElement(this, ElementTypes.EMPTY);
				break;
			case FIRE:
			case WOOD:
				parentMatrix.setNewElement(this, ElementTypes.SMOKE);
				break;
			default:
				parentMatrix.setNewElement(this, ElementTypes.SMOKE);
				break;
			}
		}

		if (this.isOnFire() && Math.random() < 0.1) {
			this.flicker();
		}
		
		this.setLifetime(this.getLifetime() - 1);
	}

	public void updateBurningLogic() {
		boolean extinguished = false;
		int numberOfFire = 0;
		Element otherElement = parentMatrix.getElement(this.getRow() + 1, this.getColumn());
		if (otherElement != null && otherElement.isOnFire()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (this.isOnFire() && otherElement instanceof Water) {
				parentMatrix.setNewElement(otherElement, ElementTypes.STEAM);
			} else if (this.isOnFire() && otherElement instanceof WetSand) {
				parentMatrix.setNewElement(this, ElementTypes.STEAM);
				parentMatrix.setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = parentMatrix.getElement(this.getRow() + 1, this.getColumn() + 1);
		if (otherElement != null && otherElement.isOnFire()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (this.isOnFire() && otherElement instanceof Water) {
				parentMatrix.setNewElement(otherElement, ElementTypes.STEAM);
			} else if (this.isOnFire() && otherElement instanceof WetSand) {
				parentMatrix.setNewElement(this, ElementTypes.STEAM);
				parentMatrix.setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = parentMatrix.getElement(this.getRow() + 1, this.getColumn() - 1);
		if (otherElement != null && otherElement.isOnFire()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (this.isOnFire() && otherElement instanceof Water) {
				parentMatrix.setNewElement(otherElement, ElementTypes.STEAM);
			} else if (this.isOnFire() && otherElement instanceof WetSand) {
				parentMatrix.setNewElement(this, ElementTypes.STEAM);
				parentMatrix.setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = parentMatrix.getElement(this.getRow(), this.getColumn() - 1);
		if (otherElement != null && otherElement.isOnFire()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (this.isOnFire() && otherElement instanceof Water) {
				parentMatrix.setNewElement(otherElement, ElementTypes.STEAM);
			} else if (this.isOnFire() && otherElement instanceof WetSand) {
				parentMatrix.setNewElement(this, ElementTypes.STEAM);
				parentMatrix.setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = parentMatrix.getElement(this.getRow(), this.getColumn() + 1);
		if (otherElement != null && otherElement.isOnFire()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (this.isOnFire() && otherElement instanceof Water) {
				parentMatrix.setNewElement(otherElement, ElementTypes.STEAM);
			} else if (this.isOnFire() && otherElement instanceof WetSand) {
				parentMatrix.setNewElement(this, ElementTypes.STEAM);
				parentMatrix.setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = parentMatrix.getElement(this.getRow() - 1, this.getColumn());
		if (otherElement != null && otherElement.isOnFire()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (this.isOnFire() && otherElement instanceof Water) {
				parentMatrix.setNewElement(otherElement, ElementTypes.STEAM);
			} else if (this.isOnFire() && otherElement instanceof WetSand) {
				parentMatrix.setNewElement(this, ElementTypes.STEAM);
				parentMatrix.setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = parentMatrix.getElement(this.getRow() - 1, this.getColumn() + 1);
		if (otherElement != null && otherElement.isOnFire()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (this.isOnFire() && otherElement instanceof Water) {
				parentMatrix.setNewElement(otherElement, ElementTypes.STEAM);
			} else if (this.isOnFire() && otherElement instanceof WetSand) {
				parentMatrix.setNewElement(this, ElementTypes.STEAM);
				parentMatrix.setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}
		otherElement = parentMatrix.getElement(this.getRow() - 1, this.getColumn() - 1);
		if (otherElement != null && otherElement.isOnFire()) {
			numberOfFire++;
		}
		if (otherElement != null && otherElement.extinguishesThings()) {
			if (this.isOnFire() && otherElement instanceof Water) {
				parentMatrix.setNewElement(otherElement, ElementTypes.STEAM);
			} else if (this.isOnFire() && otherElement instanceof WetSand) {
				parentMatrix.setNewElement(this, ElementTypes.STEAM);
				parentMatrix.setNewElement(otherElement, ElementTypes.SAND);
			}
			extinguished = true;

		}

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

}
