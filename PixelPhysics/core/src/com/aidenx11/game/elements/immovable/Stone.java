package com.aidenx11.game.elements.immovable;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Stone extends Immovable {
	
	public static ElementTypes type = ElementTypes.STONE;
	private static int meltingPoint = 150;

	public Stone(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.STONE, true), false, 0, false, false,
				0, false, meltingPoint);
	}

}
