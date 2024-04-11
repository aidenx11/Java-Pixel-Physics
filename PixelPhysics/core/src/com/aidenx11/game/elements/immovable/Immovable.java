package com.aidenx11.game.elements.immovable;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.elements.Element;

public class Immovable extends Element {

	public Immovable(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean burnsThings, int temperature) {
		super(type, row, column, color, canDie, lifetime, flammable, extinguishesThings, chanceToCatch, false,
				temperature);
		super.setDensity(999f);
	}

	@Override
	public void update() {
		super.updateBurningLogic();
		if (this.limitedLife()) {
			super.updateElementLife();
		}
	}
	
	
}
