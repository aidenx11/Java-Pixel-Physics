package com.aidenx11.JavaPixelPhysics.elements.movable.liquid;

import com.aidenx11.JavaPixelPhysics.PixelPhysicsGame;
import com.aidenx11.JavaPixelPhysics.color.CustomColor;
import com.aidenx11.JavaPixelPhysics.elements.Element;
import com.aidenx11.JavaPixelPhysics.elements.movable.Movable;
import com.aidenx11.JavaPixelPhysics.elements.movable.gas.Gas;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.MovableSolid;
import com.aidenx11.JavaPixelPhysics.elements.Empty;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Immovable;

/**
 * Class to manage Liquids. Unlike movable solids, liquids have a dispersion
 * rate and must check elements to the side to make sure they do not teleport
 * through containers.
 * 
 * @author Aiden Schroeder
 */
public class Liquid extends Movable {

	private int dispersionRate;

	public Liquid(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean burnsThings, float velocity,
			float acceleration, float maxSpeed, int density, boolean movesSideways, int dispersionRate,
			int meltingPoint) {
		super(type, row, column, color, canDie, lifetime, flammable, extinguishesThings, chanceToCatch, burnsThings,
				velocity, acceleration, maxSpeed, density, movesSideways, true, 0f, meltingPoint);
		super.setFreeFalling(true);
		setDispersionRate(dispersionRate);
	}

	@Override
	public void updateMovementLogic() {
		this.updateVerticalVelocity();

		for (int v = 0; v < this.getVerticalUpdateCount(); v++) {

			int randDirection = Math.random() > 0.5 ? 1 : -1;

			if (this.isFallingThroughAir()) {
				this.setFallingThroughAir(false);
			}
			
			int dispersionRate = (int) Math.round(this.getDispersionRate() * Math.random() + 0.5);

			Element nextVertical1 = null;
			Element nextVertical2 = null;
			Element nextVertical = null;

			int delta = (int) Math.signum(this.getVerticalVelocity());

			nextVertical = PixelPhysicsGame.matrix.getElement(this.getRow() - delta, this.getColumn(), true, false);

			if (!(nextVertical instanceof Immovable)) {
				for (int i = 0; i <= dispersionRate; i++) {
					Element currentElement = PixelPhysicsGame.matrix.getElement(this.getRow() - delta,
							this.getColumn() - randDirection * i, true, true);
					if (currentElement instanceof Immovable) {
						break;
					}

					nextVertical1 = currentElement;

				}

				for (int i = 0; i <= dispersionRate; i++) {
					Element currentElement = PixelPhysicsGame.matrix.getElement(this.getRow() - delta,
							this.getColumn() + randDirection * i, true, true);
					if (currentElement instanceof Immovable) {
						break;
					}

					nextVertical2 = currentElement;

				}
			}

			for (int i = this.getRow() - delta; i >= 0; i--) {
				Element elementToCheck = PixelPhysicsGame.matrix.getElement(i, this.getColumn(), false, false);
				if (elementToCheck instanceof Immovable) {
					break;
				}
				if (elementToCheck instanceof Empty) {
					setFallingThroughAir(true);
					break;
				}

			}

			if (nextVertical != null && nextVertical.getDensity() < this.getDensity()) {

				PixelPhysicsGame.matrix.swap(this, nextVertical);

			} else if (nextVertical1 != null && !(nextVertical1 instanceof Gas)
					&& (nextVertical1.getDensity() < this.getDensity() || (nextVertical1.isFallingThroughAir()
							&& nextVertical1.getDensity() == this.getDensity()))) {

				PixelPhysicsGame.matrix.swap(this, nextVertical1);

			} else if (nextVertical2 != null && !(nextVertical2 instanceof Gas)
					&& (nextVertical2.getDensity() < this.getDensity() || (nextVertical2.isFallingThroughAir()
							&& nextVertical2.getDensity() == this.getDensity()))) {

				PixelPhysicsGame.matrix.swap(this, nextVertical2);

			} else {

				this.resetVelocity();

				Element sideways1 = PixelPhysicsGame.matrix.getElement(this.getRow(), this.getColumn() - randDirection,
						false, true);
				Element sideways2 = PixelPhysicsGame.matrix.getElement(this.getRow(), this.getColumn() + randDirection,
						false, true);

				for (int i = 0; i <= dispersionRate; i++) {
					Element currentElement = PixelPhysicsGame.matrix.getElement(this.getRow(),
							this.getColumn() - randDirection * i, false, true);

					if (currentElement instanceof Immovable) {
						break;
					}

					sideways1 = currentElement;

				}

				for (int i = 0; i <= dispersionRate; i++) {
					Element currentElement = PixelPhysicsGame.matrix.getElement(this.getRow(),
							this.getColumn() + randDirection * i, false, true);

					if (currentElement instanceof Immovable) {
						break;
					}

					sideways2 = currentElement;

				}

				if (sideways1 instanceof MovableSolid) {
					setElementFreeFalling((MovableSolid) sideways1);
				}

				if (sideways2 instanceof MovableSolid) {
					setElementFreeFalling((MovableSolid) sideways2);
				}

				if (sideways1 != null && sideways1.getDensity() < this.getDensity()) {
					PixelPhysicsGame.matrix.swap(this, sideways1);
				} else if (sideways2 != null && sideways2.getDensity() < this.getDensity()) {
					PixelPhysicsGame.matrix.swap(this, sideways2);
				}
			}

		}

	}

	public int getDispersionRate() {
		return dispersionRate;
	}

	public void setDispersionRate(int dispersionRate) {
		this.dispersionRate = dispersionRate;
	}

}
