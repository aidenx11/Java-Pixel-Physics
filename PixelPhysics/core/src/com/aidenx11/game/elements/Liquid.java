package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;

public class Liquid extends Movable {

	public Liquid(ElementTypes type, int row, int column, CustomColor color, boolean canDie, int lifetime,
			boolean flammable, boolean extinguishesThings, float chanceToCatch, boolean burnsThings, float velocity,
			float acceleration, float maxSpeed, float density, boolean movesSideways) {
		super(type, row, column, color, canDie, lifetime, flammable, extinguishesThings, chanceToCatch, burnsThings,
				velocity, acceleration, maxSpeed, density, movesSideways, true, 0f);
		super.setFreeFalling(true);
	}

}
