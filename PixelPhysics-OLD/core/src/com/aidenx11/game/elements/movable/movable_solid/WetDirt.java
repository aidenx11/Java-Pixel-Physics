package com.aidenx11.game.elements.movable.movable_solid;

import com.aidenx11.game.PixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class WetDirt extends MovableSolid {

	public static ElementTypes type = ElementTypes.DIRT;
	private static float acceleration = PixelPhysicsGame.GRAVITY_ACCELERATION - 0.05f;
	private static float maxSpeed = 3f;
	private static int density = 8;
	private static float inertialResistance = 0.7f;
	private static float friction = 0.9f;

	public WetDirt(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.DIRT, true), false, -1, false, Math.random() < 0.8, 0, false,
				1f, acceleration, maxSpeed, density, false, inertialResistance, friction, -1);
		super.setFreeFalling(true);
	}

}
