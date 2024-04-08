package com.aidenx11.game.elements;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.color.CustomColor;

public abstract class MovableSolid extends Movable {

	public MovableSolid(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean burnsThings, float velocity,
			float acceleration, float maxSpeed, float density, boolean movesSideways, float inertialResistance,
			float friction, int temperature) {
		super(type, row, column, color, canDie, lifetime, flammable, extinguishesThings, chanceToCatch, burnsThings,
				velocity, acceleration, maxSpeed, density, movesSideways, true, friction, temperature);
		this.setInertialResistance(inertialResistance);
	}

	@Override
	public void updateMovementLogic() {
		this.updateVerticalVelocity();

		for (int v = 0; v < this.getVerticalUpdateCount(); v++) {

			Element nextVertical1;
			Element nextVertical2;
			if (this.isFallingThroughAir()) {
				this.setFallingThroughAir(false);
			}

			int delta = (int) Math.signum(this.getVerticalVelocity());
			if (delta > 0) {
				for (int i = this.getRow() - delta; i >= 0; i--) {
					if (parentMatrix.getElement(i, this.getColumn()) instanceof Immovable) {
						break;
					}
					if (parentMatrix.getElement(i, this.getColumn()) instanceof Empty) {
						setFallingThroughAir(true);
						break;
					}
				}
			} else if (delta < 0) {
				for (int i = this.getRow() - delta; i <= CellularMatrix.rows; i++) {
					if (parentMatrix.getElement(i, this.getColumn()) instanceof Immovable) {
						break;
					}
					if (parentMatrix.getElement(i, this.getColumn()) instanceof Empty) {
						setFallingThroughAir(true);
						break;
					}
				}
			}
			Element nextVertical = parentMatrix.getElement(this.getRow() - delta, this.getColumn());
			int randDirection = Math.random() > 0.5 ? 1 : -1;

			Element sideways1 = parentMatrix.getElement(this.getRow(), this.getColumn() - randDirection);
			Element sideways2 = parentMatrix.getElement(this.getRow(), this.getColumn() + randDirection);

			boolean inContainer = sideways1 instanceof Immovable || sideways2 instanceof Immovable;

			nextVertical1 = parentMatrix.getElement(this.getRow() - delta, this.getColumn() - randDirection);
			nextVertical2 = parentMatrix.getElement(this.getRow() - delta, this.getColumn() + randDirection);

			if (nextVertical != null && (nextVertical.getDensity() < this.getDensity() || this.isFallingThroughAir())) {
				setFreeFalling(true);
				parentMatrix.swap(this, nextVertical);
				setDirection(randDirection);
				setHorizontalVelocity(0f);
				updateVerticalVelocity();
			} else if (nextVertical1 != null
					&& (nextVertical1.getDensity() < this.getDensity() || nextVertical1.isFallingThroughAir())
					&& this.isFreeFalling() && !inContainer) {
				parentMatrix.swap(this, nextVertical1);
				setDirection(randDirection);
			} else if (nextVertical2 != null
					&& (nextVertical2.getDensity() < this.getDensity() || nextVertical2.isFallingThroughAir())
					&& this.isFreeFalling() && !inContainer) {
				parentMatrix.swap(this, nextVertical2);
				setDirection(randDirection);
			} else {
				if (this.getHorizontalVelocity() == 0 && isFreeFalling()) {
					this.setHorizontalVelocity((float) (getVerticalVelocity() * (Math.random() + 0.2f)));
				}
				if (!(nextVertical instanceof MovableSolid) || !((Movable) nextVertical).isFreeFalling()) {
					setFreeFalling(false);
				}
				setVerticalVelocity(0f);
			}

			if (this.isFreeFalling()) {
				if (sideways1 instanceof MovableSolid) {
					setElementFreeFalling((MovableSolid) sideways1);
				}
				if (sideways2 instanceof MovableSolid) {
					setElementFreeFalling((MovableSolid) sideways2);
				}
			}

		}

		int h = getHorizontalUpdateCount();
		if (h > 0) {
			updateHorizontalVelocity();
			if (getDirection() > 0) {
				Element elementInDirection = parentMatrix.getElement(getRow(), getColumn() + 1);
				Element elementBelowDirection = parentMatrix.getElement(getRow() - 1, getColumn() + 1);
				if (elementInDirection != null && elementInDirection.getDensity() < this.getDensity()) {
					if (elementBelowDirection != null && elementBelowDirection.getDensity() < this.getDensity()) {
						parentMatrix.swap(this, elementBelowDirection);
					} else {
						parentMatrix.swap(this, elementInDirection);
					}
					this.updateHorizontalVelocity();
				} else {
					setHorizontalVelocity(0);
					setDirection(0);
				}
			} else if (getDirection() < 0) {
				Element elementInDirection = parentMatrix.getElement(getRow(), getColumn() - 1);
				Element elementBelowDirection = parentMatrix.getElement(getRow() - 1, getColumn() - 1);
				if (elementInDirection != null && elementInDirection.getDensity() < this.getDensity()) {
					if (elementBelowDirection != null && elementBelowDirection.getDensity() < this.getDensity()) {
						parentMatrix.swap(this, elementBelowDirection);
					} else {
						parentMatrix.swap(this, elementInDirection);
					}
					this.updateHorizontalVelocity();
				} else {
					setHorizontalVelocity(0);
					setDirection(0);
				}
			}
		}
	}

}
