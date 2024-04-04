package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Obsidian extends Immovable {

	public static ElementTypes type = ElementTypes.OBSIDIAN;
	private static int meltingPoint = -1;

	public Obsidian(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.OBSIDIAN, true), false, 0, false, false,
				0, false, meltingPoint);
	}

}
