package com.aidenx11.game.elements;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Obsidian extends MovableSolid {

	public static ElementTypes type = ElementTypes.OBSIDIAN;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 5f;
	private static float density = 9f;
	private static float inertialResistance = 1;
	private static float friction = 0.5f;

	public Obsidian(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.OBSIDIAN, true), false, -1, false, true, 0, false, 0,
				acceleration, maxSpeed, density, false, inertialResistance, friction, -1);
		super.setFreeFalling(false);
	}

	@Override
	public void updateMovementLogic() {
		this.updateVerticalVelocity();

		for (int v = 0; v < this.getVerticalUpdateCount(); v++) {

			Element nextVertical1;
			Element nextVertical2;

			int delta = (int) Math.signum(this.getVerticalVelocity());
			Element nextVertical = parentMatrix.getElement(this.getRow() - delta, this.getColumn());
			int randDirection = Math.random() > 0.5 ? 1 : -1;

			Element sideways1 = parentMatrix.getElement(this.getRow(), this.getColumn() - randDirection);
			Element sideways2 = parentMatrix.getElement(this.getRow(), this.getColumn() + randDirection);

			boolean inContainer = sideways1 instanceof Immovable || sideways2 instanceof Immovable;

			nextVertical1 = parentMatrix.getElement(this.getRow() - delta, this.getColumn() - randDirection);
			nextVertical2 = parentMatrix.getElement(this.getRow() - delta, this.getColumn() + randDirection);

			if (nextVertical != null && (nextVertical instanceof Water)
					&& nextVertical.getDensity() < this.getDensity()) {
				if (nextVertical instanceof Liquid) {
					this.setVerticalVelocity(0.01f);
				}
				setFreeFalling(true);
				parentMatrix.swap(this, nextVertical);
				setDirection(randDirection);
				setHorizontalVelocity(0f);
				updateVerticalVelocity();
			} else if (nextVertical1 != null && (nextVertical1 instanceof Water)
					&& nextVertical1.getDensity() < this.getDensity() && this.isFreeFalling() && !inContainer) {
				
				parentMatrix.swap(this, nextVertical1);
				setDirection(randDirection);
			} else if (nextVertical2 != null && (nextVertical2 instanceof Water)
					&& nextVertical2.getDensity() < this.getDensity() && this.isFreeFalling() && !inContainer) {
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
				if (sideways1 instanceof MovableSolid && (sideways1 instanceof Water || sideways1 instanceof Lava)) {
					setElementFreeFalling((MovableSolid) sideways1);
				}
				if (sideways2 instanceof MovableSolid && (sideways2 instanceof Water || sideways2 instanceof Lava)) {
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
