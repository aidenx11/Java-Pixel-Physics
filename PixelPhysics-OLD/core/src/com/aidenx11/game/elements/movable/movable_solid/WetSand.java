package com.aidenx11.game.elements.movable.movable_solid;

import com.aidenx11.game.PixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class WetSand extends MovableSolid {

	public static ElementTypes type = ElementTypes.WET_SAND;
	private static float acceleration = PixelPhysicsGame.GRAVITY_ACCELERATION - 0.05f;
	private static float maxSpeed = 3f;
	private static int density = 8;
	private static float inertialResistance = 0.01f;
	private static float friction = 1f;

	public WetSand(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.WET_SAND, true), false, -1, false, true, 0, false, 1f,
				acceleration, maxSpeed, density, false, inertialResistance, friction, -1);
		super.setFreeFalling(true);
	}
	
}
