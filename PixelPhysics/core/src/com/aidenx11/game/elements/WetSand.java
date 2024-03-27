package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class WetSand extends Sand {

	public WetSand(int row, int column) {
		super(row, column);
		super.setColor(new CustomColor(ColorValues.WET_SAND, true));
		super.setDensity(7f);
		super.setWet(true);
		super.setMovesSideways(true);
	}

}
