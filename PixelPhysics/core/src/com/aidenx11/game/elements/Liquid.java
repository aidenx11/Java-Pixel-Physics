package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;

public class Liquid extends Movable {

	public Liquid(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean burnsThings, float velocity,
			float acceleration, float maxSpeed, float density, boolean movesSideways) {
		super(type, row, column, color, canDie, lifetime, flammable, extinguishesThings, chanceToCatch, burnsThings,
				velocity, acceleration, maxSpeed, density, movesSideways, true, 0f);
		super.setFreeFalling(true);
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
			for (int i = 1; i < 6; i++) {
				Element currentElement = parentMatrix.getElement(this.getRow() - delta,
						this.getColumn() - randDirection * i);
				if (!(currentElement instanceof Empty) && !(currentElement instanceof Liquid)) {
					nextVertical1 = parentMatrix.getElement(this.getRow() - delta,
							this.getColumn() - randDirection * (i - 1));
					break;
				}
			}

			for (int i = 1; i < 6; i++) {
				Element currentElement = parentMatrix.getElement(this.getRow() - delta,
						this.getColumn() + randDirection * i);
				if (!(currentElement instanceof Empty) && !(currentElement instanceof Liquid)) {
					nextVertical2 = parentMatrix.getElement(this.getRow() - delta,
							this.getColumn() + randDirection * (i - 1));
					break;
				}
			}

			Element sideways1 = parentMatrix.getElement(this.getRow(), this.getColumn() - randDirection);
			Element sideways2 = parentMatrix.getElement(this.getRow(), this.getColumn() + randDirection);

			if (nextVertical != null && nextVertical.getDensity() < this.getDensity()) {
				parentMatrix.swap(this, nextVertical);
			}
			else if (nextVertical1 != null && nextVertical1.getDensity() < this.getDensity()) {
				parentMatrix.swap(this, nextVertical1);
			}
			else if (nextVertical2 != null && nextVertical2.getDensity() < this.getDensity()) {
				parentMatrix.swap(this, nextVertical2);
			} 
			else {
				this.setVerticalVelocity(0f);
			}

			if (sideways1 instanceof MovableSolid) {
				setElementFreeFalling((MovableSolid) sideways1);
			}

			if (sideways2 instanceof MovableSolid) {
				setElementFreeFalling((MovableSolid) sideways2);
			}

			sideways1 = parentMatrix.getElement(this.getRow(), this.getColumn() - randDirection);
			sideways2 = parentMatrix.getElement(this.getRow(), this.getColumn() + randDirection);

			if (sideways1 != null && sideways1.getDensity() < this.getDensity()) {
				parentMatrix.swap(this, sideways1);
			} else if (sideways2 != null && sideways2.getDensity() < this.getDensity()) {
				parentMatrix.swap(this, sideways2);
			}
		}

	}

}
