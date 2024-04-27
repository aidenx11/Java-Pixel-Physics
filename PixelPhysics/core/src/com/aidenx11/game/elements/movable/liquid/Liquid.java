package com.aidenx11.game.elements.movable.liquid;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.movable.Movable;
import com.aidenx11.game.elements.movable.gas.Gas;
import com.aidenx11.game.elements.movable.movable_solid.MovableSolid;
import com.aidenx11.game.elements.Empty;

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

			Element nextVertical1 = null;
			Element nextVertical2 = null;

			int delta = (int) Math.signum(this.getVerticalVelocity());
			Element nextVertical = pixelPhysicsGame.matrix.getElement(this.getRow() - delta, this.getColumn());
			int randDirection = Math.random() > 0.5 ? 1 : -1;

			for (int i = 0; i <= dispersionRate; i++) {
				Element currentElement = pixelPhysicsGame.matrix.getElement(this.getRow() - delta,
						this.getColumn() - randDirection * i);
				if ((!(currentElement instanceof Empty) && !(currentElement instanceof Liquid))
						|| i == dispersionRate) {
					nextVertical1 = pixelPhysicsGame.matrix.getElement(this.getRow() - delta,
							this.getColumn() - randDirection * (i));
					break;
				}
			}

			for (int i = 0; i <= dispersionRate; i++) {
				Element currentElement = pixelPhysicsGame.matrix.getElement(this.getRow() - delta,
						this.getColumn() + randDirection * i);
				if ((!(currentElement instanceof Empty) && !(currentElement instanceof Liquid))
						|| i == dispersionRate) {
					nextVertical2 = pixelPhysicsGame.matrix.getElement(this.getRow() - delta,
							this.getColumn() + randDirection * (i));
					break;
				}
			}

			if (nextVertical != null && nextVertical.getDensity() < this.getDensity()) {
				pixelPhysicsGame.matrix.swap(this, nextVertical);
			} else if (nextVertical1 != null && !(nextVertical1 instanceof Gas)
					&& nextVertical1.getDensity() < this.getDensity()) {
				pixelPhysicsGame.matrix.swap(this, nextVertical1);
			} else if (nextVertical2 != null && !(nextVertical2 instanceof Gas)
					&& nextVertical2.getDensity() < this.getDensity()) {
				pixelPhysicsGame.matrix.swap(this, nextVertical2);
			} else {
				this.resetVelocity();
			}

			Element sideways1 = pixelPhysicsGame.matrix.getElement(this.getRow(), this.getColumn() - randDirection);
			Element sideways2 = pixelPhysicsGame.matrix.getElement(this.getRow(), this.getColumn() + randDirection);

			if (sideways1 instanceof MovableSolid) {
				setElementFreeFalling((MovableSolid) sideways1);
			}

			if (sideways2 instanceof MovableSolid) {
				setElementFreeFalling((MovableSolid) sideways2);
			}

			if (sideways1 != null && sideways1.getDensity() < this.getDensity()) {
				pixelPhysicsGame.matrix.swap(this, sideways1);
			} else if (sideways2 != null && sideways2.getDensity() < this.getDensity()) {
				pixelPhysicsGame.matrix.swap(this, sideways2);
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
