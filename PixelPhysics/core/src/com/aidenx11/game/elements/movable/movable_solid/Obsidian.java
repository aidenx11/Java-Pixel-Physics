package com.aidenx11.game.elements.movable.movable_solid;

import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;
import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.Element.ElementTypes;

public class Obsidian extends MovableSolid {

	public static ElementTypes type = ElementTypes.OBSIDIAN;
	private static float acceleration = pixelPhysicsGame.GRAVITY_ACCELERATION;
	private static float maxSpeed = 5f;
	private static float density = 7f;
	private static float inertialResistance = 0.95f;
	private static float friction = 0.5f;

	public Obsidian(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.OBSIDIAN, true), false, -1, false, true, 0, false, 0,
				acceleration, maxSpeed, density, false, inertialResistance, friction, -1);
		super.setFreeFalling(false);
	}

}
