package com.aidenx11.game.elements;

import com.aidenx11.game.color.ColorValues;
import com.aidenx11.game.color.CustomColor;

public class Wood extends Element {
	
	public static ElementTypes type = ElementTypes.WOOD;
	
	public Wood(int row, int column, boolean isEmpty) {
		super(row, column, new CustomColor(ColorValues.WOOD_COLOR), isEmpty);
		super.setMovable(false);
	}

	public Wood(int row, int column) {
		super(row, column, new CustomColor(ColorValues.WOOD_COLOR), false);
		super.setMovable(false);
	}

	@Override
	public ElementTypes getType() {
		return type;
	}
	
}
