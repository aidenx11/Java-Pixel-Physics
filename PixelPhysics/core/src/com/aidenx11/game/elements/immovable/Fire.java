package com.aidenx11.game.elements.immovable;

import com.aidenx11.game.elements.Element;

/**
 * Class to manage Fire elements. Extends the Immovable class.
 * 
 * Fire elements have a random color from the Element.fireColors enumeration.
 * Fire has a random short life span after which it will turn into smoke. All
 * fire elements are automatically set on fire.
 */
public class Fire extends Immovable {

	public static ElementTypes type = ElementTypes.FIRE;

	public Fire(int row, int column) {
		super(type, row, column, Element.fireColors[(int) Math.round(Math.random() * 3)], true,
				75 + (int) (75 * Math.random()), true, false, 0, true, -1);
		super.setOnFire(true);
	}

	@Override
	public void update() {
		this.checkForExtinguishingElements();
		super.update();
	}

	public void checkForExtinguishingElements() {
		Element[] elementsAbove = new Element[] { parentMatrix.getElement(getRow() + 1, getColumn()),
				parentMatrix.getElement(getRow() + 1, getColumn() - 1),
				parentMatrix.getElement(getRow() + 1, getColumn() + 1),
				parentMatrix.getElement(getRow(), getColumn() - 1),
				parentMatrix.getElement(getRow(), getColumn() + 1) };
		for (int i = 0; i < elementsAbove.length; i++) {
			if (elementsAbove[i] != null && elementsAbove[i].extinguishesThings()) {

				ElementTypes type = elementsAbove[i].getType();
				float chanceToExtinguish;

				switch (type) {
				case WATER:
					chanceToExtinguish = 1;
					if (Math.random() < 0.3) {
						parentMatrix.setNewElement(elementsAbove[i], ElementTypes.STEAM);
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
						parentMatrix.setNewElement(this, ElementTypes.SMOKE);
					} else {
						parentMatrix.clearElement(this);
					}
				}
			}
		}
	}

}
