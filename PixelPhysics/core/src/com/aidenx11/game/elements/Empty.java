package com.aidenx11.game.elements;

public class Empty extends Element {

	public static ElementTypes type = ElementTypes.EMPTY;

	public Empty(int row, int column) {
		super(row, column);
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

	
}
