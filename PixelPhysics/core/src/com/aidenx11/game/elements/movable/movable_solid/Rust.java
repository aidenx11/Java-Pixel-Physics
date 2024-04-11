package com.aidenx11.game.elements.movable.movable_solid;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Rust extends MovableSolid {

	public static ElementTypes type = ElementTypes.RUST;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION - 0.05f;
	private static float maxSpeed = 2f;
	private static float density = 8f;
	private static float inertialResistance = 0.8f;
	private static float friction = 1f;

	public Rust(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.RUST, true), false, -1, false, false, 0, false, 0,
				acceleration, maxSpeed, density, false, inertialResistance, friction, -1);
		super.setFreeFalling(false);
	}

}
