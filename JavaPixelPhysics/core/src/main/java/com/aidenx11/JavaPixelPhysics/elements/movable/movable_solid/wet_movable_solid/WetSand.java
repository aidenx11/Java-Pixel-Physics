package com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.wet_movable_solid;

import com.aidenx11.JavaPixelPhysics.PixelPhysicsGame;
import com.aidenx11.JavaPixelPhysics.color.CustomColor;
import com.aidenx11.JavaPixelPhysics.color.CustomColor.ColorValues;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.MovableSolid;

public class WetSand extends WetMovableSolid {

	public static ElementTypes type = ElementTypes.WET_SAND;
	private static float acceleration = PixelPhysicsGame.GRAVITY_ACCELERATION - 0.05f;
	private static float maxSpeed = 3f;
	private static int density = 8;
	private static float inertialResistance = 0.01f;
	private static float friction = 1f;
	
	public static CustomColor color = new CustomColor(ColorValues.WET_SAND, false);

	public WetSand(int row, int column) {
		super(type, row, column, color, false, -1, false, true, 0, false, 1f,
				acceleration, maxSpeed, density, false, inertialResistance, friction, -1);
		super.setFreeFalling(true);
	}
	
}
