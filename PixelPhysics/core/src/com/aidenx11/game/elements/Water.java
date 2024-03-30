package com.aidenx11.game.elements;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Water extends Liquid {

	public static ElementTypes type = ElementTypes.WATER;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 15f;
	private static float density = 6f;

	int[] darkerWater = new int[] { 15, 94, 156 };

	public Water(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.WATER, false), false, 1, true, true, 0, false,
				0f, acceleration, maxSpeed, density, true);
	}

}
