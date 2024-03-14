package com.aidenx11.game.elements;

import com.aidenx11.game.color.ColorValues;
import com.aidenx11.game.color.CustomColor;

public class Sand extends Element {

	public Sand(int row, int column, boolean isEmpty) {
		super(row, column, new CustomColor(ColorValues.SAND_COLOR), isEmpty);
	}

	public Sand(int row, int column) {
		super(row, column, new CustomColor(ColorValues.SAND_COLOR), false);
	}

	@Override
	public Sand getType() {
		return this;
	}

}
