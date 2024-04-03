package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor.ColorValues;
import com.aidenx11.game.color.CustomColor;

public class Wood extends Immovable {

	public static ElementTypes type = ElementTypes.WOOD;
	private static int lifetime = 150;
	private static float chanceToCatch = 0.006f;

	public Wood(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.WOOD_COLOR, true), false, lifetime, true, false,
				chanceToCatch, false, -1);
	}

}
