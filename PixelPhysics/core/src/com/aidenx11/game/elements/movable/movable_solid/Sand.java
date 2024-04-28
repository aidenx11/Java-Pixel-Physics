package com.aidenx11.game.elements.movable.movable_solid;

import com.aidenx11.game.PixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Sand extends MovableSolid {

	public static ElementTypes type = ElementTypes.SAND;
	private static float acceleration = PixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 8f;
	private static int density = 7;
	private static float inertialResistance = 0.01f;
	private static float friction = 0.05f;

	public Sand(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.SAND_COLOR, true), false, -1, false, true, 0, false, 0,
				acceleration, maxSpeed, density, false, inertialResistance, friction, -1);
		super.setFreeFalling(true);
	}

}
