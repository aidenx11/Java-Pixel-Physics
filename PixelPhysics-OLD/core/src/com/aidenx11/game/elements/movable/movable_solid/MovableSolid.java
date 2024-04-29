package com.aidenx11.game.elements.movable.movable_solid;

import com.aidenx11.game.PixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.Empty;
import com.aidenx11.game.elements.immovable.Immovable;
import com.aidenx11.game.elements.movable.Movable;
import com.aidenx11.game.elements.movable.liquid.Lava;
import com.aidenx11.game.elements.movable.liquid.Water;

public abstract class MovableSolid extends Movable {

	public MovableSolid(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean burnsThings, float velocity,
			float acceleration, float maxSpeed, int density, boolean movesSideways, float inertialResistance,
			float friction, int temperature) {
		super(type, row, column, color, canDie, lifetime, flammable, extinguishesThings, chanceToCatch, burnsThings,
				velocity, acceleration, maxSpeed, density, movesSideways, true, friction, temperature);
		this.setInertialResistance(inertialResistance);
	}

	@Override
	public void updateMovementLogic() {
		this.updateVerticalVelocity();

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
				this.setFallingThroughAir(false);
			}

			if (delta > 0) {
				for (int i = this.getRow() - delta; i >= 0; i--) {
					Element elementToCheck = PixelPhysicsGame.matrix.getElement(i, this.getColumn(), false, false);
					
					if (elementToCheck instanceof Immovable
							|| elementToCheck instanceof Lava) {
						break;
					}
					if (elementToCheck instanceof Empty
							|| elementToCheck instanceof Water) {
						setFallingThroughAir(true);
						break;
					}
					if (this instanceof WetSand && (elementToCheck instanceof Sand
							|| elementToCheck instanceof Dirt)) {
						setFallingThroughAir(true);
						break;
					}
					if (this instanceof WetDirt && (elementToCheck instanceof Sand
							|| elementToCheck instanceof Dirt)) {
						setFallingThroughAir(true);
						break;
					}
				}
			}

			sideways1 = PixelPhysicsGame.matrix.getElement(this.getRow(), this.getColumn() - randDirection, false, true);
			sideways2 = PixelPhysicsGame.matrix.getElement(this.getRow(), this.getColumn() + randDirection, false, true);

			boolean inContainer = sideways1 instanceof Immovable || sideways2 instanceof Immovable;

			nextVertical1 = PixelPhysicsGame.matrix.getElement(this.getRow() - delta, this.getColumn() - randDirection, true, true);
			nextVertical2 = PixelPhysicsGame.matrix.getElement(this.getRow() - delta, this.getColumn() + randDirection, true, true);

			if (nextVertical != null && (nextVertical.getDensity() < this.getDensity())) {

				setFreeFalling(true);
				PixelPhysicsGame.matrix.swap(this, nextVertical);
				setDirection(randDirection);
				setHorizontalVelocity(0f);
				updateVerticalVelocity();
				setVerticalVelocity(this.getVerticalVelocity() + this.getHorizontalVelocity());

			} else if (nextVertical1 != null
					&& (nextVertical1.getDensity() < this.getDensity()
							|| (nextVertical1.isFallingThroughAir() && nextVertical1.getDensity() == this.getDensity()))
					&& (this.isFreeFalling() || this.getHorizontalVelocity() > 0) && !inContainer) {

				PixelPhysicsGame.matrix.swap(this, nextVertical1);
				setDirection(randDirection * -1);

				if (this.getHorizontalVelocity() > 0) {
					this.setElementFreeFalling(this);
				}

			} else if (nextVertical2 != null
					&& (nextVertical2.getDensity() < this.getDensity()
							|| (nextVertical2.isFallingThroughAir()) && nextVertical2.getDensity() == this.getDensity())
					&& (this.isFreeFalling() || this.getHorizontalVelocity() > 0) && !inContainer) {

				PixelPhysicsGame.matrix.swap(this, nextVertical2);
				setDirection(randDirection);

				if (this.getHorizontalVelocity() > 0) {
					this.setElementFreeFalling(this);
				}

			} else {

				this.updateHorizontalVelocity();

				if (this.getHorizontalVelocity() == 0 && this.getVerticalVelocity() > 0) {
					this.setHorizontalVelocity((float) (this.getDirection() * getVerticalVelocity() * Math.random()));
				} else {
					for (int i = 0; i < getHorizontalUpdateCount(); i++) {

						if (getDirection() != 0) {

							Element elementInDirection = PixelPhysicsGame.matrix.getElement(getRow(),
									getColumn() + getDirection(), false, true);
							Element elementBelowDirection = PixelPhysicsGame.matrix.getElement(getRow() - 1,
									getColumn() + getDirection(), true, true);

							if (elementInDirection != null && (elementInDirection.getDensity() < this.getDensity())) {

								if (elementBelowDirection != null
										&& (elementBelowDirection.getDensity() < this.getDensity())) {

									PixelPhysicsGame.matrix.swap(this, elementBelowDirection);

								} else {

									PixelPhysicsGame.matrix.swap(this, elementInDirection);

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
}
