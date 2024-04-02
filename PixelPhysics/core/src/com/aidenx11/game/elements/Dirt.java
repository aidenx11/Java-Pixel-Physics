package com.aidenx11.game.elements;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Dirt extends MovableSolid {
	
	public static ElementTypes type = ElementTypes.DIRT;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 5f;
	private static float density = 7f;
	private static float inertialResistance = 0.4f;
	private static float friction = 0.3f;

	public Dirt(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.DIRT, true), false, -1, false, Math.random() < 0.8, 0, false,
				0, acceleration, maxSpeed, density, false, inertialResistance, friction);
		super.setFreeFalling(true);
	}

}