package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Steam extends Smoke {

	@SuppressWarnings("static-access")
	public Steam(int row, int column) {
		super(row, column);
		this.type = ElementTypes.STEAM;
		super.setColor(new CustomColor(ColorValues.STEAM, true));
		super.setMovesSideways(false);
		super.setDensity(0.5f);
		super.setLifetime(100 + (int) (130 * Math.random()));
	}

}
