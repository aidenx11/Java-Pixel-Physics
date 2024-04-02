package com.aidenx11.game.elements;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class WetSand extends MovableSolid {

	public static ElementTypes type = ElementTypes.WET_SAND;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 2f;
	private static float density = 8f;
	private static float inertialResistance = 0.5f;
	private static float friction = 1f;

	public WetSand(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.WET_SAND, true), false, 0, false, true, 0, false, 0,
				acceleration, maxSpeed, density, false, inertialResistance, friction);
		super.setFreeFalling(true);
	}
	
	
}
