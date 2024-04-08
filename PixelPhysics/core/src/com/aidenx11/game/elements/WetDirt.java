package com.aidenx11.game.elements;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class WetDirt extends MovableSolid {

	public static ElementTypes type = ElementTypes.DIRT;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION - 0.05f;
	private static float maxSpeed = 3f;
	private static float density = 8f;
	private static float inertialResistance = 0.7f;
	private static float friction = 0.9f;

	public WetDirt(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.DIRT, true), false, -1, false, Math.random() < 0.8, 0, false,
				1f, acceleration, maxSpeed, density, false, inertialResistance, friction, -1);
		super.setFreeFalling(true);
	}
	
	@Override
	public void update() {
		this.causeRust();
		super.update();
	}

}
