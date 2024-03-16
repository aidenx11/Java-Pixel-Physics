package com.aidenx11.game.elements;

import com.aidenx11.game.color.ColorValues;
import com.aidenx11.game.color.CustomColor;

public class Sand extends Element {
	
	public static ElementTypes type = ElementTypes.SAND;

	public Sand(int row, int column, boolean isEmpty) {
		super(row, column, new CustomColor(ColorValues.SAND_COLOR), isEmpty);
		super.setMovable(true);
	}

	public Sand(int row, int column) {
		super(row, column, new CustomColor(ColorValues.SAND_COLOR), false);
		super.setMovable(true);
	}

	@Override
	public ElementTypes getType() {
		return type;
	}
	
	

}
