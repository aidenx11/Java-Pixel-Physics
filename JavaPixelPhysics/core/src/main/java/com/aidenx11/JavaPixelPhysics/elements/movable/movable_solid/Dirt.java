package com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid;

import com.aidenx11.JavaPixelPhysics.PixelPhysicsGame;
import com.aidenx11.JavaPixelPhysics.color.CustomColor;
import com.aidenx11.JavaPixelPhysics.color.CustomColor.ColorValues;

public class Dirt extends MovableSolid {

	public static ElementTypes type = ElementTypes.DIRT;
	private static float acceleration = PixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 8f;
	private static int density = 7;
	private static float inertialResistance = 0.5f;
	private static float friction = 0.4f;
	private static int meltingPoint = -1;
	
	public static CustomColor color = new CustomColor(ColorValues.WET_DIRT, true);

	public Dirt(int row, int column) {
		super(type, row, column, color, false, -1, false, Math.random() < 0.8, 0,
				false, 0, acceleration, maxSpeed, density, false, inertialResistance, friction, meltingPoint);
		super.setFreeFalling(true);
	}

}
