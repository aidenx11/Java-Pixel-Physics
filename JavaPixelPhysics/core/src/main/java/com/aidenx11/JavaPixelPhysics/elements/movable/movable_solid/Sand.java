package com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid;

import com.aidenx11.JavaPixelPhysics.PixelPhysicsGame;
import com.aidenx11.JavaPixelPhysics.color.CustomColor;
import com.aidenx11.JavaPixelPhysics.color.CustomColor.ColorValues;

public class Sand extends MovableSolid {

	public static ElementTypes type = ElementTypes.SAND;
	private static float acceleration = PixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 8f;
	private static int density = 7;
	private static float inertialResistance = 0.01f;
	private static float friction = 0.05f;
	
	public static CustomColor color = new CustomColor(ColorValues.SAND_COLOR, false);

	public Sand(int row, int column) {
		super(type, row, column, color, false, -1, false, true, 0, false, 0,
				acceleration, maxSpeed, density, false, inertialResistance, friction, -1);
		super.setFreeFalling(true);
	}

}
