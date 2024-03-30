package com.aidenx11.game.elements;

import com.aidenx11.game.color.CustomColor;
import com.aidenx11.game.color.CustomColor.ColorValues;

public class Leaf extends Immovable {

	public static ElementTypes type = ElementTypes.LEAF;
	private static float chanceToCatch = 0.03f;
	private static int lifetime = 50;

	public Leaf(int row, int column) {
		super(type, row, column, new CustomColor(ColorValues.LEAF, true), false, lifetime, true, false, chanceToCatch,
				false);
	}

}
