package com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.wet_movable_solid;

import com.aidenx11.JavaPixelPhysics.CellularMatrix;
import com.aidenx11.JavaPixelPhysics.PixelPhysicsGame;
import com.aidenx11.JavaPixelPhysics.color.CustomColor;
import com.aidenx11.JavaPixelPhysics.elements.Element;
import com.aidenx11.JavaPixelPhysics.elements.Empty;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Immovable;
import com.aidenx11.JavaPixelPhysics.elements.movable.Movable;
import com.aidenx11.JavaPixelPhysics.elements.movable.liquid.Lava;
import com.aidenx11.JavaPixelPhysics.elements.movable.liquid.Water;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.Dirt;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.MovableSolid;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.Sand;

public class WetMovableSolid extends MovableSolid {

	public WetMovableSolid(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean burnsThings, float velocity,
			float acceleration, float maxSpeed, int density, boolean movesSideways, float inertialResistance,
			float friction, int temperature) {
		super(type, row, column, color, canDie, lifetime, flammable, extinguishesThings, chanceToCatch, burnsThings,
				velocity, acceleration, maxSpeed, density, movesSideways, inertialResistance, friction, temperature);
	}

	@Override
	public void update() {

		super.update();
		this.checkForDryElements();
	}

	@Override
	public void updateMovementLogic() {

		this.updateVerticalVelocity();

		boolean thisIsWetDirt = (this instanceof WetDirt);
		boolean thisIsWetSand = (this instanceof WetSand);

		Element nextVertical;
		Element nextVertical1;
		Element nextVertical2;
		Element sideways1;
		Element sideways2;

		for (int v = 0; v < this.getVerticalUpdateCount(); v++) {

			int delta = (int) Math.signum(this.getVerticalVelocity());

			nextVertical = PixelPhysicsGame.matrix.getElement(this.getRow() - delta, this.getColumn(), true, false);
			int randDirection = Math.random() > 0.5 ? 1 : -1;

			if (this.getHorizontalVelocity() > 0 && this.isFreeFalling()) {
				this.setVerticalVelocity(this.getVerticalVelocity() + this.getHorizontalVelocity());
			}

			if (this.isFallingThroughAir()) {
				CellularMatrix.activateChunk(getRow(), getColumn());
				this.setFallingThroughAir(false);
			}

			if (delta > 0) {
				for (int i = this.getRow() - delta; i >= 0; i--) {
					Element elementToCheck = PixelPhysicsGame.matrix.getElement(i, this.getColumn(), false, false);

					if (elementToCheck instanceof Immovable || elementToCheck instanceof Lava
							|| elementToCheck instanceof WetSand || elementToCheck instanceof WetDirt) {
						break;
					}
					if (elementToCheck instanceof Empty || elementToCheck instanceof Water) {
						setFallingThroughAir(true);
						break;
					}
					if (this instanceof WetSand && (elementToCheck instanceof Sand)) {
						setFallingThroughAir(true);
						break;
					}
					if (this instanceof WetDirt && (elementToCheck instanceof Dirt)) {
						setFallingThroughAir(true);
						break;
					}
				}
			}

			sideways1 = PixelPhysicsGame.matrix.getElement(this.getRow(), this.getColumn() - randDirection, false,
					true);
			sideways2 = PixelPhysicsGame.matrix.getElement(this.getRow(), this.getColumn() + randDirection, false,
					true);

			boolean inContainer = sideways1 instanceof Immovable || sideways2 instanceof Immovable;

			nextVertical1 = PixelPhysicsGame.matrix.getElement(this.getRow() - delta, this.getColumn() - randDirection,
					true, true);
			nextVertical2 = PixelPhysicsGame.matrix.getElement(this.getRow() - delta, this.getColumn() + randDirection,
					true, true);

			if (nextVertical != null && (nextVertical.getDensity() < this.getDensity() - 1
					|| ((thisIsWetDirt && nextVertical instanceof Dirt)
							|| (thisIsWetSand && nextVertical instanceof Sand)))) {

				setFreeFalling(true);
				PixelPhysicsGame.matrix.swap(this, nextVertical);
				setDirection(randDirection);
				setHorizontalVelocity(0f);
				updateVerticalVelocity();
				setVerticalVelocity(this.getVerticalVelocity() + this.getHorizontalVelocity());

			} else if (nextVertical1 != null
					&& (nextVertical1.getDensity() < this.getDensity() - 1
							|| ((nextVertical1.isFallingThroughAir()
									&& nextVertical1.getDensity() == this.getDensity()))
							|| ((thisIsWetDirt && nextVertical1 instanceof Dirt)
									|| (thisIsWetSand && nextVertical1 instanceof Sand)))
					&& (this.isFreeFalling() || this.getHorizontalVelocity() > 0) && !inContainer) {

				PixelPhysicsGame.matrix.swap(this, nextVertical1);
				setDirection(randDirection * -1);

				if (this.getHorizontalVelocity() > 0) {
					this.setElementFreeFalling(this);
				}

			} else if (nextVertical2 != null
					&& (nextVertical2.getDensity() < this.getDensity() - 1
							|| ((nextVertical2.isFallingThroughAir())
									&& nextVertical2.getDensity() == this.getDensity())
							|| ((thisIsWetDirt && nextVertical2 instanceof Dirt)
									|| (thisIsWetSand && nextVertical2 instanceof Sand)))
					&& (this.isFreeFalling() || this.getHorizontalVelocity() > 0) && !inContainer) {

				PixelPhysicsGame.matrix.swap(this, nextVertical2);
				setDirection(randDirection);

				if (this.getHorizontalVelocity() > 0) {
					this.setElementFreeFalling(this);
				}

			} else {

				this.updateHorizontalVelocity();
				this.setFreeFalling(false);

				if (this.getHorizontalVelocity() == 0 && this.getVerticalVelocity() > 0) {
					this.setHorizontalVelocity((float) (this.getDirection() * getVerticalVelocity() * Math.random()));
				} else {
					for (int i = 0; i < getHorizontalUpdateCount(); i++) {

						if (getDirection() != 0) {

							Element elementInDirection = PixelPhysicsGame.matrix.getElement(getRow(),
									getColumn() + getDirection(), false, true);
							Element elementBelowDirection = PixelPhysicsGame.matrix.getElement(getRow() - 1,
									getColumn() + getDirection(), true, true);

							if (elementInDirection != null && (elementInDirection.getDensity() < this.getDensity() - 1
									|| ((thisIsWetDirt && elementInDirection instanceof Dirt)
											|| (thisIsWetSand && elementInDirection instanceof Sand)))) {

								if (elementBelowDirection != null
										&& (elementBelowDirection.getDensity() < this.getDensity() - 1
												|| ((thisIsWetDirt && elementBelowDirection instanceof Dirt)
														|| (thisIsWetSand && elementBelowDirection instanceof Sand)))) {

									PixelPhysicsGame.matrix.swap(this, elementBelowDirection);

									nextVertical = PixelPhysicsGame.matrix.getElement(getRow() - 1, getColumn(), true,
											false);

									if (nextVertical instanceof Empty) {
										CellularMatrix.activateChunk(getRow(), getColumn());
									}

								} else {

									PixelPhysicsGame.matrix.swap(this, elementInDirection);

									nextVertical = PixelPhysicsGame.matrix.getElement(getRow() - 1, getColumn(), true,
											false);

									if (nextVertical instanceof Empty) {
										CellularMatrix.activateChunk(getRow(), getColumn());
									}

								}

							} else {

								setHorizontalVelocity(0);
								setDirection(0);
								break;
							}

						}
						updateHorizontalVelocity();
					}

				}

				if (!(nextVertical instanceof MovableSolid) || !((Movable) nextVertical).isFreeFalling()) {
					setFreeFalling(false);
				}

				this.resetVelocity();

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

	}

	public void checkForDryElements() {
		Element[] elementsBelow = PixelPhysicsGame.matrix.getAdjacentElements(this, false, false, true);

		for (int i = 0; i < elementsBelow.length; i++) {
			Element currentElement = elementsBelow[i];

			if (this instanceof WetDirt && currentElement instanceof Sand) {
				PixelPhysicsGame.matrix.setNewElement(currentElement, ElementTypes.WET_SAND);
				PixelPhysicsGame.matrix.setNewElement(this, ElementTypes.DIRT);
				return;
			}
			if (this instanceof WetSand && currentElement instanceof Dirt) {
				PixelPhysicsGame.matrix.setNewElement(currentElement, ElementTypes.WET_DIRT);
				PixelPhysicsGame.matrix.setNewElement(this, ElementTypes.SAND);
				return;
			}
		}
	}

}
