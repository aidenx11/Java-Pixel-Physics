package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor.ColorValues;
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

	@Override
	public void updateVelocity() {
	}

	@Override
	public int getUpdateCount() {
		return 0;
	}

	@Override
	public void resetVelocity() {
	}

	@Override
	public void update() {
	}
	
}
