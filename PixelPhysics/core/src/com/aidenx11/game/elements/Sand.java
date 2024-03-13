package com.aidenx11.game.elements;

import com.aidenx11.game.color.ColorValues;
import com.aidenx11.game.color.CustomColor;

public class Sand extends Element {
	
	public Sand(int row, int column) {
		super(row, column, new CustomColor(ColorValues.SAND_COLOR));
	}

}
