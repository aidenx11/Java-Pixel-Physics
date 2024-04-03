package com.aidenx11.game.elements;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Lava extends Liquid {

	public static ElementTypes type = ElementTypes.LAVA;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 3f;
	private static float density = 9f;
	private static int dispersionRate = 5;
	private static float chanceToMelt = 0.01f;
	private static int meltingPoint = 150;
	private int timeToHarden = 900;

	public static ColorValues[] lavaColors = new ColorValues[] { ColorValues.LAVA_RED, ColorValues.LAVA_ORANGE };

	public Lava(int row, int column) {
		super(type, row, column, new CustomColor(lavaColors[(int) Math.round(Math.random())], true), false, 1, false,
				false, 0, true, 0.9f, acceleration, maxSpeed, density, false, dispersionRate, meltingPoint);
		super.setOnFire(true);
	}

	@Override
	public void update() {
		this.updateMovementLogic();
		this.actOnOther();
	}

	@Override
	public void updateMovementLogic() {
		this.updateVerticalVelocity();

		for (int v = 0; v < this.getVerticalUpdateCount(); v++) {

			Element nextVertical1 = null;
			Element nextVertical2 = null;

			int delta = (int) Math.signum(this.getVerticalVelocity());
			Element nextVertical = parentMatrix.getElement(this.getRow() - delta, this.getColumn());
			int randDirection = Math.random() > 0.5 ? 1 : -1;

			for (int i = 0; i <= dispersionRate; i++) {
				Element currentElement = parentMatrix.getElement(this.getRow() - delta,
						this.getColumn() - randDirection * i);
				if ((!(currentElement instanceof Empty) && !(currentElement instanceof Liquid))
						|| i == dispersionRate) {
					nextVertical1 = parentMatrix.getElement(this.getRow() - delta,
							this.getColumn() - randDirection * (i));
					break;
				}
			}

			for (int i = 0; i <= dispersionRate; i++) {
				Element currentElement = parentMatrix.getElement(this.getRow() - delta,
						this.getColumn() + randDirection * i);
				if ((!(currentElement instanceof Empty) && !(currentElement instanceof Liquid))
						|| i == dispersionRate) {
					nextVertical2 = parentMatrix.getElement(this.getRow() - delta,
							this.getColumn() + randDirection * (i));
					break;
				}
			}

			if (nextVertical != null && nextVertical.getDensity() < this.getDensity()) {
				parentMatrix.swap(this, nextVertical);
			} else if (nextVertical1 != null && nextVertical1.getDensity() < this.getDensity() && nextVertical != null
					&& nextVertical.getDensity() > this.getDensity()) {
				parentMatrix.swap(this, nextVertical1);
			} else if (nextVertical2 != null && nextVertical2.getDensity() < this.getDensity() && nextVertical != null
					&& nextVertical.getDensity() > this.getDensity()) {
				parentMatrix.swap(this, nextVertical2);
			} else {
				this.setVerticalVelocity(1.5f);
			}

			Element sideways1 = parentMatrix.getElement(this.getRow(), this.getColumn() - randDirection);
			Element sideways2 = parentMatrix.getElement(this.getRow(), this.getColumn() + randDirection);

			if (sideways1 instanceof MovableSolid) {
				setElementFreeFalling((MovableSolid) sideways1);
			}

			if (sideways2 instanceof MovableSolid) {
				setElementFreeFalling((MovableSolid) sideways2);
			}

			if (sideways1 != null && sideways1.getDensity() < this.getDensity()
					&& ((nextVertical != null && nextVertical.getDensity() == this.getDensity())
							|| nextVertical == null)) {
				parentMatrix.swap(this, sideways1);
			} else if (sideways2 != null && sideways2.getDensity() < this.getDensity()
					&& ((nextVertical != null && nextVertical.getDensity() == this.getDensity())
							|| nextVertical == null)) {
				parentMatrix.swap(this, sideways2);
			}
		}
	}

	public void actOnOther() {

		Element[] adjacentElements = parentMatrix.getAdjacentElements(this);

		for (int i = 0; i < adjacentElements.length; i++) {
			if (adjacentElements[i] instanceof Empty && Math.random() < 0.0003) {
				parentMatrix.setNewElement(adjacentElements[i], ElementTypes.SMOKE);
			}
			if (adjacentElements[i] instanceof WetSand) {
				parentMatrix.setNewElement(adjacentElements[i], ElementTypes.SAND);
				parentMatrix.setNewElement(this, ElementTypes.STONE);
				return;
			}
			if (adjacentElements[i] instanceof WetDirt) {
				parentMatrix.setNewElement(adjacentElements[i], ElementTypes.DIRT);
				parentMatrix.setNewElement(this, ElementTypes.STONE);
				return;
			}
			if (adjacentElements[i] instanceof Water) {
				parentMatrix.setNewElement(this, ElementTypes.STONE);
				parentMatrix.setNewElement(adjacentElements[i], ElementTypes.STEAM);
				return;
			} else if (adjacentElements[i] instanceof Stone && Math.random() < chanceToMelt) {
				parentMatrix.setNewElement(adjacentElements[i], ElementTypes.LAVA);
				if (Math.random() < 0.7) {
					parentMatrix.clearElement(this);
				}
				return;
			}
		}
	}

}
