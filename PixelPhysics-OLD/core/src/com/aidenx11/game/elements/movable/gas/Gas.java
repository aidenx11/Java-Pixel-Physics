package com.aidenx11.game.elements.movable.gas;

import com.aidenx11.game.PixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.movable.Movable;
import com.aidenx11.game.elements.movable.movable_solid.MovableSolid;

/**
 * Class to manage all Gas elements. Gas elements move upwards instead of
 * downwards, and turn into empty elements over time.
 * 
 * @author Aiden Schroeder
 */
public class Gas extends Movable {

	public Gas(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean burnsThings, float velocity,
			float acceleration, float maxSpeed, int density, boolean movesSideways) {
		super(type, row, column, color, canDie, lifetime, flammable, extinguishesThings, chanceToCatch, burnsThings,
				velocity, acceleration, maxSpeed, density, movesSideways, false, 0f, 0);
		super.setFreeFalling(true);
	}

	/**
	 * Overrides the updateMovementLogic method in Movable to make the method
	 * simpler. Gasses do not have inertia or friction, and thus do not need to
	 * worry about horizontal velocity or inertia.
	 */
	@Override
	public void updateMovementLogic() {
		this.updateVerticalVelocity();

		for (int v = 0; v < this.getVerticalUpdateCount(); v++) {

			Element nextVertical1;
			Element nextVertical2;

			int delta = (int) Math.signum(this.getVerticalVelocity());
			Element nextVertical = PixelPhysicsGame.matrix.getElement(this.getRow() - delta, this.getColumn(), true, false);
			int randDirection = Math.random() > 0.5 ? 1 : -1;

			nextVertical1 = PixelPhysicsGame.matrix.getElement(this.getRow() - delta, this.getColumn() - randDirection, true, true);
			nextVertical2 = PixelPhysicsGame.matrix.getElement(this.getRow() - delta, this.getColumn() + randDirection, true, true);

			Element sideways1 = PixelPhysicsGame.matrix.getElement(this.getRow(), this.getColumn() - randDirection, false, true);
			Element sideways2 = PixelPhysicsGame.matrix.getElement(this.getRow(), this.getColumn() + randDirection, false, true);

			if (nextVertical != null && nextVertical.getDensity() < this.getDensity()) {
				PixelPhysicsGame.matrix.swap(this, nextVertical);
			} else if (nextVertical1 != null && nextVertical1.getDensity() < this.getDensity()) {
				PixelPhysicsGame.matrix.swap(this, nextVertical1);
			} else if (nextVertical2 != null && nextVertical2.getDensity() < this.getDensity()
					&& this.isFreeFalling()) {
				PixelPhysicsGame.matrix.swap(this, nextVertical2);
			} else {
				this.resetVelocity();
			}

			if (sideways1 instanceof MovableSolid) {
				setElementFreeFalling((MovableSolid) sideways1);
			}

			if (sideways2 instanceof MovableSolid) {
				setElementFreeFalling((MovableSolid) sideways2);
			}

			if (this.movesSideways()) {

				sideways1 = PixelPhysicsGame.matrix.getElement(this.getRow(), this.getColumn() - randDirection, false, true);
				sideways2 = PixelPhysicsGame.matrix.getElement(this.getRow(), this.getColumn() + randDirection, false, true);

				if (sideways1 != null && sideways1.getDensity() < this.getDensity()) {
					PixelPhysicsGame.matrix.swap(this, sideways1);
				} else if (sideways2 != null && sideways2.getDensity() < this.getDensity()) {
					PixelPhysicsGame.matrix.swap(this, sideways2);
				}
			}

		}
	}

}
