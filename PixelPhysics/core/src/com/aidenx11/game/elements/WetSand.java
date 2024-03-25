package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class WetSand extends Sand {

	public WetSand(int row, int column) {
		super(row, column);
		super.setColor(new CustomColor(ColorValues.WET_SAND));
		super.setDensity(6f);
	}

	public WetSand(int row, int column, boolean isEmpty, boolean rainbow) {
		super(row, column, isEmpty, rainbow);
		super.setColor(new CustomColor(ColorValues.WET_SAND));
		super.setDensity(6f);
	}

	public WetSand(int row, int column, boolean isEmpty, int r, int g, int b) {
		super(row, column, isEmpty, r, g, b);
		super.setColor(new CustomColor(ColorValues.WET_SAND));
		super.setDensity(6f);
	}

}
