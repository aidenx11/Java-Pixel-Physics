package com.aidenx11.game.elements;

public class Empty extends Element {

	public static ElementTypes type = ElementTypes.EMPTY;

	public Empty(int row, int column) {
		super(row, column, null, true);
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
	public void setVelocity(float f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getVelocity() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
