package com.aidenx11.game.elements;

public class Fire extends Immovable {

	public static ElementTypes type = ElementTypes.FIRE;

	public Fire(int row, int column) {
		super(type, row, column, Element.fireColors[(int) Math.round(Math.random() * 3)], true, 75 + (int) (75 * Math.random()), true, false, 0, true);
		super.setOnFire(true);
	}

}
