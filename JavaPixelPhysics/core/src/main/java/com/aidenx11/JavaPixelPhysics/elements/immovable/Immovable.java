package com.aidenx11.JavaPixelPhysics.elements.immovable;

import com.aidenx11.JavaPixelPhysics.color.CustomColor;
import com.aidenx11.JavaPixelPhysics.elements.Element;

/**
 * Class to manage all Immovable elements. Immovable elements do not move, and
 * have a density of 999 so as to not affect movable elements.
 * 
 * @author Aiden Schroeder
 */
public class Immovable extends Element {

	public Immovable(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean burnsThings, int temperature) {
		super(type, row, column, color, canDie, lifetime, flammable, extinguishesThings, chanceToCatch, false,
				temperature);
		super.setDensity(999);
	}

	/**
	 * Since Immovable elements do not move, only updates burning and lifetime logic
	 */
	@Override
	public void update() {
		if (this.limitedLife()) {
			super.updateElementLife();
		}
		if (!this.isOnFire()) {
			super.updateBurningLogic();
		}

	}

}
