package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Stone extends Immovable {
	
	public static ElementTypes type = ElementTypes.STONE;

	public Stone(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.STONE, true), false, 0, false, false,
				0, false);
	}

}
