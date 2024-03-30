package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Steam extends Gas {
	
	public static ElementTypes type = ElementTypes.STEAM;
	private static float acceleration = -0.1f;
	private static float maxSpeed = 0.35f;
	private static float density = 1f;

	public Steam(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.STEAM, true), true, (int) (150 + Math.random() * 100), false, false, 0, false, 0,
				acceleration, maxSpeed, density, true);
	}

}
