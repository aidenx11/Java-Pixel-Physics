package com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid;

import com.aidenx11.JavaPixelPhysics.PixelPhysicsGame;
import com.aidenx11.JavaPixelPhysics.color.CustomColor;
import com.aidenx11.JavaPixelPhysics.color.CustomColor.ColorValues;

public class Rust extends MovableSolid {

	public static ElementTypes type = ElementTypes.RUST;
	private static float acceleration = PixelPhysicsGame.GRAVITY_ACCELERATION - 0.05f;
	private static float maxSpeed = 2f;
	private static int density = 8;
	private static float inertialResistance = 0.8f;
	private static float friction = 1f;

	public static CustomColor color = new CustomColor(ColorValues.RUST, true);
	
	public Rust(int row, int column) {
		super(type, row, column, color, false, -1, false, false, 0, false, 0,
				acceleration, maxSpeed, density, false, inertialResistance, friction, -1);
		super.setFreeFalling(false);
	}

}
