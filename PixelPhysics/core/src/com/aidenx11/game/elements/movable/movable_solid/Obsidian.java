package com.aidenx11.game.elements.movable.movable_solid;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;
import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.Empty;
import com.aidenx11.game.elements.immovable.Immovable;
import com.aidenx11.game.elements.movable.Movable;
import com.aidenx11.game.elements.movable.liquid.Lava;
import com.aidenx11.game.elements.movable.liquid.Water;

public class Obsidian extends MovableSolid {

	public static ElementTypes type = ElementTypes.OBSIDIAN;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 8f;
	private static int density = 8;
	private static float inertialResistance = 0.995f;
	private static float friction = 0.7f;
	
	public Obsidian(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.OBSIDIAN, true), false, -1, false, true, 0, false, 0,
				acceleration, maxSpeed, density, false, inertialResistance, friction, -1);
		super.setFreeFalling(true);
	}

	@Override
	public void updateMovementLogic() {
		this.updateVerticalVelocity();

		for (int v = 0; v < this.getVerticalUpdateCount(); v++) {

			int delta = (int) Math.signum(this.getVerticalVelocity());

			Element nextVertical = parentMatrix.getElement(this.getRow() - delta, this.getColumn());
			int randDirection = Math.random() > 0.5 ? 1 : -1;

			if (this.getHorizontalVelocity() > 0 && this.isFreeFalling()) {
				this.setVerticalVelocity(this.getVerticalVelocity() + this.getHorizontalVelocity());
			}

			Element nextVertical1;
			Element nextVertical2;

			if (this.isFallingThroughAir()) {
				this.setFallingThroughAir(false);
			}

			if (this.MovedLastFrame()) {
				this.setMovedLastFrame(false);
			}

			if (delta > 0) {
				for (int i = this.getRow() - delta; i >= 0; i--) {
					if (parentMatrix.getElement(i, this.getColumn()) instanceof Immovable
							|| parentMatrix.getElement(i, this.getColumn()) instanceof Lava) {
						break;
					}
					if (parentMatrix.getElement(i, this.getColumn()) instanceof Empty
							|| parentMatrix.getElement(i, this.getColumn()) instanceof Water) {
						setFallingThroughAir(true);
						break;
					}
					
				}
			} else if (delta < 0) {
				for (int i = this.getRow() - delta; i <= CellularMatrix.rows; i++) {
					if (parentMatrix.getElement(i, this.getColumn()) instanceof Immovable
							|| parentMatrix.getElement(i, this.getColumn()) instanceof Lava) {
						break;
					}
					if (parentMatrix.getElement(i, this.getColumn()) instanceof Empty
							|| parentMatrix.getElement(i, this.getColumn()) instanceof Water) {
						setFallingThroughAir(true);
						break;
					}
				}
			}

			Element sideways1 = parentMatrix.getElement(this.getRow(), this.getColumn() - randDirection);
			Element sideways2 = parentMatrix.getElement(this.getRow(), this.getColumn() + randDirection);

			boolean inContainer = sideways1 instanceof Immovable || sideways2 instanceof Immovable;

			nextVertical1 = parentMatrix.getElement(this.getRow() - delta, this.getColumn() - randDirection);
			nextVertical2 = parentMatrix.getElement(this.getRow() - delta, this.getColumn() + randDirection);

			if (nextVertical != null && (nextVertical.getDensity() < this.getDensity() - 1)) {

				setFreeFalling(true);
				parentMatrix.swap(this, nextVertical);
				setDirection(randDirection);
				setHorizontalVelocity(0f);
				updateVerticalVelocity();
				setVerticalVelocity(this.getVerticalVelocity() + this.getHorizontalVelocity());
				setMovedLastFrame(true);

			} else if (nextVertical1 != null
					&& (nextVertical1.getDensity() < this.getDensity() - 1
							|| (nextVertical1.isFallingThroughAir() && nextVertical1.getDensity() == this.getDensity()))
					&& (this.isFreeFalling() || this.getHorizontalVelocity() > 0) && !inContainer) {

				parentMatrix.swap(this, nextVertical1);
				setDirection(randDirection * -1);
				setMovedLastFrame(true);
				if (this.getHorizontalVelocity() > 0) {
					this.setElementFreeFalling(this);
				}

			} else if (nextVertical2 != null
					&& (nextVertical2.getDensity() < this.getDensity() - 1
							|| (nextVertical2.isFallingThroughAir()) && nextVertical2.getDensity() == this.getDensity())
					&& (this.isFreeFalling() || this.getHorizontalVelocity() > 0) && !inContainer) {

				parentMatrix.swap(this, nextVertical2);
				setDirection(randDirection);
				setMovedLastFrame(true);
				if (this.getHorizontalVelocity() > 0) {
					this.setElementFreeFalling(this);
				}

			} else {

				this.updateHorizontalVelocity();

				if (this.getHorizontalVelocity() == 0 && this.getVerticalVelocity() > 0) {
					this.setHorizontalVelocity((float) (this.getDirection() * getVerticalVelocity() * Math.random()));
				} else {
					for (int i = 0; i < getHorizontalUpdateCount(); i++) {

						this.setMovedLastFrame(false);

						if (getDirection() != 0) {

							Element elementInDirection = parentMatrix.getElement(getRow(),
									getColumn() + getDirection());
							Element elementBelowDirection = parentMatrix.getElement(getRow() - 1,
									getColumn() + getDirection());

							if (elementInDirection != null && elementInDirection.getDensity() <= this.getDensity()) {

								if (elementBelowDirection != null
										&& elementBelowDirection.getDensity() <= this.getDensity()) {

									parentMatrix.swap(this, elementBelowDirection);
									setMovedLastFrame(true);

								} else {

									parentMatrix.swap(this, elementInDirection);
									setMovedLastFrame(true);

								}

							} else {

								setHorizontalVelocity(0);
								setDirection(0);
								break;
							}

						} else {

							setHorizontalVelocity(0);
							setDirection(0);
							break;
						}
						updateHorizontalVelocity();
					}

				}
				if (!(nextVertical instanceof MovableSolid) || !((Movable) nextVertical).isFreeFalling()) {
					setFreeFalling(false);
				}
				this.setVerticalVelocity(0);

			}

			if (this.isFreeFalling()) {
				if (sideways1 instanceof MovableSolid) {
					setElementFreeFalling((MovableSolid) sideways1);
					((MovableSolid) sideways1).setMovedLastFrame(true);
				}
				if (sideways2 instanceof MovableSolid) {
					setElementFreeFalling((MovableSolid) sideways2);
					((MovableSolid) sideways2).setMovedLastFrame(true);
				}
			}

		}

	}

}
