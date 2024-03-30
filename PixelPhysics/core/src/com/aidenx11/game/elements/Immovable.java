package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;

public class Immovable extends Element {

	public Immovable(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean burnsThings) {
		super(type, row, column, color, canDie, lifetime, flammable, extinguishesThings, chanceToCatch, burnsThings,
				false);
	}

	@Override
	public void update() {
		super.updateBurningLogic();
		if (this.limitedLife()) {
			super.updateElementLife();
		}
	}
	
	@Override
	public float getDensity() {
		return 999;
	}

}
